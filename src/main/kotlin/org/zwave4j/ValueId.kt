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

import java.util.concurrent.atomic.AtomicReference
import kotlin.platform.platformStatic
import java.util.ArrayList

/**
 * @author zagumennikov
 */
public class ValueId(
        public val homeId: Long,
        public val nodeId: Short,
        public val genre: ValueGenre,
        public val commandClassId: Short,
        public val instance: Short,
        public val index: Short,
        public val type: ValueType
) {

    val units: String get() = manager.getValueUnits(this)
    val label: String get() = manager.getValueLabel(this)

    val value: Any? get() {
        when (type) {
            ValueType.BOOL -> {
                val r = AtomicReference<Boolean>()
                manager.getValueAsBool(this, r)
                return r.get()
            }
            ValueType.BYTE -> {
                val r = AtomicReference<Short>()
                manager.getValueAsByte(this, r)
                return r.get()
            }
            ValueType.DECIMAL -> {
                val r = AtomicReference<Float>()
                manager.getValueAsFloat(this, r)
                return r.get()
            }
            ValueType.INT -> {
                val r = AtomicReference<Int>()
                manager.getValueAsInt(this, r)
                return r.get()
            }
            ValueType.LIST -> {
                val r = AtomicReference<String>()
                manager.getValueListSelectionString(this, r)
                return r
            }
            ValueType.SCHEDULE -> {
                return null
            }
            ValueType.SHORT -> {
                val r = AtomicReference<Short>()
                manager.getValueAsShort(this, r)
                return r.get()
            }
            ValueType.STRING -> {
                val r = AtomicReference<String>()
                manager.getValueAsString(this, r)
                return r.get()
            }
            ValueType.BUTTON -> {
                return null
            }
            ValueType.RAW -> {
                val r = AtomicReference<ShortArray>()
                manager.getValueAsRaw(this, r)
                return r.get()
            }
            else -> return null
        }
    }

    class object {
        val manager: Manager get() = Manager.get()!!
    }
}

object ValueIdFactory {
    platformStatic
    fun create(
            homeId: Long,
            nodeId: Short,
            genre: ValueGenre,
            commandClassId: Short,
            instance: Short,
            index: Short,
            type: ValueType): ValueId {
        return ValueId(homeId, nodeId, genre, commandClassId, instance, index, type)
    }
}