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

import java.util.ArrayList

/**
 * @author zagumennikov
 */
public class NodeData {

    public var sentCnt: Long = 0
    public var sentFailed: Long = 0
    public var retries: Long = 0
    public var receivedCnt: Long = 0
    public var receivedDups: Long = 0
    public var receivedUnsolicited: Long = 0
    public var sentTs: String? = null
    public var receivedTs: String? = null
    public var lastRequestRtt: Long = 0
    public var averageRequestRtt: Long = 0
    public var lastResponseRtt: Long = 0
    public var averageResponseRtt: Long = 0
    public var quality: Short = 0
    public val lastReceivedMessage: ShortArray = ShortArray(254)
    public val ccData: List<CommandClassData> = ArrayList()
}
