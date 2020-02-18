import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//https://docs.gradle.org/current/userguide/working_with_files.html#sec:creating_uber_jar_example

plugins {
	id("org.springframework.boot") version "2.2.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
	kotlin("plugin.jpa") version "1.3.61"
}

group = "com.main.app"
version = "0.0.2"
java.sourceCompatibility = JavaVersion.VERSION_13

val developmentOnly by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
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

tasks.getByName<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
	main = "com.main.app.FoodbuddyApplicationKt"
}

tasks.getByName<Jar>("jar") {
	enabled = true
	manifest {
		attributes["Main-Class"] = "com.main.app.FoodbuddyApplicationKt"
	}

	dependsOn(configurations.runtimeClasspath)
	from({
		configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
	})
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	classifier = "boot"
	mainClassName = "com.main.app.FoodbuddyApplicationKt"
	launchScript()
}

sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
	kotlin.srcDir("src/main/kotlin")
}
