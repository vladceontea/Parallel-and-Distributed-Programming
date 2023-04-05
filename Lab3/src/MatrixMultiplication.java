
import java.util.*;

public abstract class MatrixMultiplication extends Thread {
    protected final List<Map.Entry<Integer, Integer>> elements;
    protected final int startRow, startColumn, count, threadId;
    protected final Matrix A, B, C;
    protected int K;

    public MatrixMultiplication(int threadId, int startRow, int startColumn, int count, Matrix A, Matrix B, Matrix C) {
        this.threadId = threadId;
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.count = count;
        this.A = A;
        this.B = B;
        this.C = C;
        this.elements = new ArrayList<>();
        computeElements();
    }

    public MatrixMultiplication(int threadId, int startRow, int startColumn, int count, int K, Matrix A, Matrix B, Matrix C) {
        this.threadId = threadId;
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.count = count;
        this.K = K;
        this.A = A;
        this.B = B;
        this.C = C;
        this.elements = new ArrayList<>();
        computeElements();
    }

    protected abstract void computeElements();

    @Override
    public void run() {
        for (Map.Entry<Integer, Integer> element : elements) {
            int row = element.getKey();
            int column = element.getValue();
            int result = 0;
            int i = 0;
            while (i < A.getColumns()) {
                result += A.getElement(row, i) * B.getElement(i, column);
                i++;
            }
            C.setElement(row, column, result);
            //System.out.println(threadId + " " + row + " " + column);
        }
    }
}