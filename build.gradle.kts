plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "6.22.0"
}

group = "app.zhc1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.hibernate.search:hibernate-search-bom:7.2.2.Final")
    }
}

dependencies {
    implementation("org.hibernate.search:hibernate-search-mapper-orm")
    implementation("org.hibernate.search:hibernate-search-backend-lucene")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    java {
        palantirJavaFormat("2.38.0")
        indentWithSpaces()
        formatAnnotations()
        removeUnusedImports()
        trimTrailingWhitespace()
    }

    kotlin {
        ktlint()
        indentWithSpaces()
        trimTrailingWhitespace()
    }

    kotlinGradle {
        ktlint()
        indentWithSpaces()
        trimTrailingWhitespace()
    }
}
