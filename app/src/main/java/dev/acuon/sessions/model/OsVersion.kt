package dev.acuon.sessions.model

class OsVersion(
    val name: String,
    val version: String,
    val releasedOn: Int,
    val sdk: Int,
    val description: String,
    val symbol: String
) {
}