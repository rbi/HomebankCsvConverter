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

import kotlin.test.*
import java.time.LocalDate

internal class CommerzbankReaderTest {

	@Test
	fun parsesCommerzbankCsvCorrectly() {
		// given
		val testData = CommerzbankReaderTest::javaClass.javaClass.getResourceAsStream("/commerzbank-export-testdata.csv")

		// when
		val transactions = readCommerzbankCsv(testData.reader().readLines())

		// then
		//should read all transactions from it
		assertEquals(4, transactions.size)
			
		// should read the date correctly
		assertEquals(LocalDate.of(2015, 10, 23), transactions[0].date)
		assertEquals(LocalDate.of(2015, 10, 14), transactions[1].date)
		assertEquals(LocalDate.of(2015, 10, 12), transactions[2].date)
		assertEquals(LocalDate.of(2024, 3, 1), transactions[3].date)
			
		// should read the posting text correctly
		assertEquals("Test Lastschrift", transactions[0].postingText)
		assertEquals("Some Zinsen", transactions[1].postingText)
		assertEquals("eingezahlt", transactions[2].postingText)
		assertEquals("xxx", transactions[3].postingText)
		
		// should read transfered money correctly
		assertEquals(Money(-5000), transactions[0].money)
		assertEquals(Money(-795), transactions[1].money)
		assertEquals(Money(192469), transactions[2].money)
		assertEquals(Money(0), transactions[3].money)
		
		// should read the payment method correctly
		assertEquals(PaymentType.TRANSFER, transactions[0].paymentType)
		assertEquals(PaymentType.INTEREST_OR_FEE, transactions[1].paymentType)
		assertEquals(PaymentType.DEPOSIT_OR_WITHDRAWAL, transactions[2].paymentType)
		assertEquals(PaymentType.INTEREST_OR_FEE, transactions[3].paymentType)
	}
}