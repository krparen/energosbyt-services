package com.azoft.energosbyt.universal.repository;

import com.azoft.energosbyt.universal.entity.UniversalTxnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversalTxnRepository extends JpaRepository<UniversalTxnEntity, Long> {
    UniversalTxnEntity findByTxnIdAndSystem(String txnId, String system);
}
