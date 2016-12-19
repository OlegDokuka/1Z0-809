package ua.rainbow.exam.io;


import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class FilesTest {
    public static void main(String[] args) {
        printRootFiles();
        equalsFiles();
        fileInfo();
    }

    private static void fileInfo() {
        File file = new File("home/test1");
        File renameTo = new File("home/test");

        System.out.print("file.getAbsolutePath() : ");
        System.out.println(file.getAbsolutePath());

        System.out.print("file.getPath() : ");
        System.out.println(file.getPath());

        System.out.print("file.isDirectory() : ");
        System.out.println(file.isDirectory());

        System.out.print("file.isFile() : ");
        System.out.println(file.isFile());

        System.out.print("file.exists() : ");
        System.out.println(file.exists());

        System.out.print("file.mkdir() : ");
        System.out.println(file.mkdir());

        System.out.print("file.mkdirs() : ");
        System.out.println(file.mkdirs());

        System.out.print("file.isDirectory() : ");
        System.out.println(file.isDirectory());

        System.out.print("file.lastModified() : ");
        System.out.println(new Date(file.lastModified()));

        System.out.print("file.renameTo() : ");
        System.out.println(file.renameTo(renameTo));

        System.out.print("file.delete() : ");
        System.out.println(file.delete());

        System.out.print("renamed.delete() : ");
        System.out.println(renameTo.delete());

        System.out.print("file.getParentFile().delete() : ");
        System.out.println(file.getParentFile().delete());
    }

    private static void printRootFiles() {
        System.out.print("Root files are: ");
        System.out.println(
                Arrays
                        .stream(File.listRoots())
                        .map(File::getAbsolutePath)
                        .collect(Collectors.joining(",", "[", "]"))
        );
    }

    private static void equalsFiles() {
        File parent = null;
        System.out.print("The follow statement 'new File(parent, \"home\").equals(new File(\"home\"))' are ");
        System.out.println(new File(parent, "home").equals(new File("home")));
    }
}
