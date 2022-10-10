package br.com.gms.domain.common

data class Error(
    var code: String? = null,
    var message: String? = null,
    val exception: Exception? = null
)