package com.bank.transactionsystem.repository;

import com.bank.transactionsystem.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository repository;

    private Transaction testTransaction;

    @BeforeEach
    void setup() {
        testTransaction = Transaction.builder()
                .id("TEST_001")
                .amount(new BigDecimal("100.00"))
                .type("DEPOSIT")
                .description("Test transaction")
                .build();
    }

    @Test
    void shouldSaveAndRetrieveTransaction() {
        repository.save(testTransaction);
        
        assertTrue(repository.existsById("TEST_001"));
        assertEquals(1, repository.findAll(0, 10).size());
    }

    @Test
    void shouldDeleteTransaction() {
        repository.save(testTransaction);
        repository.deleteById("TEST_001");
        
        assertFalse(repository.existsById("TEST_001"));
    }

    @Test
    void shouldPaginateResults() {
        // Add 15 test transactions
        for (int i = 1; i <= 15; i++) {
            repository.save(Transaction.builder()
                    .id("PAG_" + i)
                    .amount(new BigDecimal(i + ".00"))
                    .type("DEPOSIT")
                    .build());
        }

        List<Transaction> firstPage = repository.findAll(0, 10);
        List<Transaction> secondPage = repository.findAll(1, 10);

        assertEquals(10, firstPage.size());
        assertEquals(5, secondPage.size());
    }
}