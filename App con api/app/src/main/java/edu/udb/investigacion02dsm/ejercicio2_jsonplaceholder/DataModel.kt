package edu.udb.investigacion02dsm.ejercicio2_jsonplaceholder

data class PostRequest(
    val userId: Int,
    val title: String,
    val body: String,
    val category: String
)

data class PostResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val category: String
)
