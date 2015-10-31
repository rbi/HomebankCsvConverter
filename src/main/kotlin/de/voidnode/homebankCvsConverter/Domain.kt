/*
 * This file is part of HomebankCvsConverter.
 * Copyright (C) 2015 Raik Bieniek <raik@voidnode.de>
 *
 * HomebankCvsConverter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HomebankCvsConverter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HomebankCvsConverter.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.voidnode.homebankCvsConverter

class Transaction(val postingText: String, val money: Money) {
	
}

/**
 * A given amount of money.
 */
data class Money(val raw: Long) {
	
	constructor(major: Long, minor: Long) : this((if(major < 0) -1 else 1) * (Math.abs(major) * 100 + minor))
	
	/**
	 * The major part of the money.
	 */
	val major: Long
		get() = raw / 100
	
	/**
	 * The minor part of the mony (e.g. the cent part).
	 */
	val minor: Long
		get() = raw % 100
	
}