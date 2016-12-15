package ua.rainbow.exam.exceptions;

import java.io.Closeable;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

public class TryWithResource implements Closeable {
    public static void checkTheSequenceOfAction() {
        try (AutoCloseable myAC = () -> System.out.println("Closed")) {
            System.out.println("Opened");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void suppressedExceptions() {
        try (TryWithResource t = new TryWithResource()) {
            throw new IllegalStateException("turkeys ran off");
        } catch (IllegalStateException e) {
            System.out.println("caught: " + e.getMessage());
            for (Throwable t : e.getSuppressed())
                System.out.println(t.getMessage());
        }
    }

    public static void checkOnException() {
        try (TryWithResource tryWithResource = new TryWithResource()) {
            System.out.println("Opened");
        }
//      catch (IOException e) {
//         cant handle this exception
//      }
    }

    public static void exceptionInFinalStatement() {
        try (TryWithResource t = new TryWithResource()) {
            throw new IllegalStateException("turkeys ran off");
        } catch (IllegalStateException e) {
            System.out.println("This section has not after finally block been omitted");

            System.out.println("caught: " + e.getMessage());
            for (Throwable t : e.getSuppressed())
                System.out.println(t.getMessage());
        } finally {
            throw new RuntimeException("and we couldn't find them");
        }
    }

    public static void returnInFinalStatementTWR() {
        try (TryWithResource t = new TryWithResource()) {
            throw new IllegalStateException("turkeys ran off");
        } catch (IllegalStateException e) {
            System.out.println("This section has not after finally block been omitted");

            System.out.println("caught: " + e.getMessage());
            for (Throwable t : e.getSuppressed())
                System.out.println(t.getMessage());
        } finally {
            return;
        }
    }

    public static void returnInFinalStatementTNormal() {
        try {
            throw new IllegalStateException("turkeys ran off");
        } catch (IllegalStateException e) {
            System.out.println("This section has not after finally block been omitted");

            throw new RuntimeException(e);
        } finally {
            return;
        }
    }

    public static void parseData() throws SQLException, DateTimeParseException {
    }

    public static void multiCatch() throws SQLException, DateTimeParseException {
        try {
            parseData();
        } catch (SQLException | DateTimeParseException e) {
            System.err.println(e);
            throw e;
        }
    }

    public void rethrowing() throws SQLException, DateTimeParseException {
        try {
            parseData();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println("checkTheSequenceOfAction");
        System.out.println();
        checkTheSequenceOfAction();
        System.out.println();
        System.out.println("suppressedExceptions");
        System.out.println();
        suppressedExceptions();
        System.out.println();
        System.out.println("returnInFinalStatementTWR");
        System.out.println();
        returnInFinalStatementTWR();
        System.out.println();
        System.out.println("returnInFinalStatementTNormal");
        System.out.println();
        returnInFinalStatementTNormal();
        System.out.println("Ignored");
        System.out.println();
        System.out.println("exceptionInFinalStatement");
        System.out.println();
        exceptionInFinalStatement();
    }

    @Override
    public void close() {
        System.out.println("Close clause without any throws");
        throw new IllegalArgumentException("Hello world from suppressed Exception");
    }
}
