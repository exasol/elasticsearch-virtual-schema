# Virtual Schema for Elasticsearch 4.0.2, released 2026-??-??

Code name: Fixed vulnerabilities CVE-2026-91776, CVE-2026-91777, CVE-2026-83557

## Summary

This release fixes the following 3 vulnerabilities:

### CVE-2026-91776 (CWE-770) in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
Jackson-databind - Unbounded growth of type id cache in TypeDeserializer
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-91776?component-type=maven&component-name=tools.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-91776
* https://github.com/FasterXML/jackson-databind/pull/6203

### CVE-2026-91777 (CWE-407) in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
Jackson-databind - Denial of Service (DoS)
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-91777?component-type=maven&component-name=tools.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-91777
* https://github.com/FasterXML/jackson-databind/pull/6204

### CVE-2026-83557 (CWE-502) in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
jackson-databind - Deserialization of untrusted data [CVE-2026-83557]
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-83557?component-type=maven&component-name=tools.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-83557
* https://github.com/FasterXML/jackson-databind/issues/6156
* https://github.com/FasterXML/jackson-databind/security/advisories/GHSA-gx83-3vf8-gh7j

## Security

* #103: Fixed vulnerability CVE-2026-91776 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
* #104: Fixed vulnerability CVE-2026-91777 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
* #105: Fixed vulnerability CVE-2026-83557 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:virtual-schema-common-jdbc:14.0.4` to `14.0.5`

### Test Dependency Updates

* Updated `co.elastic.clients:elasticsearch-java:8.19.19` to `9.5.4`
* Updated `com.exasol:exasol-testcontainers:8.0.1` to `8.0.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.7.3` to `1.7.4`
* Updated `com.exasol:test-db-builder-java:4.0.2` to `4.0.3`
* Updated `org.eclipse:yasson:3.0.4` to `3.0.5`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:8.19.19` to `9.5.4`
* Updated `org.junit.jupiter:junit-jupiter:5.14.4` to `6.1.3`
* Updated `org.slf4j:slf4j-jdk14:2.0.18` to `2.0.19`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:5.7.4` to `5.7.6`
