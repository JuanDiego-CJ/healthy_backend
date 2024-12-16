package com.healthy.model.entity;

import com.healthy.model.enums.Frequency;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "habits")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "habit_type_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_habit_habit_type"))
    private HabitType habitType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency")
    private Frequency frequency;

}