package bojan.jovanoski.emt.lab1.Repositories.Persistance;

import bojan.jovanoski.emt.lab1.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PersistentTransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Get all transactions
     */
    List<Transaction> findAll();

    /**
     * Get transaction by Id
     */
    Optional<Transaction> findById(@Param("ID") Long ID);

    /**
     * Get transactions by products
     */
    List<Transaction> findAllByPurchasedProductId(@Param("productID") Long productID);

    /**
     * Get all transactions by user
     */
    List<Transaction> findAllByUsername(@Param("username") String username);

    /**
     * Save transaction
     */
    @Transactional
    @Modifying
    Transaction save(Transaction transaction);

    /**
     * Remove transaction
     */
    @Transactional
    @Modifying
    void delete(Transaction transaction);
}