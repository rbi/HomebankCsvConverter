# About
HomebankCsvConverter is a small tool to convert CSV files with transactions exported from the Commerzbank home banking website into CSV files that can be imported into the [HomeBank](http://homebank.free.fr/) application. It is mostly a playground for experimenting with [Kotlin](https://kotlinlang.org/) and [Gradle](http://gradle.org/) but its actually working.

# Usage
HomebankCsvConverter needs [Java](https://www.java.com/de/download/) with Version 8 or higher to be installed on the computer. The tool is a command line only application that can be downloaded at the releases tab. It needs to be called with the following arguments.


    homebankCsvConverter <input CSV> <output CSV>
        <input CSV> The CVS file exported from the website of the bank.
        <output CSV> The destination path where the CSV file to import into HomeBank should be stored.

# License
HomebankCsvConverter is released under the [GPLv3](http://www.gnu.org/licenses/gpl-3.0.de.html) license.