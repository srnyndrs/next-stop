package com.srnyndrs.next_stop.app.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey
import com.srnyndrs.next_stop.shared.domain.model.single.VehicleIcon
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.round

fun Color.Companion.fromHex(colorString: String?): Color {
    return colorString?.let {
        Color(android.graphics.Color.parseColor("#$colorString"))
    } ?: Black
}

fun String.isNumber(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: Exception) {
        false
    }
}

fun PreferenceKey.validate(value: String): Boolean {
    // Check if the value is a number
    if(!value.isNumber()) return false

    // Check if the value is in the range
    return value.toInt() in this.min..this.max
}

fun Long.toFormattedDate(): String {
    val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return try {
        dateFormat.format(this)
    } catch (e: Exception) {
        "----.--.--"
    }
}

fun Long.formatDateWithPattern(pattern: String): String? {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return try {
        dateFormat.format(this)
    } catch (e: Exception) {
        null
    }
}

fun Long.toDateOnlyMillis(): Long {
    val oneDayInMillis = 24 * 60 * 60 * 1000
    return this / oneDayInMillis * oneDayInMillis
}

fun Long.toMinutes(): String {
    return "${round(this / 60f).toInt()} min"
}

fun currentDateInMillis(): Long {
    return System.currentTimeMillis().toDateOnlyMillis()
}


@Composable
fun VehicleIcon.toPainterResource(): Painter {
    return painterResource(
        when(this) {
            VehicleIcon.BKK_TRAM -> com.srnyndrs.next_stop.shared.R.drawable.tram
            VehicleIcon.BKK_BUS -> com.srnyndrs.next_stop.shared.R.drawable.bus
            VehicleIcon.BKK_NIGHT_BUS -> com.srnyndrs.next_stop.shared.R.drawable.night_bus
            VehicleIcon.BKK_SUBWAY -> com.srnyndrs.next_stop.shared.R.drawable.metro
            VehicleIcon.BKK_SUBURBAN_RAILWAY -> com.srnyndrs.next_stop.shared.R.drawable.suburban_rail
            VehicleIcon.VOLAN_BUS -> com.srnyndrs.next_stop.shared.R.drawable.regional_bus
            VehicleIcon.BKK_TROLLEYBUS -> com.srnyndrs.next_stop.shared.R.drawable.trolleybus
            VehicleIcon.UNKNOWN -> com.srnyndrs.next_stop.shared.R.drawable.default_bus
        }
    )
}