package com.srnyndrs.next_stop.shared.domain.model.single

enum class PreferenceKey(
    val label: String,
    val min: Int,
    val max: Int,
    val description: String,
    val metric: String
) {
    RADIUS("Radius", 0, 2000, "The maximum distance from your location", "meter"),
    TIME("Time", 0, 60, "The maximum departure time limit", "minutes")
}