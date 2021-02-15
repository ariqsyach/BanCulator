package com.example.banculator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.banculator.R
import com.example.banculator.model.HasilHitung
import com.example.banculator.model.UkuranAkhir
import com.example.banculator.model.UkuranAwal
import com.example.banculator.presenter.InterfaceContract
import com.example.banculator.presenter.PresenterUkuran
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.tv_lebar_akhir
import kotlinx.android.synthetic.main.fragment_home.tv_lebar_awal
import kotlinx.android.synthetic.main.fragment_home.tv_tinggi_akhir
import kotlinx.android.synthetic.main.fragment_home.tv_tinggi_awal
import kotlin.Int as Int


class CalculatorFragment : Fragment(), InterfaceContract.View {

    private lateinit var database:FirebaseDatabase
    private lateinit var reference:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("hasil")
        btn_hitung.setOnClickListener {
            sendData()
        }
    }

    private fun sendData()
    {
        val inputLebarAwal = et_lebar_awal.text.toString().toInt()
        val inputTinggiAwal = et_tinggi_awal.text.toString().toInt()
        val inputDiameterAwal = et_diameter_awal.text.toString().toInt()
        val inputLebarAkhir = et_lebar_akhir.text.toString().toInt()
        val inputTinggiAkhir = et_tinggi_akhir.text.toString().toInt()
        val inputDiameterAkhir = et_diameter_akhir.text.toString().toInt()
        val inputAwal = UkuranAwal(inputLebarAwal, inputTinggiAwal, inputDiameterAwal)
        val inputAkhir = UkuranAkhir(inputLebarAkhir, inputTinggiAkhir, inputDiameterAkhir)
        val presenter = PresenterUkuran(this)
        presenter.result(inputAwal,inputAkhir)
    }

    override fun showResult(
        hasilLebarAwal: Int,
        hasilLebarAkhir: Int,
        hasilTinggiAwal: Int,
        hasilTinggiAkhir: Int
    ) {
        var model = HasilHitung(hasilLebarAwal.toString(),hasilLebarAkhir.toString(),hasilTinggiAwal.toString(),hasilTinggiAkhir.toString())
        var id = reference.push().key

        reference.child(id!!).setValue(model).addOnCompleteListener{
            Toast.makeText(context!!, "oke", Toast.LENGTH_LONG).show()
        }

        tv_lebar_awal.text = "$hasilLebarAwal mm"
        tv_lebar_akhir.text = "$hasilLebarAkhir mm"
        tv_tinggi_awal.text = "$hasilTinggiAwal mm"
        tv_tinggi_akhir.text = "$hasilTinggiAkhir mm"

    }

}