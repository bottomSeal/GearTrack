package com.example.geartrack.messages.response;

import com.example.geartrack.models.TripModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripGetAllResponse {

    List<TripModel> trips;
}
