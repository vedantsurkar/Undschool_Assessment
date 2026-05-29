package com.example.Undoschool_Assessment.repository;

import com.example.Undoschool_Assessment.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}