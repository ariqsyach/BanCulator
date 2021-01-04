package com.example.banculator.presenter

import com.example.banculator.model.UkuranAkhir
import com.example.banculator.model.UkuranAwal
import com.example.banculator.view.InterfaceHome

class HomePresenter(private val view: InterfaceHome.View) : InterfaceHome.Presenter {
    override fun result(ukuranAwal: UkuranAwal,ukuranAkhir: UkuranAkhir) {
        ukuranAwal.diameter.toInt()
    }

}