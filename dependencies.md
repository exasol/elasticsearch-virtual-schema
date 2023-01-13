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
| [jdbc][12]                                      | [Elastic License 2.0][13]                                                      |
| [Yasson][14]                                    | [Eclipse Public License v. 2.0][15]; [Eclipse Distribution License v. 1.0][16] |
| [Apache HttpClient][17]                         | [Apache License, Version 2.0][18]                                              |
| [Apache Commons Codec][19]                      | [Apache License, Version 2.0][11]                                              |
| [Testcontainers :: JUnit Jupiter Extension][20] | [MIT][21]                                                                      |
| [TestContainers :: elasticsearch][20]           | [MIT][21]                                                                      |
| [Test containers for Exasol on Docker][22]      | [MIT][3]                                                                       |
| [Test Database Builder for Java][23]            | [MIT License][24]                                                              |
| [Matcher for SQL Result Sets][25]               | [MIT][3]                                                                       |
| [udf-debugging-java][26]                        | [MIT][3]                                                                       |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][27]                       | [GNU LGPL 3][28]                               |
| [Apache Maven Compiler Plugin][29]                      | [Apache License, Version 2.0][11]              |
| [Apache Maven Enforcer Plugin][30]                      | [Apache License, Version 2.0][11]              |
| [Maven Flatten Plugin][31]                              | [Apache Software Licenese][11]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][32] | [ASL2][18]                                     |
| [Maven Surefire Plugin][33]                             | [Apache License, Version 2.0][11]              |
| [Versions Maven Plugin][34]                             | [Apache License, Version 2.0][11]              |
| [Apache Maven Assembly Plugin][35]                      | [Apache License, Version 2.0][11]              |
| [Apache Maven JAR Plugin][36]                           | [Apache License, Version 2.0][11]              |
| [Apache Maven Dependency Plugin][37]                    | [Apache License, Version 2.0][11]              |
| [Artifact reference checker and unifier][38]            | [MIT License][39]                              |
| [Maven Failsafe Plugin][40]                             | [Apache License, Version 2.0][11]              |
| [JaCoCo :: Maven Plugin][41]                            | [Eclipse Public License 2.0][42]               |
| [error-code-crawler-maven-plugin][43]                   | [MIT License][44]                              |
| [Reproducible Build Maven Plugin][45]                   | [Apache 2.0][18]                               |
| [Project keeper maven plugin][46]                       | [The MIT License][47]                          |
| [Maven Clean Plugin][48]                                | [The Apache Software License, Version 2.0][18] |
| [Maven Resources Plugin][49]                            | [The Apache Software License, Version 2.0][18] |
| [Maven Install Plugin][50]                              | [The Apache Software License, Version 2.0][18] |
| [Maven Deploy Plugin][51]                               | [The Apache Software License, Version 2.0][18] |
| [Maven Site Plugin 3][52]                               | [The Apache Software License, Version 2.0][18] |

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
[12]: https://github.com/elastic/elasticsearch
[13]: https://raw.githubusercontent.com/elastic/elasticsearch/v7.17.5/licenses/ELASTIC-LICENSE-2.0.txt
[14]: https://projects.eclipse.org/projects/ee4j.yasson
[15]: http://www.eclipse.org/legal/epl-v20.html
[16]: http://www.eclipse.org/org/documents/edl-v10.php
[17]: http://hc.apache.org/httpcomponents-client
[18]: http://www.apache.org/licenses/LICENSE-2.0.txt
[19]: https://commons.apache.org/proper/commons-codec/
[20]: https://testcontainers.org
[21]: http://opensource.org/licenses/MIT
[22]: https://github.com/exasol/exasol-testcontainers
[23]: https://github.com/exasol/test-db-builder-java/
[24]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[25]: https://github.com/exasol/hamcrest-resultset-matcher
[26]: https://github.com/exasol/udf-debugging-java/
[27]: http://sonarsource.github.io/sonar-scanner-maven/
[28]: http://www.gnu.org/licenses/lgpl.txt
[29]: https://maven.apache.org/plugins/maven-compiler-plugin/
[30]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[31]: https://www.mojohaus.org/flatten-maven-plugin/
[32]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[33]: https://maven.apache.org/surefire/maven-surefire-plugin/
[34]: https://www.mojohaus.org/versions-maven-plugin/
[35]: https://maven.apache.org/plugins/maven-assembly-plugin/
[36]: https://maven.apache.org/plugins/maven-jar-plugin/
[37]: https://maven.apache.org/plugins/maven-dependency-plugin/
[38]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[39]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[40]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[41]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[42]: https://www.eclipse.org/legal/epl-2.0/
[43]: https://github.com/exasol/error-code-crawler-maven-plugin/
[44]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[45]: http://zlika.github.io/reproducible-build-maven-plugin
[46]: https://github.com/exasol/project-keeper/
[47]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[48]: http://maven.apache.org/plugins/maven-clean-plugin/
[49]: http://maven.apache.org/plugins/maven-resources-plugin/
[50]: http://maven.apache.org/plugins/maven-install-plugin/
[51]: http://maven.apache.org/plugins/maven-deploy-plugin/
[52]: http://maven.apache.org/plugins/maven-site-plugin/
