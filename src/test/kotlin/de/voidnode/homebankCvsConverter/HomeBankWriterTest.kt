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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset
import org.jetbrains.spek.api.Spek
import kotlin.test.*

class HomeBankWriter: Spek() { init {
	given("A list of transactions") {
		val transactions = listOf(Transaction("Einzahlung", Money(43,59)),
				Transaction("Internet DSL", Money(-45,0)), Transaction("Something",Money(0,-53)))
				
		on("passing the transactions to the HomeBankWriter") {
			val csvLines = serializeHomeBankCsv(transactions)
					
			it("should serialize them to CSV lines HomeBank can import") {
				val testDataSource = HomeBankWriter::javaClass.javaClass.getResource("/hombank-testdata.csv")
				val testData = Files.readAllLines(Paths.get(testDataSource.getPath()), Charset.forName("UTF-8"))
				
				for(i in 0..testData.size - 1) {
					assertEquals(testData[i], csvLines[i])
				}
			}
		}
	}
}}