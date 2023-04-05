import java.util.concurrent.*;

public class ParallelKaratsuba {

    private static final int threads = 4;
    private static final int MAX_DEPTH = 5;

    public static Polynomial multiply(Polynomial p1, Polynomial p2, int currentDepth) throws InterruptedException, ExecutionException {
        if (currentDepth > MAX_DEPTH) {
            return SequentialKaratsuba.multiply(p1, p2);
        }

        if (p1.getDegree() < 2 || p2.getDegree() < 2) {
            return SequentialKaratsuba.multiply(p1, p2);
        }

        int len = Math.max(p1.getDegree(), p2.getDegree()) / 2;
        Polynomial lowP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        Polynomial highP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getCoefficients().size()));
        Polynomial lowP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        Polynomial highP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getCoefficients().size()));

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        Future<Polynomial> f1 = executor.submit(() -> multiply(lowP1, lowP2, currentDepth + 1));
        Future<Polynomial> f2 = executor.submit(() -> multiply(PolynomialBasic.add(lowP1, highP1), PolynomialBasic
                .add(lowP2, highP2), currentDepth + 1));
        Future<Polynomial> f3 = executor.submit(() -> multiply(highP1, highP2, currentDepth + 1));

        executor.shutdown();

        Polynomial z1 = f1.get();
        Polynomial z2 = f2.get();
        Polynomial z3 = f3.get();

        executor.awaitTermination(50, TimeUnit.SECONDS);

        Polynomial r1 = PolynomialBasic.addZeros(z3, 2 * len);
        Polynomial r2 = PolynomialBasic.addZeros(PolynomialBasic.subtract(PolynomialBasic.subtract(z2, z3), z1), len);
        Polynomial result = PolynomialBasic.add(PolynomialBasic.add(r1, r2), z1);
        return result;
    }
}
