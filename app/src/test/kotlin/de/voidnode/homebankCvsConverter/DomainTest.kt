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

internal class DomainTest {
	@Test
	fun toStringWorksAsExpected() {
		// given
		val money = listOf(Money(58, 92), Money(-60), Money(0, 0))
		
		// when
		val serialized = money.map { it.toString() }
		
		// should be converted correctly
		assertEquals(listOf("58,92", "-0,60", "0,00"), serialized)
	}
}