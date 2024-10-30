plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktfmt)
    application
}

repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(libs.guava)
    implementation(libs.capstone)
    implementation(libs.gson)
    implementation(libs.slf4j)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest("2.0.0")
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.updater.UpdaterKt"
}

ktfmt {
    kotlinLangStyle()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    archiveBaseName.set("Updater")
    archiveVersion.set("$version")
}

// Im not putting ktfmtFormat every time, wtf
tasks.register("fmt") {
    dependsOn(tasks.ktfmtFormat)
}

tasks.named("build") {
    dependsOn(tasks.jar)
}
