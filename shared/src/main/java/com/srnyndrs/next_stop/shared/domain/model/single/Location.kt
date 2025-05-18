package com.srnyndrs.next_stop.shared.domain.model.single

typealias Location = Pair<Double, Double>

fun Location.latitude(): Double = this.first

fun Location.longitude(): Double = this.second