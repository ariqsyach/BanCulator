package com.example.banculator.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banculator.R
import com.example.banculator.model.FeedFirestoreData
import com.example.banculator.model.UkuranAwal
import com.google.firebase.firestore.CollectionReference
import kotlinx.android.synthetic.main.item_feeds.view.*

class AdapterRv(var feedList: ArrayList<FeedFirestoreData>): RecyclerView.Adapter<AdapterRv.ViewHolder>() {


    fun addData(listHistory: List<FeedFirestoreData>) {
        this.feedList.clear()
        this.feedList.addAll(listHistory)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(feed: FeedFirestoreData) {
            Log.d("historyBind", feed.toString())
            val diameter = feed.diameter
            val lebar = feed.lebar
            val tinggi = feed.tinggi
            view.tv_item_hasil.text = "$lebar / $tinggi / $diameter"
            view.tv_item_tipeMobil.text = feed.mobil
            view.tv_item_keterangan.text = feed.keterangan
            Glide.with(this.view)
                .load(feed.imageUrl)
                .into(view.iv_item_mobil)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feeds, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = feedList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feedList[position])
    }
}