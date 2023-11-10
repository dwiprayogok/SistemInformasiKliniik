package com.example.sisteminformasikliniik.shared.interfaces;

import com.example.sisteminformasikliniik.Model.KartuBerobat;

import java.util.List;

public interface IKartuBerobat {
    void showDetail(List<KartuBerobat> berobatList);
    void onFailedShowData(String error);
}
