import java.util.List;

public class Producer extends Thread {
    private final Buffer buffer;
    private final List<Integer> v1, v2;

    public Producer(Buffer buffer, List<Integer> vector1, List<Integer> vector2){
        this.buffer = buffer;
        this.v1 = vector1;
        this.v2 = vector2;
    }

    public void run(){
        for(int i = 0; i<v1.size(); i++){
            try{
                int product = v1.get(i) * v2.get(i);
                buffer.put(product);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
