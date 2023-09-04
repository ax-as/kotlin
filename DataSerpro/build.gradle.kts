plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "com.github.ax-as"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    dependencies {
        implementation("org.jetbrains.kotlinx:dataframe:0.11.0")
        // https://mvnrepository.com/artifact/technology.tabula/tabula
        implementation("org.apache.pdfbox:pdfbox:2.0.29")

        // https://mvnrepository.com/artifact/org.apache.commons/commons-text
//    implementation("org.apache.commons:commons-text:1.11.1")

        implementation("technology.tabula:tabula:1.0.5")
        implementation("com.github.ajalt.clikt:clikt:4.2.0")
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("com.github.ax_as.dataserpro.MainKt")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE  // Estratégia para lidar com entradas duplicadas

    manifest {
        attributes["Main-Class"] = "com.github.ax_as.dataserpro.MainKt"  // Substitua pelo nome da sua classe principal
    }
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/*.RSA")
}