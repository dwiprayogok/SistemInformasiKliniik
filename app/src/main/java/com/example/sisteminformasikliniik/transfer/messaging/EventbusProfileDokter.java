package com.example.sisteminformasikliniik.transfer.messaging;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;

import java.util.List;

public class EventbusProfileDokter {

    private List<DokterEntity> dokterEntityList;
    private String message;

    public EventbusProfileDokter(List<DokterEntity> dokterEntityList, String message) {
        this.dokterEntityList = dokterEntityList;
        this.message = message;
    }

    public List<DokterEntity> getDokterEntityList() {
        return dokterEntityList;
    }

    public String getMessage() {
        return message;
    }
}
