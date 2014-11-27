package org.zwave4j

public enum class NotificationCode {
    MSG_COMPLETE
    TIMEOUT
    NO_OPERATION
    AWAKE
    SLEEP
    DEAD
    ALIVE
    UNKNOWN

    class object {
        public fun getNotificationCode(aByte: Short): NotificationCode {
            when (aByte.toInt()) {
                0 -> return MSG_COMPLETE
                1 -> return TIMEOUT
                2 -> return NO_OPERATION
                3 -> return AWAKE
                4 -> return SLEEP
                5 -> return DEAD
                6 -> return ALIVE
                else -> return UNKNOWN
            }
        }
    }
}
