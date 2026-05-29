package com.example.Undoschool_Assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Undoschool_Assessment.entity.Parent;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Parent p where p.id = :id")
    Optional<Parent> findByIdWithLock(@Param("id") Long id);
}
