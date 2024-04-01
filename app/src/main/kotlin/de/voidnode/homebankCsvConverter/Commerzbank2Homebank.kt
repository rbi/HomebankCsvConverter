/*
 * This file is part of HomebankCsvConverter.
 * Copyright (C) 2024 Raik Bieniek <raik@bieniek-it.de>
 *
 * HomebankCsvConverter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * homebankCsvConverter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with homebankCsvConverter.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.voidnode.homebankCsvConverter

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;


fun main(args : Array<String>) {
	if(args.size != 2) {
		printHelp()
		return
	}

	val input = Paths.get(args.get(0));
	val output = Paths.get(args.get(1));

	if(!Files.exists(input)) {
		printHelp()
		return
	}

	convert(input, output)
}

/**
* Converts a CVS file exported from the Commerzbank page into a CVS file that can be imported into the Homebank application.
*/
private fun convert(input: Path, output: Path) {
	val utf8 = Charset.forName("UTF-8");
	val inputLines = Files.readAllLines(input, utf8)
	val transactions = readCommerzbankCsv(inputLines)
	val outputLines = serializeHomeBankCsv(transactions)
	Files.write(output, outputLines, utf8)
}

private fun printHelp() {
	println("Usage: homebankCsvConverter <input CSV> <output CSV>")
	println("    <input CSV> The CVS file exported from the website of the bank.")
	println("    <output CSV> The destination path where the CSV file to import into HomeBank should be stored.")
}