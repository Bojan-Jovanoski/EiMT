package bojan.jovanoski.emt.lab1.Service;

import bojan.jovanoski.emt.lab1.Models.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction addNewTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    Transaction getById(long ID);

    List<Transaction> getAllByUsername(String username);
}