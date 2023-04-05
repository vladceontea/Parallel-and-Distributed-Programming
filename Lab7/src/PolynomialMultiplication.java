import java.util.ArrayList;
import java.util.List;

public class PolynomialMultiplication {

    public static Polynomial regularSequential(Polynomial p1, Polynomial p2){
        List<Integer> coefficients = new ArrayList<>();

        for(var i=0;i<=p1.getDegree() + p2.getDegree();++i) {
            coefficients.add(0);
        }

        Polynomial result = new Polynomial(coefficients);

        for (int i = 0; i < p1.getDegree()+1; i++) {
            for (int j = 0; j < p2.getDegree()+1; j++) {
                int newValue = result.getCoefficients().get(i + j) + p1.getCoefficients().get(i) * p2.getCoefficients().get(j);
                result.getCoefficients().set(i + j, newValue);
            }
        }
        return result;
    }

    public static Polynomial KaratsubaSequential(Polynomial p1, Polynomial p2){
        if (p1.getDegree() < 2 || p2.getDegree() < 2)
            return regularSequential(p1, p2);

        int len = Math.max(p1.getDegree(), p2.getDegree()) / 2;

        Polynomial lowP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        Polynomial highP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getCoefficients().size()));
        Polynomial lowP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        Polynomial highP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getCoefficients().size()));

        Polynomial z1 = KaratsubaSequential(lowP1, lowP2);
        Polynomial z2 = KaratsubaSequential(PolynomialBasic.add(lowP1, highP1), PolynomialBasic.add(lowP2, highP2));
        Polynomial z3 = KaratsubaSequential(highP1, highP2);

        Polynomial r1 = PolynomialBasic.addZeros(z3, 2 * len);
        Polynomial r2 = PolynomialBasic.addZeros(PolynomialBasic.subtract(PolynomialBasic.subtract(z2, z3), z1), len);
        Polynomial result = PolynomialBasic.add(PolynomialBasic.add(r1, r2), z1);
        return result;
    }
}