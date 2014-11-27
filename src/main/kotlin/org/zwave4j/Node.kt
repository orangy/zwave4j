package org.zwave4j

public class Node(val homeId: Long, val id: kotlin.Short) {
    var name : String
        get() = manager.getNodeName(homeId, id)
        set(value) = manager.setNodeName(homeId, id, value)

    val type : String
        get() = manager.getNodeType(homeId, id)

    var productName : String
        get() = manager.getNodeProductName(homeId, id)
        set(value) = manager.setNodeProductName(homeId, id, value)

    var location : String
        get() = manager.getNodeName(homeId, id)
        set(value) = manager.setNodeName(homeId, id, value)

    class object {
        val manager = Manager.get()!!
    }
}