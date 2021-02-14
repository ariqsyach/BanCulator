package com.example.banculator.model

import com.google.firebase.Timestamp

data class FeedFirestoreData (
        var imageUrl: String = "",
        var diameter: String = "",
        var lebar: String = "",
        var tinggi: String = "",
        var mobil: String = "",
        var keterangan: String = ""
    )
