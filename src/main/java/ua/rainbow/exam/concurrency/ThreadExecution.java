package ua.rainbow.exam.concurrency;


public class ThreadExecution {
    public static void main(String[] args) {
       createThreadWithLambda();
    }

    private static void createThreadWithLambda() {
        new Thread(()->new String(""));
    }
}
