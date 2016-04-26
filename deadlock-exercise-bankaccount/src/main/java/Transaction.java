import java.util.Date;

/**
 * Created by aaronhoffer on 4/7/16.
 */
public class Transaction {

    private final BankAccount fromAccount;

    private final BankAccount toAccount;

    private final int amount;

    private Date timestamp;

    public Transaction(BankAccount fromAccount, BankAccount toAccount, int amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void transferFunds() {

        synchronized (toAccount) {
            fromAccount.debit(amount);

            synchronized (fromAccount) {
                toAccount.credit(amount);

                completeTransaction();
            }
        }
    }

    private void completeTransaction() {
        timestamp = new Date();
        System.err.println(String.format("%s transfered %d from %s to %s%n",
                timestamp,
                amount,
                fromAccount.name,
                toAccount.name));
    }

}



