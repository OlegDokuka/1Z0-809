package ua.rainbow.exam.nio;

import javaslang.control.Try;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.System.out;
import static java.util.stream.Collectors.joining;

public class PathTest {
    private static final String[] PATHS = {
            "/home/Documents",
            "C:\\classes",
            "/home/",
            "Workspace",
            "./build",
            "../1Z0-809"
    };

    public static void main(String[] args) {
        simplePathTest();
        pathTransformationPipe();
        pathResolutionPipe();
        pathSiblingResolutionPipe();
        pathRelativizingPipe();
    }

    private static void simplePathTest() {
        printPathInformation(Paths.get("/zoo/armadillo/shells.txt"));
        System.out.println();
        printPathInformation(Paths.get("armadillo/shells.txt"));
    }

    private static void printPathInformation(Path path) {
        System.out.println("Filename is: " + path.getFileName());
        System.out.println("Root is: " + path.getRoot());

        Path currentParent = path;
        while ((currentParent = currentParent.getParent()) != null) {
            System.out.println("Current parent is: " + currentParent);
        }
    }

    private static void pathRelativizingPipe() {
        out.println("------------------------------------------------------------------------------------------------");
        out.println("---------------------------------RELATIVE-PATH-RESOLUTION-PIPE----------------------------------");
        out.println("------------------------------------------------------------------------------------------------");
        pathInforming(Arrays
                .stream(PATHS)
                .map(Paths::get)
                .flatMap(p -> Arrays
                        .stream(PATHS)
                        .map(Paths::get)
                        .peek(i -> out.println("**********"))
                        .peek(i -> out.print("Relativize "))
                        .peek(i -> out.println(p))
                        .peek(i -> out.println("**********"))
                        .peek(i -> out.print("With: "))
                        .peek(out::println)
                        .peek(i -> out.println("**********"))
                        .map(i -> Try.of(() -> p.relativize(i)).getOrElse(() -> {
                            out.println("********** Filed relativizing op **********");
                            return p;
                        }))));
    }


    private static void pathSiblingResolutionPipe() {
        out.println("------------------------------------------------------------------------------------------------");
        out.println("---------------------------------PATH-SIBLING-RESOLUTION-PIPE-----------------------------------");
        out.println("------------------------------------------------------------------------------------------------");
        pathInforming(Arrays
                .stream(PATHS)
                .map(Paths::get)
                .flatMap(p -> Arrays
                        .stream(PATHS)
                        .peek(i -> out.println("**********"))
                        .peek(i -> out.print("Composing "))
                        .peek(i -> out.println(p))
                        .peek(i -> out.println("**********"))
                        .peek(i -> out.print("With: "))
                        .peek(out::println)
                        .peek(i -> out.println("**********"))
                        .map(p::resolveSibling)));
    }

    private static void pathResolutionPipe() {
        out.println("------------------------------------------------------------------------------------------------");
        out.println("-------------------------------------PATH-RESOLUTION-PIPE---------------------------------------");
        out.println("------------------------------------------------------------------------------------------------");
        pathInforming(Arrays
                .stream(PATHS)
                .map(Paths::get)
                .flatMap(p -> Arrays
                        .stream(PATHS)
                        .peek(i -> out.println("**********"))
                        .peek(i -> out.print("Composing "))
                        .peek(i -> out.println(p))
                        .peek(i -> out.println("**********"))
                        .peek(i -> out.print("With: "))
                        .peek(out::println)
                        .peek(i -> out.println("**********"))
                        .map(p::resolve)));
    }

    private static void pathTransformationPipe() {
        out.println("------------------------------------------------------------------------------------------------");
        out.println("-----------------------------------PATH-TRANSFORMATION-PIPE-------------------------------------");
        out.println("------------------------------------------------------------------------------------------------");
        pathInforming(Arrays
                .stream(PATHS)
                .peek(i -> out.print("String path: "))
                .peek(out::println)
                .map(Paths::get));

    }


    private static void pathInforming(Stream<Path> pathStream) {
        pathStream
                .peek(i -> out.print("java.nio.file.Path: "))
                .peek(out::println)
                .map(Path::normalize)
                .peek(i -> out.print("Normalized: "))
                .peek(out::println)
                .map(p -> Try.of(p::toRealPath).onFailure(out::println).getOrElse(p.toAbsolutePath()))
                .peek(i -> out.print("Real Path: "))
                .peek(out::println)
                .map(Path::normalize)
                .peek(i -> out.print("Normalized: "))
                .peek(out::println)
                .peek(i -> out.print("Parts Count: "))
                .peek(p -> out.println(p.getNameCount()))
                .peek(i -> out.print("Parts Names: "))
                .peek(p -> out.println(StreamSupport.stream(p.spliterator(), false)
                        .map(Path::toString)
                        .collect(joining(", ", "[", "]"))))
                .peek(i -> out.print("Is Folder: "))
                .peek(p -> out.println(Files.isDirectory(p)))
                .peek(i -> out.print("Owner: "))
                .peek(p -> out.println(Try.of(() -> Files.getOwner(p)).getOrElse((UserPrincipal) null)))
                .peek(i -> out.print("ACLView: "))
                .peek(p -> out.println(Files.getFileAttributeView(p, AclFileAttributeView.class)))
                .peek(i -> out.print("BasicView: "))
                .peek(p -> out.println(Files.getFileAttributeView(p, BasicFileAttributeView.class)))
                .forEach(p -> out.println("-------------------------------------------------------------------------"));
    }
}
