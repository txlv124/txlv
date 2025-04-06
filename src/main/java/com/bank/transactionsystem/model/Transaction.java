package com.bank.transactionsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Financial transaction entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @NotBlank(message = "Transaction ID is required")
    private String id;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Transaction type is required")
    private String type;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @Builder.Default
    private Instant timestamp = Instant.now();
}