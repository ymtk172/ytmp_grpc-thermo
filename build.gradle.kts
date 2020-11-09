import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//gRPC
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id

plugins {
	id("org.springframework.boot") version "2.3.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	id("com.google.protobuf") version "0.8.12"
}

group = "com.yama-lc.ytmp.grpc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
	maven {
		url = uri("https://plugins.gradle.org/m2/")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	//gRPC
	implementation("io.grpc:grpc-protobuf:1.25.0")
	implementation("io.grpc:grpc-stub:1.25.0")
	implementation("io.grpc:grpc-netty:1.25.0")
	//user-apiで使う
	//implementation("io.github.lognet:grpc-spring-boot-starter:3.3.0")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.11.0"
	}
	plugins{
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.25.0"
		}
	}
	// generate build/generated/source/proto/main/grpc/${package_path}/ThermoGrpc
	generateProtoTasks {
		val protoConfig = file("protoConfig.asciipb")
		all().forEach {
			it.inputs.files.plus(protoConfig)
			it.plugins {
				id("grpc") {}
			}
		}
	}
	sourceSets {
		main {
			java {
				srcDir("$buildDir/generated/source/proto/main/java")
				srcDir("$buildDir/generated/source/proto/main/grpc")
			}
		}
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
