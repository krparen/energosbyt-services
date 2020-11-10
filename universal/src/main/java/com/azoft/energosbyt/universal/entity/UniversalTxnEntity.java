package com.azoft.energosbyt.universal.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "universal_txn")
public class UniversalTxnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String txnId;
    private LocalDateTime txnDate;
    private String system;
    private String account;
    private BigDecimal sum;

    private Instant created;
    private Instant updated;

    @PrePersist
    public void prePersist() {
        updated = Instant.now();
        created = updated;
    }

    @PreUpdate
    public void preUpdate() {
        updated = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UniversalTxnEntity that = (UniversalTxnEntity) o;

        if (getId() == null && that.getId() == null) {
            return false;
        }

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

