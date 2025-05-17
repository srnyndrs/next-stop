package com.srnyndrs.next_stop.app.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val notoSansFamily = FontFamily(
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_black, FontWeight.Black),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_bold, FontWeight.Bold),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_extra_bold, FontWeight.ExtraBold),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_extra_light, FontWeight.ExtraLight),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_light, FontWeight.Light),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_medium, FontWeight.Medium),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_regular, FontWeight.Normal),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_semibold, FontWeight.SemiBold),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_thin, FontWeight.Thin),
    Font(com.srnyndrs.next_stop.shared.R.font.notosans_thin_italic, FontWeight.Thin, FontStyle.Italic),
)

val AppTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = notoSansFamily,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    displayMedium = TextStyle(
        fontFamily = notoSansFamily,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displayLarge = TextStyle(
        fontFamily = notoSansFamily,
        fontSize = 57.sp,
        lineHeight = 64.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    titleSmall = TextStyle(
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = notoSansFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    bodySmall = TextStyle(
        fontFamily = notoSansFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = notoSansFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = notoSansFamily,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = notoSansFamily,
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = notoSansFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = notoSansFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
)