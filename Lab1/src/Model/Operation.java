package Model;

public class Operation {
    private int id;
    private int src;
    private int dest;
    private int sum;

    public Operation(int id, int src, int dest, int sum) {
        this.id = id;
        this.src = src;
        this.dest = dest;
        this.sum = sum;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getSrc(){
        return this.src;
    }

    public void setSrc(int src){
        this.src = src;
    }

    public int getDest(){
        return this.dest;
    }

    public void setDest(int dest){
        this.dest = dest;
    }

    public int getSum(){
        return this.sum;
    }

    public void setSum(int sum){
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + this.id +
                ", sourceAccount=" + src +
                ", destAccount=" + dest +
                ", sum=" + sum +
                '}';
    }
}