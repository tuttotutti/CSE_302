import java.util.ArrayList;

public class Bank {
    private ArrayList<Account> accounts = new ArrayList<>();

    public Bank(int accountNum, int balance) {
        for (int i = 0; i < accountNum; i++) {
            Account acc = new Account(i, balance);
            this.accounts.add(acc);
        }
    }

    private Account find(int id) {
        for (int i = 0; i < this.accounts.size(); i++) {
            if (this.accounts.get(i).getId() == id) {
                return this.accounts.get(i);
            }
        }
        return null;
    }

    public double getTotalBalance() {
        double total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    public boolean canTransaction(int fromId, int toId, double amount) {
        Account from = this.find(fromId);
        if (from == null) {
            return false;
        }
        Account to = this.find(toId);
        if (to == null) {
            return false;
        }
        return this.transaction(from, to, amount);
    }

    public boolean transaction(Account from, Account to, double amount) {
        int fromId = from.getId();
        int toId = to.getId();

        Account firstAccount = fromId < toId ? from : to;
        Account secondAccount = fromId < toId ? to : from;

        firstAccount.getLock().lock();
        secondAccount.getLock().lock();

        if (from.getBalance() < amount) {
            System.out.println("The balance of account " + fromId + " is not enough to do the transaction.");
            return false;
        }
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        firstAccount.getLock().unlock();
        secondAccount.getLock().unlock();
        System.out.println("Transaction from " + fromId + " to " + toId + " has been done successfully!");
        return true;
    }
}
