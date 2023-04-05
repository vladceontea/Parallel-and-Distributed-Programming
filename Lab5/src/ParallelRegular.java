import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ParallelRegular {

    private static final int threads = 4;

    public static Polynomial multiply(Polynomial p1, Polynomial p2) throws InterruptedException {
        int sizeOfResultCoefficientList = p1.getDegree() + p2.getDegree() + 1;

        List<Integer> coefficients = new ArrayList<>();

        for(var i=0;i<=p1.getDegree() + p2.getDegree();++i) {
            coefficients.add(0);
        }

        Polynomial result = new Polynomial(coefficients);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        int step = sizeOfResultCoefficientList / threads;
        if (step == 0) {
            step = 1;
        }

        int end;
        for (int i = 0; i < sizeOfResultCoefficientList; i += step) {
            end = i + step;
            PolynomialMultiplication task = new PolynomialMultiplication(i, end, p1, p2, result);
            executor.execute(task);
        }

        executor.shutdown();
        executor.awaitTermination(50, TimeUnit.SECONDS);

        return result;
    }
}
