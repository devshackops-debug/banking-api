package com.dev.shack.banking.api.persistence.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Double amount;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "transaction_type")
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
