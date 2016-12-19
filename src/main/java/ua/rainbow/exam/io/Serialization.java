package ua.rainbow.exam.io;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialization {

    public static void main(String[] args) throws IOException {
        serializeMyObject();
    }

    private static void serializeMyObject() throws IOException {
        try (ObjectOutput output = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(prepareFile()))
        )) {
            output.writeObject(new MyObject(5, 6));
        }

        try (ObjectInput input = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(prepareFile()))
        )) {
            Object obj = input.readObject();

            System.out.println("object deserialize");
            System.out.println(obj);
        } catch (ClassNotFoundException | EOFException e) {
            e.printStackTrace();
        }
    }

    private static File prepareFile() throws IOException {
        File file = new File("build/serialized.data");

        file = file.exists() ? file : file.createNewFile() ? file : null;

        if (file == null) throw new UnsupportedOperationException();

        return file;
    }

    static class MyObject implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int a;
        private final int b;
        private static int A = 123;

        private MyObject() {
            this(1, 2);
            A = 321;

            System.out.println("This ctor handled!!!");
        }

        public MyObject(int A, int B) {
            a = A;
            b = B;
        }
    }
}
