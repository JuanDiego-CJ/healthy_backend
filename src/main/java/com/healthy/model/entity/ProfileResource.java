package com.healthy.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profile_resources")
public class ProfileResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private boolean is_active;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_profile_resource_profile"))
    private Profile profile ;

    @ManyToOne
    @JoinColumn(name = "resource_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_profile_resource_resource"))
    private Resource resource;

    @Column(name = "access_expiration")
    private LocalDateTime access_expiration;
}