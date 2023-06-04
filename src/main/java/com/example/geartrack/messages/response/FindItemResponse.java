package com.example.geartrack.messages.response;

import com.example.geartrack.models.ItemModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindItemResponse {

    ItemModel item;
}

