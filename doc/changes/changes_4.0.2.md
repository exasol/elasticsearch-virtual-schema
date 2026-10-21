# Virtual Schema for Elasticsearch 4.0.2, released 2026-10-21

Code name: Security Fixes

## Summary

This release fixes many vulnerabilities in test dependencies and stabilizes DST-sensitive integration tests.

## Bugfixes

* #84: Stabilize DST-sensitive integration tests

## Security

* #97: Fixed vulnerability CVE-2026-19032 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
* #98: Fixed vulnerability CVE-2026-68497 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
* #100: Fixed vulnerability CVE-2026-86231 in dependency `com.github.mwiede:jsch:jar:2.28.5:test`
* #101: Fixed vulnerability CVE-2026-68498 in dependency `tools.jackson.core:jackson-core:jar:3.2.1:test`
* #103: Fixed vulnerability CVE-2026-91776 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
* #104: Fixed vulnerability CVE-2026-91777 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
* #105: Fixed vulnerability CVE-2026-83557 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
* #107: Fixed vulnerability CVE-2026-89407 in dependency `tools.jackson.core:jackson-core:jar:3.2.1:test`
* #108: Fixed vulnerability CVE-2026-89425 in dependency `tools.jackson.core:jackson-core:jar:3.2.1:test`
* #110: Fixed vulnerability CVE-2026-110262 in dependency `org.ow2.asm:asm-commons:jar:9.10.1:test`
* #111: Fixed vulnerability CVE-2026-110270 in dependency `org.ow2.asm:asm-commons:jar:9.10.1:test`
* #112: Fixed vulnerability CVE-2026-110257 in dependency `org.ow2.asm:asm:jar:9.10.1:test`
* #113: Fixed vulnerability CVE-2026-110258 in dependency `org.ow2.asm:asm:jar:9.10.1:test`
* #114: Fixed vulnerability CVE-2026-110259 in dependency `org.ow2.asm:asm:jar:9.10.1:test`
* #115: Fixed vulnerability CVE-2026-110260 in dependency `org.ow2.asm:asm:jar:9.10.1:test`
* #116: Fixed vulnerability CVE-2026-110261 in dependency `org.ow2.asm:asm:jar:9.10.1:test`
* #117: Fixed vulnerability CVE-2026-110263 in dependency `org.ow2.asm:asm:jar:9.10.1:test`
* Fixed vulnerability CVE-2026-110879 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:virtual-schema-common-jdbc:14.0.4` to `14.1.0`

### Test Dependency Updates

* Updated `co.elastic.clients:elasticsearch-java:8.19.19` to `8.19.23`
* Updated `com.exasol:exasol-testcontainers:8.0.1` to `8.0.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.7.3` to `1.7.4`
* Updated `com.exasol:test-db-builder-java:4.0.2` to `4.0.3`
* Updated `com.exasol:udf-debugging-java:0.6.20` to `0.6.22`
* Added `org.eclipse.yasson:yasson:3.0.5`
* Removed `org.eclipse:yasson:3.0.4`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:8.19.19` to `8.19.23`
* Updated `org.mockito:mockito-junit-jupiter:5.23.0` to `5.24.0`
* Updated `org.slf4j:slf4j-jdk14:2.0.18` to `2.0.20`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.1.0` to `2.1.2`
* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.8.0`
* Updated `io.github.git-commit-id:git-commit-id-maven-plugin:10.0.0` to `10.0.1`
* Updated `org.apache.maven.plugins:maven-artifact-plugin:3.6.1` to `3.7.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.15.0` to `3.16.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.5.6` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-install-plugin:3.1.4` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.5.0` to `3.5.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.6` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.2.0` to `3.3.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.7.3` to `1.8.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.21.0` to `2.23.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:5.7.0.6970` to `5.8.0.7211`
