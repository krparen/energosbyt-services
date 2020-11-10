package com.azoft.energosbyt.entity;

import com.azoft.energosbyt.dto.Command;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "qiwi_txn")
public class QiwiTxnEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String txnId;
  private LocalDateTime txnDate;
  @Enumerated(EnumType.STRING)
  private Command command;
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
    final QiwiTxnEntity that = (QiwiTxnEntity) o;

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
