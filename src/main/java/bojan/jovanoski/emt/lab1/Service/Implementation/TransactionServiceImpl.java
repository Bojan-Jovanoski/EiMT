package bojan.jovanoski.emt.lab1.Service.Implementation;

import bojan.jovanoski.emt.lab1.Models.Transaction;
import bojan.jovanoski.emt.lab1.Repositories.Persistance.PersistentTransactionRepository;
import bojan.jovanoski.emt.lab1.Service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private PersistentTransactionRepository transactionRepository;

    public TransactionServiceImpl(PersistentTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction addNewTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(long ID){
        Optional<Transaction> transaction = transactionRepository.findById(ID);
        if (transaction.isPresent())
            return transaction.get();
        return null;
    }

    @Override
    public List<Transaction> getAllByUsername(String username){
        return transactionRepository.findAllByUsername(username);
    }
}
