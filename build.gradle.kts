import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		maven("https://plugins.gradle.org/m2/")
		mavenCentral()
	}
}

plugins {
	id("org.springframework.boot") version "2.3.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.4.10"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.4.10"

	kotlin("jvm") version "1.4.10"
	kotlin("plugin.spring") version "1.4.10"
	kotlin("plugin.jpa") version "1.4.10"

	kotlin("kapt") version "1.4.10" // annotation processing을 위한 kapt

	idea
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

group = "jpashop"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("com.querydsl:querydsl-core") // querydsl
	implementation("com.querydsl:querydsl-jpa") // querydsl

	//    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
	//    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

	kapt("com.querydsl:querydsl-apt:4.2.2:jpa")
	kapt("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")

	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

idea {
	module {
		val kaptMain = file("build/generated/source/kapt/main")
		sourceDirs.add(kaptMain)
		generatedSourceDirs.add(kaptMain)
	}
}
