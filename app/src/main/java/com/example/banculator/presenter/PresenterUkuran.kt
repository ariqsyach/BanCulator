package com.example.banculator.presenter

import android.widget.TextView

import com.example.banculator.model.UkuranAkhir
import com.example.banculator.model.UkuranAwal

class PresenterUkuran(private val view: InterfaceContract.View) : InterfaceContract.Presenter {

    private var hasilTinggiAwal = 0
    private var hasilTinggiAkhir= 0
    private var hasilLebarAwal= 0
    private var hasilLebarAkhir= 0


    override fun result(ukuranAwal: UkuranAwal, ukuranAkhir: UkuranAkhir) {
        hasilLebarAwal = ukuranAwal.lebar
        hasilLebarAkhir = ukuranAkhir.lebar
        hasilTinggiAwal = (ukuranAwal.diameter*25.4 + ukuranAwal.lebar * ukuranAwal.tinggi / 100).toInt()
        hasilTinggiAkhir = (ukuranAkhir.diameter*25.4 + ukuranAkhir.lebar * ukuranAkhir.tinggi / 100).toInt()

        view.showResult(hasilLebarAwal,hasilLebarAkhir,hasilTinggiAwal,hasilTinggiAkhir)
        
    }
}
