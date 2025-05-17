package com.srnyndrs.next_stop.shared.domain.model.single

/*data class Location(
    val latitude: Double,
    val longitude: Double
)*/

typealias Location = Pair<Double, Double>

fun Location.latitude(): Double = this.first

fun Location.longitude(): Double = this.second