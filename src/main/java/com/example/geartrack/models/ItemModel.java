package com.example.geartrack.models;

import com.example.geartrack.entities.ItemEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ItemModel {

    private String name;

    private String description;

    private boolean isCollected;

    public static ItemModel fromEntity(ItemEntity itemEntity) {
        return ItemModel.builder()
                .name(itemEntity.getName())
                .description(itemEntity.getDescription())
                .build();
    }

}
