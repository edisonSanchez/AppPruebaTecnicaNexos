package com.edisonsanchez.apppruebatecnicanexos.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface EndPointsApi {

    @POST(AUTHORIZATION_END_POINT)
    @Headers("Content-type: application/json", "BASE64: UTF-8")
    fun authorizeTransaction(@Header(HEADER_AUTHORIZATION) headerAuthorization: String,
                             @Body requestAuthorizationTransaction: RequestAuthorizationTransaction)
    : Call<AuthorizationResponse>

    @POST(ANNULATION_END_POINT)
    @Headers("Content-type: application/json", "BASE64: UTF-8")
    fun invalidateTransaction(@Header(HEADER_AUTHORIZATION) headerAuthorization: String,
                              @Body requestAnnulationTransaction: RequestAnnulationTransaction)
    : Call<AnnulationResponse>


}