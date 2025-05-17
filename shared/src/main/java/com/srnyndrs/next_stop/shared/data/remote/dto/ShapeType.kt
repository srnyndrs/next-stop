package com.srnyndrs.next_stop.shared.data.remote.dto

enum class ShapeType {
    BOX,
    CIRCLE;

    companion object {
        fun from(findValue: String?): ShapeType = ShapeType.entries.find {
            it.name == (findValue ?: "")
        } ?: BOX
    }
}