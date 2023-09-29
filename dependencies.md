<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                      | License          |
| ------------------------------- | ---------------- |
| [Virtual Schema Common JDBC][0] | [MIT License][1] |
| [error-reporting-java][2]       | [MIT License][3] |

## Test Dependencies

| Dependency                                      | License                                                                        |
| ----------------------------------------------- | ------------------------------------------------------------------------------ |
| [Hamcrest][4]                                   | [BSD License 3][5]                                                             |
| [JUnit Jupiter (Aggregator)][6]                 | [Eclipse Public License v2.0][7]                                               |
| [mockito-junit-jupiter][8]                      | [The MIT License][9]                                                           |
| [Elasticsearch Java API Client][10]             | [The Apache Software License, Version 2.0][11]                                 |
| [jdbc][12]                                      | [Elastic License 2.0][13]                                                      |
| [Yasson][14]                                    | [Eclipse Public License v. 2.0][15]; [Eclipse Distribution License v. 1.0][16] |
| [Testcontainers :: JUnit Jupiter Extension][17] | [MIT][18]                                                                      |
| [Testcontainers :: elasticsearch][17]           | [MIT][18]                                                                      |
| [Test containers for Exasol on Docker][19]      | [MIT License][20]                                                              |
| [Test Database Builder for Java][21]            | [MIT License][22]                                                              |
| [Matcher for SQL Result Sets][23]               | [MIT License][24]                                                              |
| [udf-debugging-java][25]                        | [MIT License][26]                                                              |
| [SLF4J JDK14 Provider][27]                      | [MIT License][28]                                                              |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][29]                       | [GNU LGPL 3][30]                               |
| [Apache Maven Compiler Plugin][31]                      | [Apache-2.0][11]                               |
| [Apache Maven Enforcer Plugin][32]                      | [Apache-2.0][11]                               |
| [Maven Flatten Plugin][33]                              | [Apache Software Licenese][11]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][34] | [ASL2][35]                                     |
| [Maven Surefire Plugin][36]                             | [Apache-2.0][11]                               |
| [Versions Maven Plugin][37]                             | [Apache License, Version 2.0][11]              |
| [duplicate-finder-maven-plugin Maven Mojo][38]          | [Apache License 2.0][39]                       |
| [Apache Maven Assembly Plugin][40]                      | [Apache-2.0][11]                               |
| [Apache Maven JAR Plugin][41]                           | [Apache License, Version 2.0][11]              |
| [Maven Dependency Plugin][42]                           | [The Apache Software License, Version 2.0][35] |
| [Artifact reference checker and unifier][43]            | [MIT License][44]                              |
| [Maven Failsafe Plugin][45]                             | [Apache-2.0][11]                               |
| [JaCoCo :: Maven Plugin][46]                            | [Eclipse Public License 2.0][47]               |
| [error-code-crawler-maven-plugin][48]                   | [MIT License][49]                              |
| [Reproducible Build Maven Plugin][50]                   | [Apache 2.0][35]                               |
| [Project keeper maven plugin][51]                       | [The MIT License][52]                          |
| [Maven Clean Plugin][53]                                | [The Apache Software License, Version 2.0][35] |
| [Maven Resources Plugin][54]                            | [The Apache Software License, Version 2.0][35] |
| [Maven Install Plugin][55]                              | [The Apache Software License, Version 2.0][35] |
| [Maven Deploy Plugin][56]                               | [The Apache Software License, Version 2.0][35] |
| [Maven Site Plugin 3][57]                               | [The Apache Software License, Version 2.0][35] |

[0]: https://github.com/exasol/virtual-schema-common-jdbc/
[1]: https://github.com/exasol/virtual-schema-common-jdbc/blob/main/LICENSE
[2]: https://github.com/exasol/error-reporting-java/
[3]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[4]: http://hamcrest.org/JavaHamcrest/
[5]: http://opensource.org/licenses/BSD-3-Clause
[6]: https://junit.org/junit5/
[7]: https://www.eclipse.org/legal/epl-v20.html
[8]: https://github.com/mockito/mockito
[9]: https://github.com/mockito/mockito/blob/main/LICENSE
[10]: https://github.com/elastic/elasticsearch-java/
[11]: https://www.apache.org/licenses/LICENSE-2.0.txt
[12]: https://github.com/elastic/elasticsearch.git
[13]: https://raw.githubusercontent.com/elastic/elasticsearch/v8.10.2/licenses/ELASTIC-LICENSE-2.0.txt
[14]: https://projects.eclipse.org/projects/ee4j.yasson
[15]: http://www.eclipse.org/legal/epl-v20.html
[16]: http://www.eclipse.org/org/documents/edl-v10.php
[17]: https://java.testcontainers.org
[18]: http://opensource.org/licenses/MIT
[19]: https://github.com/exasol/exasol-testcontainers/
[20]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[21]: https://github.com/exasol/test-db-builder-java/
[22]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[23]: https://github.com/exasol/hamcrest-resultset-matcher/
[24]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[25]: https://github.com/exasol/udf-debugging-java/
[26]: https://github.com/exasol/udf-debugging-java/blob/main/LICENSE
[27]: http://www.slf4j.org
[28]: http://www.opensource.org/licenses/mit-license.php
[29]: http://sonarsource.github.io/sonar-scanner-maven/
[30]: http://www.gnu.org/licenses/lgpl.txt
[31]: https://maven.apache.org/plugins/maven-compiler-plugin/
[32]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[33]: https://www.mojohaus.org/flatten-maven-plugin/
[34]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[35]: http://www.apache.org/licenses/LICENSE-2.0.txt
[36]: https://maven.apache.org/surefire/maven-surefire-plugin/
[37]: https://www.mojohaus.org/versions/versions-maven-plugin/
[38]: https://basepom.github.io/duplicate-finder-maven-plugin
[39]: http://www.apache.org/licenses/LICENSE-2.0.html
[40]: https://maven.apache.org/plugins/maven-assembly-plugin/
[41]: https://maven.apache.org/plugins/maven-jar-plugin/
[42]: http://maven.apache.org/plugins/maven-dependency-plugin/
[43]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[44]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[45]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[46]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[47]: https://www.eclipse.org/legal/epl-2.0/
[48]: https://github.com/exasol/error-code-crawler-maven-plugin/
[49]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[50]: http://zlika.github.io/reproducible-build-maven-plugin
[51]: https://github.com/exasol/project-keeper/
[52]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[53]: http://maven.apache.org/plugins/maven-clean-plugin/
[54]: http://maven.apache.org/plugins/maven-resources-plugin/
[55]: http://maven.apache.org/plugins/maven-install-plugin/
[56]: http://maven.apache.org/plugins/maven-deploy-plugin/
[57]: http://maven.apache.org/plugins/maven-site-plugin/
