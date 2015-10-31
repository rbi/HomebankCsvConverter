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

import java.time.format.DateTimeFormatter;

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
		PaymentType.TRANSFER -> "4" 
		PaymentType.DEPOSIT_OR_WITHDRAWAL -> "9"
		PaymentType.INTEREST_OR_FEE -> "10"
		else -> "0"
	}
}