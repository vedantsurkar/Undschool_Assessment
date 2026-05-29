package com.example.Undoschool_Assessment.repository;

import com.example.Undoschool_Assessment.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByParentId(Long parentId);
}
