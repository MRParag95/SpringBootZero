import com.google.protobuf.gradle.id

import org.jooq.meta.jaxb.GeneratedSerialVersionUID
import org.jooq.meta.jaxb.Logging

plugins {
    java
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "7.2.1.Final"
    id("org.graalvm.buildtools.native") version "0.11.4"
    id("gg.jte.gradle") version "3.1.16"
    id("com.google.protobuf") version "0.9.5"

    id("io.freefair.aspectj.post-compile-weaving") version "9.2.0"

    id("org.jooq.trial.jooq-codegen-gradle") version "3.20.10"
}

group = "org.thezerobytehunter"
version = "1.0.0"
description = "SpringBootZero"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

extra["springGrpcVersion"] = "1.0.1"
extra["springShellVersion"] = "4.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-batch-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jooq") {
        exclude(group = "org.jooq", module = "jooq")
    }
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-opentelemetry")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("gg.jte:jte-spring-boot-starter-3:3.1.16")
    implementation("io.grpc:grpc-services")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.springframework.grpc:spring-grpc-server-web-spring-boot-starter")
    implementation("org.springframework.shell:spring-shell-starter")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
    testImplementation("org.springframework.boot:spring-boot-starter-batch-jdbc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-batch-test")
    testImplementation("org.springframework.boot:spring-boot-starter-cache-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jdbc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
    testImplementation("org.springframework.boot:spring-boot-starter-jdbc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-jooq-test")
    testImplementation("org.springframework.boot:spring-boot-starter-mail-test")
    testImplementation("org.springframework.boot:spring-boot-starter-opentelemetry-test")
    testImplementation("org.springframework.boot:spring-boot-starter-quartz-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.grpc:spring-grpc-test")
    testImplementation("org.springframework.shell:spring-shell-starter-test")
    implementation("org.springframework.shell:spring-shell-jline")
    testImplementation("org.testcontainers:testcontainers-grafana")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.testcontainers:testcontainers-postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.fasterxml.uuid:java-uuid-generator:5.2.0")

    implementation("org.aspectj:aspectjrt:1.9.25.1")

    implementation("io.hypersistence:hypersistence-optimizer:2.13.0-trial") {
        artifact {
            classifier = "jakarta"
        }
    }
    implementation("io.hypersistence:hypersistence-utils-hibernate-71:3.14.1")

    implementation("org.jooq.trial:jooq:3.20.10")
    implementation("org.jooq.trial:jooq-meta:3.20.10")
    implementation("org.jooq.trial:jooq-checker:3.20.10")
    implementation("org.jooq.trial:jooq-meta-extensions:3.20.10")
    implementation("org.jooq.trial:jooq-postgres-extensions:3.20.10")
    implementation("org.jooq.trial:jooq-jackson-extensions:3.20.10")
    jooqCodegen("org.postgresql:postgresql")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.shell:spring-shell-dependencies:${property("springShellVersion")}")
        mavenBom("org.springframework.grpc:spring-grpc-dependencies:${property("springGrpcVersion")}")
    }
}

hibernate {
    enhancement {
        enableAssociationManagement = true
    }
}

jte {
    generate()
    binaryStaticContent = true
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc") {
                    option("@generated=omit")
                }
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jooq {
    configuration {
        logging = Logging.DEBUG // TODO: NEED TO CHANGE IT TO WARN WHEN WENT TO PRODUCTION
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/SpringBootZero?reWriteBatchedInserts=true"
            user = "Mendax47"
            password = "Mendax47"
        }
        generator {
            name = "org.jooq.codegen.JavaGenerator"
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
                includes = ".*"
                excludes = "flyway_schema_history|event_publication"
            }
            generate {
                isTables = true

                isRecords = true

                isDaos = true

                isPojos = true
                isPojosToString = true
                isPojosEqualsAndHashCode = true

                isFluentSetters = true

                isJavaTimeTypes = true

                generatedSerialVersionUID = GeneratedSerialVersionUID.CONSTANT

                isImplicitJoinPathsToOne = true
                isImplicitJoinPathsToMany = true
                isImplicitJoinPathsManyToMany = true
                isImplicitJoinPathsUseTableNameForUnambiguousFKs = true

                isSequences = true
                isTriggers = true
            }
            target {
                packageName = "com.thezerobytehunters.springbootzero.jooq"
            }
            strategy {
                name = "org.jooq.codegen.DefaultGeneratorStrategy"
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDir("build/generated-src/jooq/main")
        }
    }
}

tasks.named("compileJava") {
    dependsOn(tasks.named("jooqCodegen"))
}