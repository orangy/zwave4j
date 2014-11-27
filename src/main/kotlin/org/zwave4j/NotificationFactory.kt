package org.zwave4j

import kotlin.platform.platformStatic
import org.zwave4j.NotificationCode.MSG_COMPLETE
import org.zwave4j.NotificationCode.TIMEOUT
import org.zwave4j.NotificationCode.NO_OPERATION
import org.zwave4j.NotificationCode.AWAKE
import org.zwave4j.NotificationCode.SLEEP
import org.zwave4j.NotificationCode.DEAD
import org.zwave4j.NotificationCode.ALIVE
import org.zwave4j.NotificationCode.UNKNOWN

object NotificationFactory {
    platformStatic
    fun create(type: NotificationType, valueId: ValueId, aByte: Short): Notification {
        when (type) {
            NotificationType.BUTTON_ON,
            NotificationType.BUTTON_OFF,
            NotificationType.CREATE_BUTTON,
            NotificationType.DELETE_BUTTON
            -> return ButtonNotification(type, valueId, aByte)

            NotificationType.DRIVER_FAILED,
            NotificationType.DRIVER_READY,
            NotificationType.DRIVER_REMOVED,
            NotificationType.DRIVER_RESET,
            NotificationType.ALL_NODES_QUERIED,
            NotificationType.ALL_NODES_QUERIED_SOME_DEAD,
            NotificationType.AWAKE_NODES_QUERIED,
            NotificationType.POLLING_DISABLED,
            NotificationType.POLLING_ENABLED
            -> return HomeNotification(type, valueId)

            NotificationType.GROUP
            -> return GroupNotification(type, valueId, aByte)

            NotificationType.SCENE_EVENT
            -> return SceneEventNotification(type, valueId, aByte)

            NotificationType.NODE_EVENT
            -> return NodeEventNotification(type, valueId, aByte)

            NotificationType.NOTIFICATION
            -> return NodeStatusNotification(type, valueId, aByte)

            NotificationType.NODE_ADDED,
            NotificationType.NODE_NAMING,
            NotificationType.NODE_REMOVED,
            NotificationType.NODE_NEW,
            NotificationType.NODE_PROTOCOL_INFO,
            NotificationType.NODE_QUERIES_COMPLETE,
            NotificationType.ESSENTIAL_NODE_QUERIES_COMPLETE
            -> return NodeNotification(type, valueId)

            NotificationType.VALUE_ADDED,
            NotificationType.VALUE_CHANGED,
            NotificationType.VALUE_REFRESHED,
            NotificationType.VALUE_REMOVED
            -> return NodeValueNotification(type, valueId)

            else -> return NodeNotification(type, valueId)
        }
    }
}

public open class Notification(public val type: NotificationType, public val valueId: ValueId) {
}

class HomeNotification(type: NotificationType, valueId: ValueId) : Notification(type, valueId) {
    val homeId = valueId.homeId
}

class SceneEventNotification(type: NotificationType, valueId: ValueId, val sceneId: Short) : Notification(type, valueId)

public open class NodeNotification(type: NotificationType, valueId: ValueId) : Notification(type, valueId) {
    public val node: Node = Node(valueId.homeId, valueId.nodeId)
}

public class GroupNotification(type: NotificationType, valueId: ValueId, val groupId: Short) : NodeNotification(type, valueId)
public class ButtonNotification(type: NotificationType, valueId: ValueId, val buttonId: Short) : NodeNotification(type, valueId)
public class NodeEventNotification(type: NotificationType, valueId: ValueId, val eventId: Short) : NodeNotification(type, valueId)

public class NodeValueNotification(type: NotificationType, valueId: ValueId) : NodeNotification(type, valueId) {

}

public class NodeStatusNotification(type: NotificationType, valueId: ValueId, codeId: Short) : NodeNotification(type, valueId) {
    val code = when (codeId.toInt()) {
        0 -> MSG_COMPLETE
        1 -> TIMEOUT
        2 -> NO_OPERATION
        3 -> AWAKE
        4 -> SLEEP
        5 -> DEAD
        6 -> ALIVE
        else -> UNKNOWN
    }
}

