package com.bank.transactionsystem.controller;

import com.bank.transactionsystem.model.Transaction;
import com.bank.transactionsystem.repository.TransactionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for transaction operations
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionRepository repository;

    @PostMapping
    public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction transaction) {
        if (repository.existsById(transaction.getId())) {
            throw new IllegalArgumentException("Transaction already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(repository.save(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(repository.findAll(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(
            @PathVariable String id,
            @Valid @RequestBody Transaction transaction) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Transaction not found");
        }
        return ResponseEntity.ok(repository.save(transaction));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        repository.deleteById(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}