
public class PolynomialMultiplication implements Runnable{
    private final int start;
    private final int end;
    private final Polynomial p1;
    private final Polynomial p2;
    private final Polynomial result;

    public PolynomialMultiplication(int start, int end, Polynomial p1, Polynomial p2, Polynomial result) {
        this.start = start;
        this.end = end;
        this.p1 = p1;
        this.p2 = p2;
        this.result = result;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {

            if (i > result.getCoefficients().size()) {
                return;
            }

            for (int j = 0; j <= i; j++) {
                if (j < p1.getCoefficients().size() && (i - j) < p2.getCoefficients().size()) {
                    int value = p1.getCoefficients().get(j) * p2.getCoefficients().get(i - j);
                    result.getCoefficients().set(i, result.getCoefficients().get(i) + value);
                }
            }
        }
    }
}

