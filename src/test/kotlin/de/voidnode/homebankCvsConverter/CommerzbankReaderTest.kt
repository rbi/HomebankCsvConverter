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

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import org.jetbrains.spek.api.Spek
import kotlin.test.*

class CommerzbankReaderTest: Spek() { init {
	given("A CSV export of transaction from the Commerzbank website") {
		val testData = CommerzbankReaderTest::javaClass.javaClass.getResourceAsStream("/commerzbank-export-testdata.csv")

		on("passing the CSV to the CommerzbankReader") {
			val transactions = readCommerzbankCsv(testData.reader().useLines { it.toArrayList() })
			
			it("should read all transactions from it") {
				assertEquals(3, transactions.size)	
			}
			
			it("should read the posting text correctly") {
				assertEquals("Test Lastschrift", transactions[0].postingText)
				assertEquals("Some Zinsen", transactions[1].postingText)
				assertEquals("eingezahlt", transactions[2].postingText)
			}
			
			it("should read transfered money correctly") {
				assertEquals(Money(-5000), transactions[0].money)
				assertEquals(Money(-795), transactions[1].money)
				assertEquals(Money(192469), transactions[2].money)
			}
		}
	}
}}