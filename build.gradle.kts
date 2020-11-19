import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.cloud.contract.verifier.config.TestFramework
import org.springframework.cloud.contract.verifier.config.TestMode

plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot") version "2.3.3.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("org.springframework.cloud.contract")
}

group = "de.company"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

contracts {
	testFramework.set(TestFramework.JUNIT5)
	testMode.set(TestMode.WEBTESTCLIENT)
	baseClassForTests.set("de.company.demosender.function.DemoSenderBaseClass")
	failOnInProgress.set(false)
}

repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR8")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("io.github.microutils:kotlin-logging:1.8.3")
	implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.cloud:spring-cloud-contract-verifier")
	testImplementation("org.springframework.cloud:spring-cloud-stream") {
		artifact {
			name = "spring-cloud-stream"
			extension = "jar"
			type ="test-jar"
			classifier = "test-binder"
		}
	}

	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
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
