package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int id;
    private int initialSum;
    private int actualSum;
    private ReentrantLock lock;
    private List<Operation> operationList;

    public Account(int id, int initialSum) {
        this.id = id;
        this.initialSum = initialSum;
        this.actualSum = initialSum;
        this.lock = new ReentrantLock();
        this.operationList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInitialSum() {
        return initialSum;
    }

    public void setInitialSum(int initialSum) {
        this.initialSum = initialSum;
    }

    public int getActualSum() {
        return actualSum;
    }

    public void setActualSum(int actualSum) {
        this.actualSum = actualSum;
    }

    public List<Operation> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<Operation> operationList) {
        this.operationList = operationList;
    }

    public ReentrantLock getLock(){
        return this.lock;
    }

    public void decreaseSum(int sum){
        this.actualSum -= sum;
    }

    public void increaseSum(int sum){
        this.actualSum += sum;
    }

    public boolean check() {
        int initSum = this.initialSum;
        for (Operation operation : this.operationList) {
            if (operation.getSrc() == this.id) {
                initSum -= operation.getSum();
            } else {
                initSum += operation.getSum();
            }
        }
        return initSum == this.actualSum;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", initialSum=" + initialSum +
                ", actualSum=" + actualSum +
                ", operationList=" + operationList +
                '}';
    }
}