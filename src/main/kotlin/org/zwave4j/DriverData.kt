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
public class DriverData {
    public var sofCnt: Long = 0
    public var ackWaiting: Long = 0
    public var readAborts: Long = 0
    public var badChecksum: Long = 0
    public var readCnt: Long = 0
    public var writeCnt: Long = 0
    public var canCnt: Long = 0
    public var nakCnt: Long = 0
    public var ackCnt: Long = 0
    public var oofCnt: Long = 0
    public var dropped: Long = 0
    public var retries: Long = 0
    public var callbacks: Long = 0
    public var badRoutes: Long = 0
    public var noAck: Long = 0
    public var netBusy: Long = 0
    public var notIdle: Long = 0
    public var nondelivery: Long = 0
    public var routedBusy: Long = 0
    public var broadcastReadCnt: Long = 0
    public var broadcastWriteCnt: Long = 0
}
