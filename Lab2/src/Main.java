import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ArrayList<Integer> v1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));
        ArrayList<Integer> v2 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));

        var buffer = new Buffer(3);
        var producer = new Producer(buffer, v1, v2);
        var consumer = new Consumer(buffer, v1.size());

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

    }
}
