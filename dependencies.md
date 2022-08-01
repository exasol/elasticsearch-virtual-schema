<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                      | License          |
| ------------------------------- | ---------------- |
| [Virtual Schema Common JDBC][0] | [MIT License][1] |
| [error-reporting-java][2]       | [MIT][3]         |

## Test Dependencies

| Dependency                                      | License                                                                        |
| ----------------------------------------------- | ------------------------------------------------------------------------------ |
| [Hamcrest][4]                                   | [BSD License 3][5]                                                             |
| [JUnit Jupiter (Aggregator)][6]                 | [Eclipse Public License v2.0][7]                                               |
| [mockito-junit-jupiter][8]                      | [The MIT License][9]                                                           |
| [Elasticsearch Java API Client][10]             | [The Apache Software License, Version 2.0][11]                                 |
| [Yasson][12]                                    | [Eclipse Public License v. 2.0][13]; [Eclipse Distribution License v. 1.0][14] |
| [Apache HttpClient][15]                         | [Apache License, Version 2.0][16]                                              |
| [Apache Commons Codec][17]                      | [Apache License, Version 2.0][11]                                              |
| [Testcontainers :: JUnit Jupiter Extension][18] | [MIT][19]                                                                      |
| [TestContainers :: elasticsearch][18]           | [MIT][19]                                                                      |
| [Test containers for Exasol on Docker][20]      | [MIT][3]                                                                       |
| [Test Database Builder for Java][21]            | [MIT License][22]                                                              |
| [Matcher for SQL Result Sets][23]               | [MIT][3]                                                                       |
| [udf-debugging-java][24]                        | [MIT][3]                                                                       |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][25]                       | [GNU LGPL 3][26]                               |
| [Apache Maven Compiler Plugin][27]                      | [Apache License, Version 2.0][11]              |
| [Apache Maven Enforcer Plugin][28]                      | [Apache License, Version 2.0][11]              |
| [Maven Flatten Plugin][29]                              | [Apache Software Licenese][16]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][30] | [ASL2][16]                                     |
| [Reproducible Build Maven Plugin][31]                   | [Apache 2.0][16]                               |
| [Maven Surefire Plugin][32]                             | [Apache License, Version 2.0][11]              |
| [Versions Maven Plugin][33]                             | [Apache License, Version 2.0][11]              |
| [Apache Maven Assembly Plugin][34]                      | [Apache License, Version 2.0][11]              |
| [Apache Maven JAR Plugin][35]                           | [Apache License, Version 2.0][11]              |
| [Apache Maven Dependency Plugin][36]                    | [Apache License, Version 2.0][11]              |
| [Artifact reference checker and unifier][37]            | [MIT][3]                                       |
| [Maven Failsafe Plugin][38]                             | [Apache License, Version 2.0][11]              |
| [JaCoCo :: Maven Plugin][39]                            | [Eclipse Public License 2.0][40]               |
| [error-code-crawler-maven-plugin][41]                   | [MIT][3]                                       |
| [Project keeper maven plugin][42]                       | [The MIT License][43]                          |
| [Maven Clean Plugin][44]                                | [The Apache Software License, Version 2.0][16] |
| [Maven Resources Plugin][45]                            | [The Apache Software License, Version 2.0][16] |
| [Maven Install Plugin][46]                              | [The Apache Software License, Version 2.0][16] |
| [Maven Deploy Plugin][47]                               | [The Apache Software License, Version 2.0][16] |
| [Maven Site Plugin 3][48]                               | [The Apache Software License, Version 2.0][16] |

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
[10]: https://github.com/elastic/elasticsearch-java/
[11]: https://www.apache.org/licenses/LICENSE-2.0.txt
[12]: https://projects.eclipse.org/projects/ee4j.yasson
[13]: http://www.eclipse.org/legal/epl-v20.html
[14]: http://www.eclipse.org/org/documents/edl-v10.php
[15]: http://hc.apache.org/httpcomponents-client
[16]: http://www.apache.org/licenses/LICENSE-2.0.txt
[17]: https://commons.apache.org/proper/commons-codec/
[18]: https://testcontainers.org
[19]: http://opensource.org/licenses/MIT
[20]: https://github.com/exasol/exasol-testcontainers
[21]: https://github.com/exasol/test-db-builder-java/
[22]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[23]: https://github.com/exasol/hamcrest-resultset-matcher
[24]: https://github.com/exasol/udf-debugging-java/
[25]: http://sonarsource.github.io/sonar-scanner-maven/
[26]: http://www.gnu.org/licenses/lgpl.txt
[27]: https://maven.apache.org/plugins/maven-compiler-plugin/
[28]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[29]: https://www.mojohaus.org/flatten-maven-plugin/
[30]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[31]: http://zlika.github.io/reproducible-build-maven-plugin
[32]: https://maven.apache.org/surefire/maven-surefire-plugin/
[33]: http://www.mojohaus.org/versions-maven-plugin/
[34]: https://maven.apache.org/plugins/maven-assembly-plugin/
[35]: https://maven.apache.org/plugins/maven-jar-plugin/
[36]: https://maven.apache.org/plugins/maven-dependency-plugin/
[37]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[38]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[39]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[40]: https://www.eclipse.org/legal/epl-2.0/
[41]: https://github.com/exasol/error-code-crawler-maven-plugin
[42]: https://github.com/exasol/project-keeper/
[43]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[44]: http://maven.apache.org/plugins/maven-clean-plugin/
[45]: http://maven.apache.org/plugins/maven-resources-plugin/
[46]: http://maven.apache.org/plugins/maven-install-plugin/
[47]: http://maven.apache.org/plugins/maven-deploy-plugin/
[48]: http://maven.apache.org/plugins/maven-site-plugin/
