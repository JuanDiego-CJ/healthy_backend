package com.healthy.model.entity;

import com.healthy.model.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "profiles")
public class  Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_profile_user"))
    private User user;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "height", nullable = false)
    private Float height;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "health_conditions", columnDefinition = "TEXT")
    private String healthConditions;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Plan> plans;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<ProfileResource> profileResources;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Subscription> subPlans;
}