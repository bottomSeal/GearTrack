package com.example.geartrack.messages.response;

import com.example.geartrack.models.ItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemListResponse {

    private List<ItemModel> items;
}
