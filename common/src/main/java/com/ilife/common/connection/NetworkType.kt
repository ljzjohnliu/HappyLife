package com.ilife.common.connection

enum class NetworkType(val value: Int) {

    GLOBAL_OFF(-1),

    ALL(0),

    WIFI_ONLY(1),

    UNMETERED(2);

    companion object {

        @JvmStatic
        fun valueOf(value: Int): NetworkType {
            return when (value) {
                -1 -> GLOBAL_OFF
                0 -> ALL
                1 -> WIFI_ONLY
                2 -> UNMETERED
                else -> ALL
            }
        }

    }

}