/*
 * This file is part of HomebankCsvConverter.
 * Copyright (C) 2024 Raik Bieniek <raik@bieniek-ti.de>
 *
 * HomebankCsvConverter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HomebankCsvConverter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HomebankCsvConverter.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.voidnode.homebankCsvConverter

import kotlin.text.Regex
import kotlin.text.MatchGroupCollection
import java.time.LocalDate

private val commerzbankCsvLine = Regex("([^;]*);([0-9]+)\\.([0-9]+)\\.([0-9]+);([^;]*);\"([^;]*)\";(-?)([0-9]+),([0-9]+);([A-Z]*);([0-9]*);([0-9]*);([A-Za-z0-9]*)(;([^;]*))?")

/**
* Converts CSV files exported from the website of the commerzbank to [Transaction]s.
*/
fun readCommerzbankCsv(lines: List<String>) : List<Transaction> {
	// skip the first entry as it is the csv header
	return lines.drop(1).map(::convertCommerzbankCsvLine)
}

private fun convertCommerzbankCsvLine(line: String) : Transaction {
	val matcher = commerzbankCsvLine.matchEntire(line)
	if(matcher != null) {
		val date = readDate(matcher.groups)
		val paymentType = matcher.groups[5]?.value?.let { readPaymentType(it) }
		val postingText = matcher.groups[6]
		val money = readMoney(matcher.groups)
		if(date != null && postingText != null && money != null) {
			return Transaction(date, postingText.value, money, paymentType)
		}
	}
	throw IllegalArgumentException("The following line in the CSV file did not match the Commerzbank CSV pattern:\n$line")
}

private fun readMoney(groups: MatchGroupCollection) : Money? {
	val invert = if(groups[7]?.value == "-") -1 else 1
	val major = groups[8]
	val minor = groups[9]

	if(major != null && minor != null) {
		return Money( invert * major.value.toLong(), minor.value.toLong())
	}
	return null
}

private fun readPaymentType(rawType: String) : PaymentType? {
	return when (rawType ){
		"Überweisung" -> PaymentType.TRANSFER
		"Zinsen/Entgelte" -> PaymentType.INTEREST_OR_FEE
		"Einzahlung/Auszahlung" -> PaymentType.DEPOSIT_OR_WITHDRAWAL
		"Lastschrift" -> PaymentType.DIRECT_WITHDRAWL
		"Gutschrift" -> PaymentType.CREDIT_NOTE
		"Dauerauftrag" -> PaymentType.STANDING_ORDER
		else -> null 
	}
}

private fun readDate(groups: MatchGroupCollection) : LocalDate? {
	val day = groups[2]?.value?.toInt()
	val month =  groups[3]?.value?.toInt()
	val year = groups[4]?.value?.toInt()
	
	if(year != null && month != null && day != null) {
		return LocalDate.of(year, month, day)
	}
	return null
}