# Virtual Schema for Elasticsearch 4.0.2, released 2026-??-??

Code name: Fixed vulnerabilities CVE-2026-19032, CVE-2026-68497

## Summary

This release fixes the following 2 vulnerabilities:

### CVE-2026-19032 (CWE-470) in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
com.fasterxml.jackson.core/jackson-databind - Unrestricted URI schemes in Path deserialization
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-19032?component-type=maven&component-name=tools.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-19032
* https://github.com/FasterXML/jackson-databind/pull/6129

### CVE-2026-68497 (CWE-770) in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
jackson-databind - Allocation of Resources Without Limits or Throttling
#### References
* https://guide.sonatype.com/vulnerability/CVE-2026-68497?component-type=maven&component-name=tools.jackson.core%2Fjackson-databind&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2026-68497
* https://github.com/FasterXML/jackson-databind/pull/6127

## Security

* #97: Fixed vulnerability CVE-2026-19032 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`
* #98: Fixed vulnerability CVE-2026-68497 in dependency `tools.jackson.core:jackson-databind:jar:3.2.1:test`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:virtual-schema-common-jdbc:14.0.4` to `14.0.5`

### Test Dependency Updates

* Updated `co.elastic.clients:elasticsearch-java:8.19.19` to `9.5.1`
* Updated `org.eclipse:yasson:3.0.4` to `3.0.5`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:8.19.19` to `9.5.1`
* Updated `org.junit.jupiter:junit-jupiter:5.14.4` to `6.1.3`
