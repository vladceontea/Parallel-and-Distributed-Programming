import java.util.concurrent.ExecutionException;


public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Polynomial p1 = new Polynomial(7);
        //System.out.println("p1=" + p1);
        Polynomial p2 = new Polynomial(7);
        //System.out.println("p2=" + p2);

        System.out.println("----------------");
        sequentialRegular(p1, p2);

        System.out.println("----------------");
        parallelRegular(p1, p2);

        System.out.println("----------------");
        sequentialKaratsuba(p1, p2);

        System.out.println("----------------");
        parallelKaratsuba(p1, p2);

    }

    public static void sequentialRegular(Polynomial p1, Polynomial p2) {
        System.out.println("Sequential Regular");
        long startTime = System.nanoTime();
        Polynomial result = SequentialRegular.multiply(p1, p2);
        //System.out.println("result=" + result);
        long stopTime = System.nanoTime();
        double totalTime = stopTime-startTime;
        System.out.println("Elapsed running time: " + totalTime/1000000000);
    }

    public static void parallelRegular(Polynomial p1, Polynomial p2) throws InterruptedException {
        System.out.println("Parallel Regular");
        long startTime = System.nanoTime();
        Polynomial result = ParallelRegular.multiply(p1, p2);
        //System.out.println("result=" + result);
        long stopTime = System.nanoTime();
        double totalTime = stopTime-startTime;
        System.out.println("Elapsed running time: " + totalTime/1000000000);
    }

    public static void sequentialKaratsuba(Polynomial p1, Polynomial p2) throws InterruptedException {
        System.out.println("Sequential Karatsuba");
        long startTime = System.nanoTime();
        Polynomial result = SequentialKaratsuba.multiply(p1, p2);
        //System.out.println("result=" + result);
        long stopTime = System.nanoTime();
        double totalTime = stopTime-startTime;
        System.out.println("Elapsed running time: " + totalTime/1000000000);
    }

    public static void parallelKaratsuba(Polynomial p1, Polynomial p2) throws ExecutionException, InterruptedException {
        System.out.println("Parallel Karatsuba");
        long startTime = System.nanoTime();
        Polynomial result = ParallelKaratsuba.multiply(p1, p2, 1);
        //System.out.println("result=" + result);
        long stopTime = System.nanoTime();
        double totalTime = stopTime-startTime;
        System.out.println("Elapsed running time: " + totalTime/1000000000);
    }


}