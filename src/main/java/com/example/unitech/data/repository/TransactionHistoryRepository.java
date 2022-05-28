package com.example.unitech.data.repository;

import com.example.unitech.data.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory,Long> {

}
