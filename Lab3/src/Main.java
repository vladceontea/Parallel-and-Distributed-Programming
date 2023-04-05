import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int rowsA = 1000;
    private static final int rowsB = 1000;
    private static final int colsA = 1000;
    private static final int colsB = 1000;
    private static final int nrThreads = 4;

    public static void main(String[] args) {

        Matrix A = new Matrix(rowsA, colsA);
        A.populate();
        Matrix B = new Matrix(rowsB, colsB);
        B.populate();

        System.out.println("Matrix A:");
        //System.out.println(A);
        System.out.println("Matrix B:");
        //System.out.println(B);

        if (colsA == rowsB) {
            Matrix C = new Matrix(rowsA, colsB);

            int sizeC = C.getRows() * C.getColumns();
            int count = sizeC / nrThreads;
            int totalRest = sizeC % nrThreads;
            long startTime, elapsedTime;

            System.out.println("Many threads approach");
            System.out.println("----------------------");


            System.out.println("Row Multiplication");
            List<Thread> threads1 = new ArrayList<>();
            int rest = sizeC % nrThreads;

            startTime = System.nanoTime();

            for(int i=0;i<nrThreads;++i) {

                int startRow = (count * i + totalRest - rest) / C.getColumns();
                int startCol = (count * i + totalRest - rest) % C.getColumns();

                if (rest > 0) {
                    threads1.add(new RowMultiplication(i, startRow, startCol, count+1, A, B, C));
                    rest--;
                }
                else{
                    threads1.add(new RowMultiplication(i, startRow, startCol, count, A, B, C));
                }
            }

            for(Thread thread : threads1){
                thread.start();
            }

            for (Thread thread : threads1) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            elapsedTime = System.nanoTime() - startTime;
            System.out.println("Time elapse: " + elapsedTime);

            System.out.println("Matrix C:");
            //System.out.println(C);

            System.out.println("Column Multiplication");
            List<Thread> threads2 = new ArrayList<>();
            rest = sizeC % nrThreads;
            C.clear();

            startTime = System.nanoTime();

            for(int i=0;i<nrThreads;++i) {

                int startRow = (count * i + totalRest - rest) % C.getRows();
                int startCol = (count * i + totalRest - rest) / C.getRows();

                if (rest > 0) {
                    threads2.add(new ColumnMultiplication(i, startRow, startCol, count+1, A, B, C));
                    rest--;
                }
                else{
                    threads2.add(new ColumnMultiplication(i, startRow, startCol, count, A, B, C));
                }
            }

            for(Thread thread : threads2){
                thread.start();
            }

            for (Thread thread : threads2) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            elapsedTime = System.nanoTime() - startTime;
            System.out.println("Time elapse: " + elapsedTime);

            System.out.println("Matrix C:");
            //System.out.println(C);

            System.out.println("K Multiplication");
            List<Thread> threads3 = new ArrayList<>();
            rest = sizeC % nrThreads;
            C.clear();

            startTime = System.nanoTime();

            for(int i=0;i<nrThreads;++i) {

                int startRow = i / C.getColumns();
                int startCol = i % C.getColumns();

                if (rest > 0) {
                    threads3.add(new KMultiplication(i, startRow, startCol,count+1, nrThreads, A, B, C));
                    rest--;
                }
                else{
                    threads3.add(new KMultiplication(i, startRow, startCol, count, nrThreads, A, B, C));
                }
            }

            for(Thread thread : threads3){
                thread.start();
            }

            for (Thread thread : threads3) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            elapsedTime = System.nanoTime() - startTime;
            System.out.println("Time elapse: " + elapsedTime);

            System.out.println("Matrix C:");
            //System.out.println(C);

            ExecutorService executorService1, executorService2, executorService3;

            System.out.println("Pool thread approach");
            System.out.println("----------------------");

            System.out.println("Row Multiplication");
            executorService1 = Executors.newFixedThreadPool(nrThreads);
            rest = sizeC % nrThreads;
            C.clear();

            startTime = System.nanoTime();

            for(int i=0;i<nrThreads;++i) {

                int startRow = (count * i + totalRest - rest) / C.getColumns();
                int startCol = (count * i + totalRest - rest) % C.getColumns();

                if (rest > 0) {
                    executorService1.submit(new RowMultiplication(i, startRow, startCol, count+1, A, B, C));
                    rest--;
                }
                else{
                    executorService1.submit(new RowMultiplication(i, startRow, startCol, count, A, B, C));
                }
            }

            executorService1.shutdown();
            try {
                if (!executorService1.awaitTermination(3, TimeUnit.SECONDS)) {
                    executorService1.shutdownNow();
                }
                elapsedTime = System.nanoTime() - startTime;
                System.out.println("Time elapse: " + elapsedTime);

                System.out.println("Matrix C:");
                //System.out.println(C);
            } catch (InterruptedException ex) {
                executorService1.shutdownNow();
                Thread.currentThread().interrupt();
            }

            System.out.println("Column Multiplication");
            executorService2 = Executors.newFixedThreadPool(nrThreads);
            rest = sizeC % nrThreads;
            C.clear();

            startTime = System.nanoTime();

            for(int i=0;i<nrThreads;++i) {

                int startRow = (count * i + totalRest - rest) % C.getRows();
                int startCol = (count * i + totalRest - rest) / C.getRows();

                if (rest > 0) {
                    executorService2.submit(new ColumnMultiplication(i, startRow, startCol, count+1, A, B, C));
                    rest--;
                }
                else{
                    executorService2.submit(new ColumnMultiplication(i, startRow, startCol, count, A, B, C));
                }
            }

            executorService2.shutdown();
            try {
                if (!executorService2.awaitTermination(3, TimeUnit.SECONDS)) {
                    executorService2.shutdownNow();
                }
                elapsedTime = System.nanoTime() - startTime;
                System.out.println("Time elapse: " + elapsedTime);

                System.out.println("Matrix C:");
                //System.out.println(C);
            } catch (InterruptedException ex) {
                executorService2.shutdownNow();
                Thread.currentThread().interrupt();
            }

            System.out.println("K Multiplication");
            executorService3 = Executors.newFixedThreadPool(nrThreads);
            rest = sizeC % nrThreads;
            C.clear();

            startTime = System.nanoTime();

            for(int i=0;i<nrThreads;++i) {

                int startRow = i / C.getColumns();
                int startCol = i % C.getColumns();

                if (rest > 0) {
                    executorService3.submit(new KMultiplication(i, startRow, startCol,count+1, nrThreads, A, B, C));
                    rest--;
                }
                else{
                    executorService3.submit(new KMultiplication(i, startRow, startCol, count, nrThreads, A, B, C));
                }
            }

            executorService3.shutdown();
            try {
                if (!executorService3.awaitTermination(3, TimeUnit.SECONDS)) {
                    executorService3.shutdownNow();
                }
                elapsedTime = System.nanoTime() - startTime;
                System.out.println("Time elapse: " + elapsedTime);

                System.out.println("Matrix C:");
                //System.out.println(C);
            } catch (InterruptedException ex) {
                executorService3.shutdownNow();
                Thread.currentThread().interrupt();
            }


        } else {
            System.out.println("Cannot multiply matrices");
        }

    }
}
