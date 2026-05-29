package com.example.Undoschool_Assessment.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Offering offering;

    private LocalDateTime startTimeUtc;
    private LocalDateTime endTimeUtc;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Offering getOffering() { return offering; }
    public void setOffering(Offering offering) { this.offering = offering; }
    public LocalDateTime getStartTimeUtc() { return startTimeUtc; }
    public void setStartTimeUtc(LocalDateTime startTimeUtc) { this.startTimeUtc = startTimeUtc; }
    public LocalDateTime getEndTimeUtc() { return endTimeUtc; }
    public void setEndTimeUtc(LocalDateTime endTimeUtc) { this.endTimeUtc = endTimeUtc; }
}
