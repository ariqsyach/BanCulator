package com.example.banculator.view

import android.view.View
import com.example.banculator.model.UkuranAkhir
import com.example.banculator.model.UkuranAwal

interface InterfaceHome {
    interface View{
        fun input(ukuranAwal: UkuranAwal,ukuranAkhir: UkuranAkhir)
        fun reset(view: View)
        fun hideLoading()
        fun showLoading()
        fun onSuccess(msg: String)
        fun onError(msg: String)

    }
    interface Presenter{
        fun result(ukuranAwal: UkuranAwal,ukuranAkhir: UkuranAkhir)

    }
}