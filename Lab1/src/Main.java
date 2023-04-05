import Model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args){

        AtomicBoolean check  = new AtomicBoolean(true);
        List<Account> accountList = new ArrayList<>();

        Scanner accounts = new Scanner(System.in);
        System.out.println("Enter number of accounts: ");
        int NUMBER_OF_ACCOUNTS = Integer.parseInt(accounts.nextLine());
        for (int i = 0; i < NUMBER_OF_ACCOUNTS; ++i) {
            accountList.add(new Account(i, 500));
        }

        Scanner threads = new Scanner(System.in);
        System.out.println("Enter number of threads: ");
        int NUMBER_OF_THREADS = Integer.parseInt(threads.nextLine());

        Scanner operations = new Scanner(System.in);
        System.out.println("Enter number of operations: ");
        int NUMBER_OF_OPERATIONS = Integer.parseInt(operations.nextLine());

        Controller controller = new Controller(accountList);

        Thread consistencyCheckThread = new Thread(() -> {
            try {
                while(check.get()) {
                    Thread.sleep(2);
                    if (controller.checkAccounts()) {
                        System.out.println("""
                                ---------------
                                CHECKER SUCCESS
                                ---------------""");
                    } else {
                        System.out.println("""
                                ---------------
                                CHECKER FAILED
                                ---------------""");
                    }
                }
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        List<Thread> threadsList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int j = i;
            threadsList.add(new Thread(() -> {
                try {
                    controller.transfer(j, NUMBER_OF_OPERATIONS, NUMBER_OF_ACCOUNTS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }

        consistencyCheckThread.start();
        threadsList.forEach(Thread::start);

        threadsList.forEach(thread -> {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        check.set(false);

        System.out.println("\nFinal list of accounts:");
        controller.printAccounts();
        System.out.println("\nFinal check.");
        if(controller.checkAccounts()) {
            System.out.println("""
                    ---------------
                    CHECKER SUCCESS
                    ---------------""");
        }
        else {
            System.out.println("""
                    ---------------
                    CHECKER FAILED
                    ---------------""");
        }
    }
}
