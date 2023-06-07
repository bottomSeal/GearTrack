package com.example.geartrack.models;

import com.example.geartrack.entities.ItemEntity;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ItemModel {

    private String name;

    private String description;

    private boolean isCollected;

    private UUID itemId;

    public static ItemModel fromEntity(ItemEntity itemEntity) {
        return ItemModel.builder()
                .name(itemEntity.getName())
                .description(itemEntity.getDescription())
                .itemId(itemEntity.getItemId())
                .build();
    }

}
