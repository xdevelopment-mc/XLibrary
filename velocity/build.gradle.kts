plugins {
    id("com.gradleup.shadow")
}

dependencies {
    implementation(project(":XLibrary-core"))
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
}

tasks {
    build {
        dependsOn("shadowJar")
    }
}
