import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.jmailen.kotlinter") version "2.4.1"
    id("com.star-zero.gradle.githook") version "1.2.0"
    id("ru.netris.commitlint") version "1.2"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

group = "com.appoutlet"
version = "2.0.0-ALPHA-0"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
	maven(url = "https://repo.spring.io/milestone")
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.0-M1")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springdoc:springdoc-openapi-webflux-ui:1.4.3")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.4.3")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("com.ninja-squad:springmockk:2.0.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(module = "mockito-core")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

githook {
    failOnMissingHooksDir = false
    createHooksDirIfNotExist = false
    hooks {
        create("commit-msg") {
            task = "commitlint -Dmsgfile=\$1"
        }
    }
}
