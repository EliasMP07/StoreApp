package com.devdroid07.storeapp.store.data.network.dto.apomex

import com.google.gson.annotations.SerializedName

data class CopomexResponse(
    @SerializedName("error")val error : Boolean,
    @SerializedName("code_error")val codeError : Int,
    @SerializedName("error_message")val errorMessage : String?,
    @SerializedName("response")val postalCodeDto : PostalCodeDto?,
)
