package com.healthy.model.entity;

import com.healthy.model.enums.ResourceType;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "expert_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_resource_expert"))
    private Expert expert;

    @ManyToOne
    @JoinColumn(name = "sub_plan_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_resource_sub_plan"))
    private SubPlan subPlan;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}