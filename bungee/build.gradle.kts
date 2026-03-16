plugins {
    id("com.gradleup.shadow")
}

dependencies {
    implementation(project(":core"))
    compileOnly("net.md-5:bungeecord-api:1.21-R0.4")
}

tasks {
    build {
        dependsOn("shadowJar")
    }
}
