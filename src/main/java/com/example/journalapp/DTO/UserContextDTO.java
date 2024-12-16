package com.example.journalapp.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserContextDTO {
    String ename;
    String locality;
    String crofarmId;
    int eid;
    @JsonProperty("app_name")
    String appName;
    @JsonProperty("retailer_id")
    int retailerId;
    @JsonProperty("oblivion_delivery_boy_id")
    String deliveryBoyId;
    @JsonProperty("oblivion_shift_id")
    String shiftId;
    @JsonProperty("oblivion_store_id")
    String storeId;
}
