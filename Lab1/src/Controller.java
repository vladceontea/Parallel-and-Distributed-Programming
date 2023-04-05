import Model.Account;
import Model.Operation;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    private List<Account> accountsList;
    private AtomicInteger opId = new AtomicInteger();

    public Controller(List<Account> accountsList) {
        this.accountsList = accountsList;
    }

    public void printAccounts() {
        for (Account account : accountsList) {
            System.out.println(account.toString());
        }
    }

    public boolean checkAccounts() {

        int failedAccounts = 0;

        for (Account account: accountsList){
            account.getLock().lock();
            if (!account.check()) {
                failedAccounts++;
            }
            for (Operation op : account.getOperationList()) {
                Account targetAccount = accountsList.get(op.getDest());
                if (!targetAccount.getOperationList().contains(op)) {
                    failedAccounts++;
                }
            }
            account.getLock().unlock();
        }

        return failedAccounts <= 0;
    }

    public void transfer(int threadId, int NUMBER_OF_OPERATIONS, int NUMBER_OF_ACCOUNTS) throws InterruptedException {
        for(int i = 0; i<=NUMBER_OF_OPERATIONS; i++) {
            int sender = (int) (Math.random() * NUMBER_OF_ACCOUNTS);
            int receiver = (int) (Math.random() * NUMBER_OF_ACCOUNTS);

            while (sender == receiver) {
                receiver = (int) (Math.random() * NUMBER_OF_ACCOUNTS);
            }

            int min = 50;
            int max = 250;
            int sum = (int) (Math.random() * (max - min + 1) + min);
            if (sender < receiver) {
                accountsList.get(sender).getLock().lock();
                accountsList.get(receiver).getLock().lock();
            } else {
                accountsList.get(receiver).getLock().lock();
                accountsList.get(sender).getLock().lock();
            }
            if (accountsList.get(sender).getActualSum() >= accountsList.get(receiver).getActualSum()) {

                accountsList.get(sender).decreaseSum(sum);
                accountsList.get(receiver).increaseSum(sum);
                Operation operation = new Operation(opId.getAndIncrement(), accountsList.get(sender).getId(), accountsList.get(receiver).getId(), sum);
                accountsList.get(sender).getOperationList().add(operation);
                accountsList.get(receiver).getOperationList().add(operation);

                System.out.println("Operation " + operation.getId() + " between accounts " + sender + " and " + receiver + " has been done successfully by thread " + threadId);

            } else {
                System.out.println("Operation could not be done");
            }
            if (sender < receiver) {
                accountsList.get(receiver).getLock().unlock();
                accountsList.get(sender).getLock().unlock();
            } else {
                accountsList.get(sender).getLock().unlock();
                accountsList.get(receiver).getLock().unlock();
            }
        }
    }
}