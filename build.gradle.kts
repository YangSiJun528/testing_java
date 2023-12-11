plugins {
    id("java")
}

group = "dev.yangsijun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation(platform("org.junit:junit-bom:5.9.1"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
    //annotationProcessor("dev.yangsijun.field_specific_builder:specific-builder-processor:1.0")
    //compileOnly("dev.yangsijun.field_specific_builder:specific-builder-annotations:1.0")
}

tasks.test {
    useJUnitPlatform()
}
