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

import java.io.*
import java.lang
import org.zwave4j.*

private var homeId: Long = 0
private var ready = false

fun format(format: String, vararg values: Any?) = lang.String.format(format, *values)

fun Node.describe(): String {
    return "#${id} ${productName} ${name} @${location}"
}

fun ValueId.action(action: String): String {
    return "\tValue $action: $label = $value\n " +
            "\t\tcommand class: $commandClassId\n" +
            "\t\tinstance: $instance\n" +
            "\t\tindex: $index\n" +
            "\t\tgenre: $genre\n" +
            "\t\ttype:  $type\n"
}


public fun main(args: Array<String>) {
    NativeLibraryLoader.loadLibrary(ZWave4j.LIBRARY_NAME, javaClass<ZWave4j>())

    val options = Options.create(args[0], "", "")
    options.addOptionBool("ConsoleOutput", false)
    options.lock()

    val manager = Manager.create()

    val watcher = object : NotificationWatcher {
        override fun onNotification(notification: Notification, context: Any?) {
            when (notification) {
                is HomeNotification -> {
                    println("Home [${notification.homeId}]")
                    homeId = notification.homeId
                    when (notification.type) {
                        NotificationType.DRIVER_READY -> println("\tDriver ready")
                        NotificationType.DRIVER_FAILED -> println("\tDriver failed")
                        NotificationType.DRIVER_RESET -> println("\tDriver reset")
                        NotificationType.DRIVER_REMOVED -> println("\tDriver removed")

                        NotificationType.AWAKE_NODES_QUERIED,
                        NotificationType.ALL_NODES_QUERIED_SOME_DEAD,
                        NotificationType.ALL_NODES_QUERIED -> {
                            println("\tAll nodes queried (${notification.type})")
                            manager.writeConfig(notification.homeId)
                            ready = true
                        }

                        NotificationType.POLLING_ENABLED -> println("\tPolling enabled")
                        NotificationType.POLLING_DISABLED -> println("\tPolling disabled")
                    }
                }
                is GroupNotification -> {
                    println("Node ${notification.node.describe()}")
                    println("Group # ${notification.groupId}")
                }
                is SceneEventNotification -> println("Scene event: #${notification.sceneId}")

                is NodeEventNotification -> {
                    println("Node ${notification.node.describe()}")
                    println("\tEvent ${notification.eventId}")
                }
                is NodeStatusNotification -> {
                    println("Node ${notification.node.describe()}")
                    println("\tStatus ${notification.code}")
                }
                is ButtonNotification -> {
                    println("Node ${notification.node.describe()}")
                    println("\tButton [${notification.buttonId}]")
                }

                is NodeValueNotification -> {
                    println("Node ${notification.node.describe()}")
                    when (notification.type) {
                        NotificationType.VALUE_ADDED -> println(notification.valueId.action("Added"))
                        NotificationType.VALUE_REMOVED -> println(notification.valueId.action("Removed"))
                        NotificationType.VALUE_CHANGED -> println(notification.valueId.action("Changed"))
                        NotificationType.VALUE_REFRESHED -> println(notification.valueId.action("Refreshed"))
                    }
                }
                is NodeNotification -> {
                    println("Node ${notification.node.describe()}")
                    when (notification.type) {
                        NotificationType.NODE_NEW -> println("\tNew")
                        NotificationType.NODE_ADDED -> println("\tAdded")
                        NotificationType.NODE_REMOVED -> println("\tRemoved")
                        NotificationType.ESSENTIAL_NODE_QUERIES_COMPLETE -> println("\nEssential complete")
                        NotificationType.NODE_QUERIES_COMPLETE -> println("\tQueries complete")
                        NotificationType.NODE_NAMING -> println("\tNaming")
                        NotificationType.NODE_PROTOCOL_INFO -> println("\tProtocol Info")
                    }
                }
                else -> println(notification.type.name())
            }
        }
    }
    manager.addWatcher(watcher, null)

    val controllerPort = args[1]

    manager.addDriver(controllerPort)

    val br = BufferedReader(InputStreamReader(System.`in`))

    val line: String?
    do {
        line = br.readLine()
        if (line == null) {
            continue
        }

        when (line) {
            "on" -> if (ready)
                manager.switchAllOn(homeId)
            else
                println("Home is not ready")
            "off" -> if (ready)
                manager.switchAllOff(homeId)
            else
                println("Home is not ready")
        }
    } while (line != null && line != "q")


    br.close()

    manager.removeDriver(controllerPort)
    manager.removeWatcher(watcher, null)
    Manager.destroy()
    Options.destroy()
}

