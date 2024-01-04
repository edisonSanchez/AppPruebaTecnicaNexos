package com.edisonsanchez.apppruebatecnicanexos.data

data class AuthorizationResponse(
    val receiptId: String,
    val rrn: String,
    val statusCode: String,
    val statusDescription: String
)

data class AnnulationResponse(
    val statusCode: String,
    val statusDescription: String
)

data class RequestAuthorizationTransaction(
    val id: String,
    val commerceCode: String,
    val terminalCode: String,
    val amount: String,
    val card: String
)

data class RequestAnnulationTransaction(
    val receiptId: String,
    val rrn: String
)