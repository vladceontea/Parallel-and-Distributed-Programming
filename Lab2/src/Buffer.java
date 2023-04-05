import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.*;

public class Buffer {

    private final Queue<Integer> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition cond = lock.newCondition();
    private final int capacity;

    public Buffer(int capacity){
        this.capacity = capacity;
    }

    public void put(int value) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                System.out.println("The queue is full");
                cond.await();
            }

            queue.add(value);
            System.out.println("Value " + value + " added to the queue");

            cond.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public int get() throws InterruptedException{
        lock.lock();
        try {
            while (queue.size() == 0) {
                System.out.println("The queue is empty");
                cond.await();
            }

            int value = queue.poll();
            System.out.println("Value " + value + " extracted from the queue");

            cond.signal();
            return value;
        }
        finally {
            lock.unlock();
        }
    }
}
