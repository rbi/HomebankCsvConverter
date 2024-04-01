plugins {
  alias(libs.plugins.jvm)
  application
  alias(libs.plugins.versioning)
}

repositories {
  mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
  applicationName = "homebankCsvConverter"
  mainClass = "de.voidnode.homebankCsvConverter.Commerzbank2HomebankKt"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

versioning {
   releaseMode = "snapshot"
}
version = versioning.info.display