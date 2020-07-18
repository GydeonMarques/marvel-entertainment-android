package br.com.android.marvel.data.response

import com.google.gson.annotations.SerializedName

class ErrorDataResponse(
    @SerializedName("String")
    var error: String? = null
)