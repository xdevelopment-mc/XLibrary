plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0" apply false
    id("xyz.jpenilla.run-paper") version "2.3.1" apply false
    id("xyz.jpenilla.run-velocity") version "2.3.1" apply false
}

allprojects {
    group = "net.xdevelopment"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

subprojects {
    apply(plugin = "java")

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    val lombok = project.findProperty("lombok_version")?.toString() ?: "1.18.42"
    val annotations = project.findProperty("annotations_version")?.toString() ?: "26.0.2"
    val fastutil = project.findProperty("fastutil_version")?.toString() ?: "8.5.12"

    dependencies {
        compileOnly("org.projectlombok:lombok:$lombok")
        annotationProcessor("org.projectlombok:lombok:$lombok")
        compileOnly("org.jetbrains:annotations:$annotations")
        compileOnly("it.unimi.dsi:fastutil:$fastutil")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(21)
    }
}
