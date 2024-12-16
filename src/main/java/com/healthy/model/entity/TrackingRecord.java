package com.healthy.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tracking_records")
public class TrackingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "value", nullable = false)
    private Float value;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "goal_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_tracking_record_goal"), nullable = false)
    private Goal goal;
}