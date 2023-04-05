import java.util.AbstractMap;
import java.util.Map;

public class RowMultiplication extends MatrixMultiplication{
    public RowMultiplication(int threadId, int startRow, int startColumn, int count, Matrix A, Matrix B, Matrix C) {
        super(threadId, startRow, startColumn, count, A, B, C);
    }

    @Override
    protected void computeElements() {
        int i = startRow, j = startColumn;
        int count = this.count;
        while (count > 0) {
            Map.Entry<Integer,Integer> element = new AbstractMap.SimpleEntry<>(i, j);
            elements.add(element);
            j++;
            count--;
            if (j == C.getColumns()) {
                j = 0;
                i++;
            }
        }
    }
}
