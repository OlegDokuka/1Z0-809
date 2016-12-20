package ua.rainbow.exam.stream;

import java.util.Arrays;
import java.util.List;

public class ReductinTest {
    public static void main(String[] args) {
        List<String> vals = Arrays.asList("a", "b");
        String join = vals.parallelStream()
                .reduce("_", (a, b) -> a.concat(b));
        System.out.println(join);

    }
}
