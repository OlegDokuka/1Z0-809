package ua.rainbow.exam.sql;

import javaslang.control.Try;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.err;
import static java.lang.System.out;
import static javaslang.control.Try.CheckedConsumer;

public class SQLJourney {
    public static void main(String[] args) throws Exception {
        connect(
                SQLJourney::users,
                user("Tom"),
                user("Carl"),
                view(13),
                viewAll(),
                scroll(),
                absolute(),
                error()
        ).onFailure(err::println);
    }

    @SafeVarargs
    private static Try<Void> connect(CheckedConsumer<? super Connection>... ccs) {
        try (Connection c = DriverManager.getConnection("jdbc:h2:./build/test")) {
            for (CheckedConsumer<? super Connection> cc : ccs)
                cc.accept(c);
            return Try.success(null);
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }

    private static void users(Connection c) throws SQLException {
        try (Statement s = c.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS USERS(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255))");
        }
    }

    private static CheckedConsumer<? super Connection> user(String name) {
        return c -> {
            try (PreparedStatement s = c.prepareStatement("INSERT INTO USERS (NAME) VALUES (?)")) {
                s.setString(1, name);
                s.execute();
            }
        };
    }


    private static CheckedConsumer<? super Connection> view(int id) {
        return c -> {
            try (PreparedStatement s = c.prepareStatement("SELECT * FROM USERS WHERE ID = ?",
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
                s.setInt(1, id);
                try (ResultSet rs = s.executeQuery()) {
                    if (rs.next()) {
                        out.print("User: ");
                        out.println(rs.getString("NAME"));
                    }
                }
            }
        };
    }

    private static CheckedConsumer<? super Connection> viewAll() {
        return c -> {
            try (Statement s = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                 ResultSet rs = s.executeQuery("SELECT * FROM USERS")) {
                out.print("Users:");

                while (rs.next()) {
                    out.print(' ');
                    out.print(rs.getString("NAME"));
                }
            }
        };
    }

    private static CheckedConsumer<? super Connection> scroll() {
        return c -> {
            try (Statement s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                 ResultSet rs = s.executeQuery("SELECT * FROM USERS")) {
                out.println();
                out.print("Users Forward:");

                while (rs.next()) {
                    out.print(' ');
                    out.print(rs.getString("NAME"));
                }

                out.println();
                out.print("Users Backward:");

                while (rs.previous()) {
                    out.print(' ');
                    out.print(rs.getString("NAME"));
                }
            }
        };
    }

    private static CheckedConsumer<? super Connection> absolute() {
        return c -> {
            try (Statement s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                 ResultSet rs = s.executeQuery("SELECT * FROM USERS")) {
                out.println();
                out.print("User on position 1: ");

                if (rs.absolute(1))
                    out.println(rs.getString("NAME"));

                out.println();
                out.print("User on position -1: ");

                if (rs.absolute(-1))
                    out.println(rs.getString("NAME"));
            }
        };
    }

    private static CheckedConsumer<? super Connection> error() {
        return c -> {
            try (Statement s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                 ResultSet rs = s.executeQuery("SELECT asdbaba FROM USERS")) {
            } catch (SQLException e) {
                err.print("Message: ");
                err.println(e.getMessage());
                err.print("SQLState: ");
                err.println(e.getSQLState());
                err.print("ErrorCode: ");
                err.println(e.getErrorCode());
            }
        };
    }
}
