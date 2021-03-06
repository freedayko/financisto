package ru.orangesoftware.financisto2.test.db;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ru.orangesoftware.financisto2.db.DatabaseAdapter;
import ru.orangesoftware.financisto2.db.DatabaseHelper;
import ru.orangesoftware.financisto2.db.DatabaseUtils;
import ru.orangesoftware.financisto2.model.Account;
import ru.orangesoftware.financisto2.model.Category;
import ru.orangesoftware.financisto2.model.Transaction;
import ru.orangesoftware.financisto2.test.builders.DateTime;

/**
 * Created by IntelliJ IDEA.
 * User: Denis Solonenko
 * Date: 2/7/11 7:22 PM
 */
public abstract class AbstractDbTest extends AndroidTestCase {

    private DatabaseHelper dbHelper;
    protected DatabaseAdapter db;

    @Override
    public void setUp() throws Exception {
        Context context = new RenamingDelegatingContext(getContext(), "test-");
        dbHelper = new DatabaseHelper(context);
        db = new DatabaseAdapter(context);
        db.dbHelper = db.dbHelper = dbHelper;
    }

    @Override
    public void tearDown() throws Exception {
        dbHelper.close();
    }

    public void assertAccountTotal(Account account, long total) {
        Account a = db.getAccount(account.id);
        assertEquals("Account "+account.id+" total", total, a.totalAmount);
    }

    public void assertLastTransactionDate(Account account, DateTime dateTime) {
        Account a = db.getAccount(account.id);
        assertEquals("Account "+account.id+" last transaction date", dateTime.asLong(), a.lastTransactionDate);
    }

    public void assertFinalBalanceForAccount(Account account, long expectedBalance) {
        long balance = db.getLastRunningBalanceForAccount(account);
        assertEquals("Account "+account.id+" final balance", expectedBalance, balance);
    }

    public void assertAccountBalanceForTransaction(Transaction t, Account a, long expectedBalance) {
        long balance = db.getAccountBalanceForTransaction(a, t);
        assertEquals(expectedBalance, balance);
    }

    public void assertTransactionsCount(Account account, long expectedCount) {
        long count = DatabaseUtils.rawFetchLongValue(db,
                "select count(*) from transactions where from_account_id=?",
                new String[]{String.valueOf(account.id)});
        assertEquals("Transaction for account "+account.id, expectedCount, count);
    }

    public void assertCategory(String name, boolean isIncome, Category c) {
        assertEquals(name, c.title);
        assertEquals(isIncome, c.isIncome());
    }

    public static <T> Set<T> asSet(T...values) {
        return new HashSet<T>(Arrays.asList(values));
    }

}
