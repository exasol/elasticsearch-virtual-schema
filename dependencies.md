<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                      | License          |
| ------------------------------- | ---------------- |
| [Virtual Schema Common JDBC][0] | [MIT License][1] |
| [error-reporting-java][2]       | [MIT][3]         |

## Test Dependencies

| Dependency                                      | License                                        |
| ----------------------------------------------- | ---------------------------------------------- |
| [Hamcrest][4]                                   | [BSD License 3][5]                             |
| [JUnit Jupiter (Aggregator)][6]                 | [Eclipse Public License v2.0][7]               |
| [mockito-junit-jupiter][8]                      | [The MIT License][9]                           |
| [rest-high-level][10]                           | [Elastic License 2.0][11]                      |
| [jdbc][10]                                      | [Elastic License 2.0][11]                      |
| [Testcontainers :: JUnit Jupiter Extension][12] | [MIT][13]                                      |
| [TestContainers :: elasticsearch][12]           | [MIT][13]                                      |
| [Test containers for Exasol on Docker][14]      | [MIT][3]                                       |
| [Test Database Builder for Java][15]            | [MIT License][16]                              |
| [Matcher for SQL Result Sets][17]               | [MIT][3]                                       |
| [udf-debugging-java][18]                        | [MIT][3]                                       |
| [Apache HttpClient][19]                         | [Apache License, Version 2.0][20]              |
| [Apache Log4j API][21]                          | [Apache License, Version 2.0][22]              |
| [Jackson dataformat: CBOR][23]                  | [The Apache Software License, Version 2.0][20] |
| [Jackson dataformat: Smile][23]                 | [The Apache Software License, Version 2.0][20] |
| [Jackson-dataformat-YAML][24]                   | [The Apache Software License, Version 2.0][20] |
| [Apache Commons Codec][25]                      | [Apache License, Version 2.0][22]              |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][26]                       | [GNU LGPL 3][27]                               |
| [Apache Maven Compiler Plugin][28]                      | [Apache License, Version 2.0][22]              |
| [Apache Maven Enforcer Plugin][29]                      | [Apache License, Version 2.0][22]              |
| [Maven Flatten Plugin][30]                              | [Apache Software Licenese][20]                 |
| [Project keeper maven plugin][31]                       | [The MIT License][32]                          |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][33] | [ASL2][20]                                     |
| [Reproducible Build Maven Plugin][34]                   | [Apache 2.0][20]                               |
| [Maven Surefire Plugin][35]                             | [Apache License, Version 2.0][22]              |
| [Versions Maven Plugin][36]                             | [Apache License, Version 2.0][22]              |
| [Apache Maven Assembly Plugin][37]                      | [Apache License, Version 2.0][22]              |
| [Apache Maven JAR Plugin][38]                           | [Apache License, Version 2.0][22]              |
| [Apache Maven Dependency Plugin][39]                    | [Apache License, Version 2.0][22]              |
| [Artifact reference checker and unifier][40]            | [MIT][3]                                       |
| [Maven Failsafe Plugin][41]                             | [Apache License, Version 2.0][22]              |
| [JaCoCo :: Maven Plugin][42]                            | [Eclipse Public License 2.0][43]               |
| [error-code-crawler-maven-plugin][44]                   | [MIT][3]                                       |
| [Maven Clean Plugin][45]                                | [The Apache Software License, Version 2.0][20] |
| [Maven Resources Plugin][46]                            | [The Apache Software License, Version 2.0][20] |
| [Maven Install Plugin][47]                              | [The Apache Software License, Version 2.0][20] |
| [Maven Deploy Plugin][48]                               | [The Apache Software License, Version 2.0][20] |
| [Maven Site Plugin 3][49]                               | [The Apache Software License, Version 2.0][20] |

[0]: https://github.com/exasol/virtual-schema-common-jdbc/
[1]: https://github.com/exasol/virtual-schema-common-jdbc/blob/main/LICENSE
[2]: https://github.com/exasol/error-reporting-java
[3]: https://opensource.org/licenses/MIT
[4]: http://hamcrest.org/JavaHamcrest/
[5]: http://opensource.org/licenses/BSD-3-Clause
[6]: https://junit.org/junit5/
[7]: https://www.eclipse.org/legal/epl-v20.html
[8]: https://github.com/mockito/mockito
[9]: https://github.com/mockito/mockito/blob/main/LICENSE
[10]: https://github.com/elastic/elasticsearch
[11]: https://raw.githubusercontent.com/elastic/elasticsearch/v7.17.5/licenses/ELASTIC-LICENSE-2.0.txt
[12]: https://testcontainers.org
[13]: http://opensource.org/licenses/MIT
[14]: https://github.com/exasol/exasol-testcontainers
[15]: https://github.com/exasol/test-db-builder-java/
[16]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[17]: https://github.com/exasol/hamcrest-resultset-matcher
[18]: https://github.com/exasol/udf-debugging-java/
[19]: http://hc.apache.org/httpcomponents-client
[20]: http://www.apache.org/licenses/LICENSE-2.0.txt
[21]: https://logging.apache.org/log4j/2.x/log4j-api/
[22]: https://www.apache.org/licenses/LICENSE-2.0.txt
[23]: http://github.com/FasterXML/jackson-dataformats-binary
[24]: https://github.com/FasterXML/jackson-dataformats-text
[25]: https://commons.apache.org/proper/commons-codec/
[26]: http://sonarsource.github.io/sonar-scanner-maven/
[27]: http://www.gnu.org/licenses/lgpl.txt
[28]: https://maven.apache.org/plugins/maven-compiler-plugin/
[29]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[30]: https://www.mojohaus.org/flatten-maven-plugin/
[31]: https://github.com/exasol/project-keeper/
[32]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[33]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[34]: http://zlika.github.io/reproducible-build-maven-plugin
[35]: https://maven.apache.org/surefire/maven-surefire-plugin/
[36]: http://www.mojohaus.org/versions-maven-plugin/
[37]: https://maven.apache.org/plugins/maven-assembly-plugin/
[38]: https://maven.apache.org/plugins/maven-jar-plugin/
[39]: https://maven.apache.org/plugins/maven-dependency-plugin/
[40]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[41]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[42]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[43]: https://www.eclipse.org/legal/epl-2.0/
[44]: https://github.com/exasol/error-code-crawler-maven-plugin
[45]: http://maven.apache.org/plugins/maven-clean-plugin/
[46]: http://maven.apache.org/plugins/maven-resources-plugin/
[47]: http://maven.apache.org/plugins/maven-install-plugin/
[48]: http://maven.apache.org/plugins/maven-deploy-plugin/
[49]: http://maven.apache.org/plugins/maven-site-plugin/
