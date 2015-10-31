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

import kotlin.text.Regex
import kotlin.text.MatchGroupCollection

private val commerzbankCsvLine = Regex("([^;]*);([^;]*);([^;]*);\"([^;]*)\";(-?)([0-9]+),([0-9]+);([A-Z]*);([0-9]*);([0-9]*);([A-Za-z0-9]*)")

/**
* Converts CSV files exported from the website of the commerzbank to [Transaction]s.
*/
public fun readCommerzbankCsv(lines: List<String>) : List<Transaction> {
	// skip the first entry as it is the csv header
	return lines.drop(1).map(::convertCommerzbankCsvLine)
}

private fun convertCommerzbankCsvLine(line: String) : Transaction {
	val matcher = commerzbankCsvLine.matchEntire(line)
	if(matcher != null) {
		val postingText = matcher.groups[4];
		val money = readMoney(matcher.groups);
		if(postingText != null && money != null) {
			return Transaction(postingText.value, money)
		}
	}
	throw IllegalArgumentException("The following line in the CSV file did not match the Commerzbank CSV pattern:\n$line")
}

private fun readMoney(groups: MatchGroupCollection) : Money? {
	val invert = if(groups[5]?.value == "-") -1 else 1
	val major = groups[6]
	val minor = groups[7]

	if(major != null && minor != null) {
		return Money( invert * major.value.toLong(), minor.value.toLong())
	}
	return null
}