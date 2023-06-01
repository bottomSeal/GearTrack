package com.example.geartrack.entities;

import com.example.geartrack.models.enums.HikingType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "items")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ItemEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID tripId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HikingType hikingType;

    private String description;

    private boolean isCollected;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripItemEntity> tripItems = new ArrayList<>();

}
