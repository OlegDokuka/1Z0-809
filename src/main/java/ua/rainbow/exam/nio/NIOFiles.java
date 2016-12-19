package ua.rainbow.exam.nio;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class NIOFiles {
    public static void main(String[] args) throws IOException {
        traversingFilesInDepth();
        findFilesInDepth();
        traversingFilesInBreadth();
        listFiles();
        printFile();
    }

    private static void printFile() throws IOException {
        out.println("------------------------------------------------------------------------------------------------");
        out.println("------------------------------------------PRINT-FILE--------------------------------------------");
        out.println("------------------------------------------------------------------------------------------------");
        AtomicInteger val = new AtomicInteger(1);
        Files
                .lines(Paths.get("src/main/java/ua/rainbow/exam/nio/NIOFiles.java"))
                .peek(i -> out.print(val.getAndIncrement()))
                .peek(i -> out.print(((val.get() - 1) / 10) < 1 ? " :   " : ":   "))
                .forEach(out::println);
    }

    private static void listFiles() throws IOException {
        out.println("------------------------------------------------------------------------------------------------");
        out.println("------------------------------------------LIST-FILES--------------------------------------------");
        out.println("------------------------------------------------------------------------------------------------");
        Files
                .list(Paths.get("").toRealPath())
                .forEach(out::println);
    }

    private static void findFilesInDepth() throws IOException {
        out.println("------------------------------------------------------------------------------------------------");
        out.println("--------------------------------------FIND-FILES-IN-DEPTH---------------------------------------");
        out.println("------------------------------------------------------------------------------------------------");
        Files.find(
                Paths.get("."),
                Integer.MAX_VALUE,
                (p, a) -> (p.toString().endsWith(".java") || p.toString().endsWith(".kt"))
                        && (a.lastAccessTime().toInstant().isAfter(Instant.now().minus(1, ChronoUnit.HOURS))
                        || a.lastModifiedTime().toInstant().isAfter(Instant.now().minus(1, ChronoUnit.HOURS)))
        ).forEach(out::println);
    }

    private static void traversingFilesInDepth() throws IOException {
        out.println("------------------------------------------------------------------------------------------------");
        out.println("------------------------------------TRAVERS-FILES-IN-DEPTH--------------------------------------");
        out.println("------------------------------------------------------------------------------------------------");
        Files
                .walk(Paths.get("."))
                .forEach(out::println);
    }

    private static void traversingFilesInBreadth() {
//        Files.newDirectoryStream().
    }

}
