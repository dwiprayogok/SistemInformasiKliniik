package com.example.sisteminformasikliniik.transfer.messaging;

import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import java.util.List;

public class EventbusProfileUser {
    private List<UserEntity> userEntityList;
    private String message;

    public EventbusProfileUser(List<UserEntity> userEntityList, String message) {
        this.userEntityList = userEntityList;
        this.message = message;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public String getMessage() {
        return message;
    }
}
