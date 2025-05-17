package com.srnyndrs.next_stop.shared.domain.model.single

enum class VehicleIcon {
    BKK_TRAM,
    BKK_BUS,
    VOLAN_BUS,
    BKK_TROLLEYBUS,
    BKK_NIGHT_BUS,
    BKK_SUBWAY,
    BKK_SUBURBAN_RAILWAY,
    UNKNOWN;

    companion object {
        fun from(findValue: String?): VehicleIcon = entries.find {
            it.name == (findValue ?: "")
        } ?: UNKNOWN
    }
}