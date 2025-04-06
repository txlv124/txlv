package com.bank.transactionsystem.repository;

import com.bank.transactionsystem.model.Transaction;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory transaction storage with caching support
 */
@Repository
@CacheConfig(cacheNames = "transactions")
public class TransactionRepository {
    private final Map<String, Transaction> storage = new ConcurrentHashMap<>();

    /**
     * Save transaction with cache update
     */
    @CachePut(key = "#transaction.id")
    public Transaction save(Transaction transaction) {
        storage.put(transaction.getId(), transaction);
        return transaction;
    }

    /**
     * Find all transactions with pagination
     */
    @Cacheable
    public List<Transaction> findAll(int page, int size) {
        return storage.values().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    /**
     * Find transaction by ID with cache
     */
    @Cacheable(key = "#id")
    public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Delete transaction with cache eviction
     */
    @CacheEvict(key = "#id")
    public void deleteById(String id) {
        storage.remove(id);
    }

    /**
     * Check transaction existence
     */
    public boolean existsById(String id) {
        return storage.containsKey(id);
    }
}