package dev.acuon.sessions.model


data class OsVersion(
    var name: String,
    var version: Int,
    var releasedOn: Array<Int>,
    var sdk: Int,
    var description: String
) {
}