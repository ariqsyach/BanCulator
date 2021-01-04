package com.example.banculator.presenter


import com.example.banculator.model.UkuranAkhir
import com.example.banculator.model.UkuranAwal

interface InterfaceContract {
    interface View{
//        fun showDataAwal(ukuranAwal: UkuranAwal)
//        fun showDataAkhir(ukuranAkhir: UkuranAkhir)
        fun showResult(hasilLebarAwal:Int,hasilLebarAkhir:Int, hasilTinggiAwal:Int, hasilTinggiAkhir:Int)
    }
    interface Presenter {
        fun result(ukuranAwal: UkuranAwal,ukuranAkhir: UkuranAkhir)

    }
}