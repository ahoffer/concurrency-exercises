import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class DeadlockTest {
    private static final int REPETITIONS = 4;

    Transaction trx1, trx2;

    int amountToBill, amountToSally, transferedToBill, openingBalance;

    private BankAccount sally, bill;

    @Before
    public void setup() {
        openingBalance = 500;

        sally = new BankAccount("Sally", openingBalance);
        bill = new BankAccount("Bill", openingBalance);

        amountToBill = 50;
        amountToSally = 30;

        transferedToBill = REPETITIONS * (amountToBill - amountToSally);
        trx1 = new Transaction(sally, bill, amountToBill);
        trx2 = new Transaction(bill, sally, amountToSally);

        System.err.println("------------------------------------");
    }

    @Test
    public void multiThreaded() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(REPETITIONS);

        for (int i = 0; i < REPETITIONS; i++) {
            executor.execute(() -> trx1.transferFunds());
            executor.execute(() -> trx2.transferFunds());
        }

        executor.shutdown();
        boolean allJobsCompleted = executor.awaitTermination(1, TimeUnit.SECONDS);
        assertThat("Deadlock detected", allJobsCompleted, is(true));
        assertBalances();

    }

    @Test
    public void multiThreaded2() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(REPETITIONS);

        for (int i = 0; i < REPETITIONS; i++) {
            executor.execute(() -> {
                trx1.transferFunds();
            });
            executor.execute(() -> {
                trx2.transferFunds();
            });
        }

        executor.shutdown();
        boolean allJobsCompleted = executor.awaitTermination(1, TimeUnit.SECONDS);
        assertThat("Deadlock detected", allJobsCompleted, is(true));
        assertBalances();

    }

    @Test
    public void singleThreaded() {
        final long startTime = System.currentTimeMillis();

        for (int i = 0; i < REPETITIONS; i++) {
            trx1.transferFunds();
            trx2.transferFunds();
        }

        final long endTime = System.currentTimeMillis();
        System.err.println("Total execution time: " + (endTime - startTime));

        assertBalances();

    }

    private void assertBalances() {
        assertThat(bill.balance, equalTo(openingBalance + transferedToBill));
        assertThat(sally.balance, equalTo(openingBalance - transferedToBill));
        assertThat(bill.balance + sally.balance, equalTo(2 * openingBalance));
    }

}