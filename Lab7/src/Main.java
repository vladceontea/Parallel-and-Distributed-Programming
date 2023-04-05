
import mpi.*;

public class Main {
    static final int POLYNOMIAL_SIZE = 7;
    static final String IMPLEMENTATION = "karatsuba";

    public static void master(Polynomial p1, Polynomial p2, int processes){
        double startTime = System.nanoTime();
        int length = p1.getDegree() / (processes - 1);

        int start;
        int end = 0;
        for (int i = 1; i  < processes; i++){
            start = end;
            end = start + length;
            if (i == processes - 1) {
                end = p1.getDegree();
            }

            MPI.COMM_WORLD.Send(new Object[]{p1}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new Object[]{p2}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{start}, 0, 1, MPI.INT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{end}, 0, 1, MPI.INT, i, 0);
        }

        Object[] results = new Object[processes - 1];
        for (int i = 1; i  < processes; i++){
            MPI.COMM_WORLD.Recv(results, i-1, 1, MPI.OBJECT, i, 0);
        }

        Polynomial result = PolynomialBasic.getResult(results);

        double endTime = System.nanoTime();
        double totalTime = endTime - startTime;
        System.out.println("Execution time: " + totalTime/1000000000 + "s");
        System.out.println(result + "\n");
    }

    public static void regularWorker(){
        Object[] p1 = new Object[1];
        Object[] p2= new Object[1];
        int[] start = new int[1];
        int[] end = new int[1];

        MPI.COMM_WORLD.Recv(p1, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(p2, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(start, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(end, 0, 1, MPI.INT, 0, 0);

        Polynomial pol1 = (Polynomial) p1[0];
        Polynomial pol2 = (Polynomial) p2[0];

        for (int i = 0; i < start[0]; i++) {
            pol1.getCoefficients().set(i, 0);
        }
        for (int j = end[0]; j < pol1.getCoefficients().size(); j++) {
            pol1.getCoefficients().set(j, 0);
        }

        Polynomial result = PolynomialMultiplication.regularSequential(pol1, pol2);

        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);
    }

    public static void karatsubaWorker(){
        Object[] p1 = new Object[1];
        Object[] p2= new Object[1];
        int[] start = new int[1];
        int[] end = new int[1];

        MPI.COMM_WORLD.Recv(p1, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(p2, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(start, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(end, 0, 1, MPI.INT, 0, 0);

        Polynomial pol1 = (Polynomial) p1[0];
        Polynomial pol2 = (Polynomial) p2[0];

        for (int i = 0; i < start[0]; i++) {
            pol1.getCoefficients().set(i, 0);
        }
        for (int j = end[0]; j < pol1.getCoefficients().size(); j++) {
            pol1.getCoefficients().set(j, 0);
        }

        Polynomial result = PolynomialMultiplication.KaratsubaSequential(pol1, pol2);

        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);
    }

    public static void main(String[] args) {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (me == 0){
            System.out.println("Master process: \n");
            Polynomial p1 = new Polynomial(POLYNOMIAL_SIZE);
            //p1.generateCoefficients();
            Polynomial p2 = new Polynomial(POLYNOMIAL_SIZE);
            //p2.generateCoefficients();

            System.out.println("Polynomial 1:" + p1);
            System.out.println("Polynomial 2:" + p2);
            System.out.println("\n");

            master(p1, p2, size);
        } else {
            if (IMPLEMENTATION == "regular"){
                regularWorker();
            }
            if (IMPLEMENTATION == "karatsuba"){
                karatsubaWorker();
            }
        }

        MPI.Finalize();
    }
}