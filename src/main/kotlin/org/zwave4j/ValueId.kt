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

    public fun getValue(): Any? {
        when (type) {
            ValueType.BOOL -> {
                val b = AtomicReference<Boolean>()
                Manager.get().getValueAsBool(this, b)
                return b.get()
            }
            ValueType.BYTE -> {
                val bb = AtomicReference<Short>()
                Manager.get().getValueAsByte(this, bb)
                return bb.get()
            }
            ValueType.DECIMAL -> {
                val f = AtomicReference<Float>()
                Manager.get().getValueAsFloat(this, f)
                return f.get()
            }
            ValueType.INT -> {
                val i = AtomicReference<Int>()
                Manager.get().getValueAsInt(this, i)
                return i.get()
            }
            ValueType.LIST -> return null
            ValueType.SCHEDULE -> return null
            ValueType.SHORT -> {
                val s = AtomicReference<Short>()
                Manager.get().getValueAsShort(this, s)
                return s.get()
            }
            ValueType.STRING -> {
                val ss = AtomicReference<String>()
                Manager.get().getValueAsString(this, ss)
                return ss.get()
            }
            ValueType.BUTTON -> return null
            ValueType.RAW -> {
                val sss = AtomicReference<ShortArray>()
                Manager.get().getValueAsRaw(this, sss)
                return sss.get()
            }
            else -> return null
        }
    }

}
