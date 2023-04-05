import java.util.AbstractMap;
import java.util.Map;

public class KMultiplication extends MatrixMultiplication{
    public KMultiplication(int threadId, int startRow, int startColumn, int count, int K, Matrix A, Matrix B, Matrix C) {
        super(threadId, startRow, startColumn, count, K, A, B, C);
    }

    @Override
    protected void computeElements() {
        int i = startRow, j = startColumn;
        int count = this.count;
        while (count > 0) {
            Map.Entry<Integer,Integer> element = new AbstractMap.SimpleEntry<>(i, j);
            elements.add(element);
            count--;
            i += (j + K) / C.getColumns();
            j = (j + K) % C.getColumns();
        }
    }
}
