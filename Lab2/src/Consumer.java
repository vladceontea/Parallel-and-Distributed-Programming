public class Consumer extends Thread{

    private final Buffer buffer;
    private Integer sum;
    private final Integer length;

    public Consumer(Buffer buffer, Integer length){
        this.buffer = buffer;
        this.length = length;
        this.sum = 0;
    }

    public void run() {
        for(int i=0 ; i<this.length; i++){
            try {
                sum += buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("The final sum is " + sum);
    }
}
