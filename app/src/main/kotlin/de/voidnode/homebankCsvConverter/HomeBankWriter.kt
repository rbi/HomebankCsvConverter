/*
 * This file is part of HomebankCsvConverter.
 * Copyright (C) 2015 Raik Bieniek <raik@bieniek-it.de>
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

import java.time.format.DateTimeFormatter

/**
* Converts [Transaction]s to CSV lines that can be imported into HomeBank.
*/
fun serializeHomeBankCsv(transactions: List<Transaction>) : List<String> {
	return transactions.map(::serializeTranscation)
}

private fun serializeTranscation(transaction: Transaction) : String {
	val date = transaction.date.format(DateTimeFormatter.ofPattern("dd-MM-yy"))
	val paymentType = serializePaymentType(transaction.paymentType)
	return "$date;$paymentType;;;${transaction.postingText};${transaction.money};;"
}

private fun serializePaymentType(paymentType: PaymentType?) : String {
	return when(paymentType) {
		PaymentType.CREDIT_NOTE -> "4"
		PaymentType.TRANSFER -> "4"
		PaymentType.STANDING_ORDER -> "7"
		PaymentType.DEPOSIT_OR_WITHDRAWAL -> "9"
		PaymentType.INTEREST_OR_FEE -> "10"
		PaymentType.DIRECT_WITHDRAWL -> "11"
		else -> "0"
	}
}