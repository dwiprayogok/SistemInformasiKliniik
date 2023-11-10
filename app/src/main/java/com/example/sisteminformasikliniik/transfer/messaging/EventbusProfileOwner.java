package com.example.sisteminformasikliniik.transfer.messaging;

import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;

import java.util.List;

public class EventbusProfileOwner {

    private List<DataPemilikEntity> dataPemilikEntityList;
    private String message;

    public EventbusProfileOwner(List<DataPemilikEntity> dataPemilikEntityList, String message) {
        this.dataPemilikEntityList = dataPemilikEntityList;
        this.message = message;
    }

    public List<DataPemilikEntity> getDataPemilikEntityList() {
        return dataPemilikEntityList;
    }

    public String getMessage() {
        return message;
    }
}
