package com.example.E_Shopping.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    pending, shipped,  delivered,  cancelled;
    @JsonCreator
    public static OrderStatus forValue(String value){
        return OrderStatus.valueOf(value.toLowerCase());

    }

    @JsonValue
    public String toValue(){
        return this.name();
    }
}
