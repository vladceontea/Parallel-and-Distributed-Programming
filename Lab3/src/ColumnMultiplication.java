import java.util.AbstractMap;
import java.util.Map;

public class ColumnMultiplication extends MatrixMultiplication{
    public ColumnMultiplication(int threadId, int startRow, int startColumn, int count, Matrix A, Matrix B, Matrix C) {
        super(threadId,startRow, startColumn, count, A, B, C);
    }

    @Override
    protected void computeElements() {
        int i = startRow, j = startColumn;
        int count = this.count;
        while (count > 0) {
            Map.Entry<Integer,Integer> element = new AbstractMap.SimpleEntry<>(i, j);
            elements.add(element);
            i++;
            count--;
            if (i == C.getRows()) {
                i = 0;
                j++;
            }
        }
    }

}
