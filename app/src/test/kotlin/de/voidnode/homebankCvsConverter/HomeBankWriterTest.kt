/*
 * This file is part of HomebaHomebankCsvConverternkCvsConverter.
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

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset
import kotlin.test.*
import java.time.LocalDate



internal class HomeBankWriter {
	@Test
	fun writesTransactionsCorrectly() {
		// given
		val transactions = listOf(
				Transaction(LocalDate.of(2015, 4, 9), "Einzahlung", Money(43,59), PaymentType.TRANSFER),
				Transaction(LocalDate.of(1998, 6, 9), "Internet DSL", Money(-45,0), PaymentType.INTEREST_OR_FEE),
				Transaction(LocalDate.of(2010, 11, 30), "Something",Money(0,-53)))
				
		// when
		val csvLines = serializeHomeBankCsv(transactions)
		
		// then
		val testDataSource = HomeBankWriter::javaClass.javaClass.getResource("/hombank-testdata.csv")
		val testData = Files.readAllLines(Paths.get(testDataSource.getPath()), Charset.forName("UTF-8"))
		
		for(i in 0..testData.size - 1) {
			assertEquals(testData[i], csvLines[i])
		}
	}
}