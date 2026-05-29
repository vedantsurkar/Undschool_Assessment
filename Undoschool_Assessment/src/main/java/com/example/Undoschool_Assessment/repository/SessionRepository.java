package com.example.Undoschool_Assessment.repository;

import com.example.Undoschool_Assessment.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByOfferingId(Long offeringId);
}
