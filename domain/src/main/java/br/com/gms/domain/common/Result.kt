package br.com.gms.domain.common

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        var code: String? = null,
        var message: String? = null,
        val exception: Exception? = null
    ) : Result<Nothing>() {

        fun default(): Error {
            return Error().also {
                message = "Has error occurred!"
            }
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[code=$code,message=$message]"
        }
    }
}