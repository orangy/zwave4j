/*
 * Copyright (c) 2013 Alexander Zagumennikov
 *
 * SOFTWARE NOTICE AND LICENSE
 *
 * This file is part of ZWave4J.
 *
 * ZWave4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ZWave4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ZWave4J.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.zwave4j

/**
 * @author zagumennikov
 */
public class Notification(public val type: NotificationType, public val valueId: ValueId, private val aByte: Short) {
    public fun getHomeId(): Long {
        return valueId.homeId
    }

    public fun getNodeId(): Short {
        return valueId.nodeId
    }

    public fun getGroupIdx(): Short {
        assert(NotificationType.GROUP == type)
        return aByte
    }

    public fun getEvent(): Short {
        assert(NotificationType.NODE_EVENT == type)
        return aByte
    }

    public fun getButtonId(): Short {
        assert(NotificationType.CREATE_BUTTON == type || NotificationType.DELETE_BUTTON == type || NotificationType.BUTTON_ON == type || NotificationType.BUTTON_OFF == type)
        return aByte
    }

    public fun getSceneId(): Short {
        assert(NotificationType.SCENE_EVENT == type)
        return aByte
    }

    public fun getNotification(): NotificationCode {
        assert(NotificationType.NOTIFICATION == type)
        return NotificationCode.getNotificationCode(aByte)
    }

}
