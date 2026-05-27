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
| [Hamcrest][4]                                   | [BSD-3-Clause][5]                                                              |
| [JUnit Jupiter (Aggregator)][6]                 | [Eclipse Public License v2.0][7]                                               |
| [mockito-junit-jupiter][8]                      | [MIT][9]                                                                       |
| [Elasticsearch Java API Client][10]             | [The Apache Software License, Version 2.0][11]                                 |
| [Jackson-core][12]                              | [The Apache Software License, Version 2.0][11]                                 |
| [jackson-databind][13]                          | [The Apache Software License, Version 2.0][11]                                 |
| [jdbc][14]                                      | [Elastic License 2.0][15]                                                      |
| [Yasson][16]                                    | [Eclipse Public License v. 2.0][17]; [Eclipse Distribution License v. 1.0][18] |
| [Testcontainers :: JUnit Jupiter Extension][19] | [MIT][20]                                                                      |
| [Testcontainers :: elasticsearch][19]           | [MIT][20]                                                                      |
| [Test containers for Exasol on Docker][21]      | [MIT License][22]                                                              |
| [Test Database Builder for Java][23]            | [MIT License][24]                                                              |
| [Matcher for SQL Result Sets][25]               | [MIT License][26]                                                              |
| [udf-debugging-java][27]                        | [MIT License][28]                                                              |
| [SLF4J JDK14 Provider][29]                      | [MIT][30]                                                                      |
| [JaCoCo :: Agent][31]                           | [EPL-2.0][32]                                                                  |

## Plugin Dependencies

| Dependency                                              | License                                     |
| ------------------------------------------------------- | ------------------------------------------- |
| [SonarQube Scanner for Maven][33]                       | [GNU LGPL 3][34]                            |
| [Apache Maven Toolchains Plugin][35]                    | [Apache-2.0][11]                            |
| [Apache Maven Compiler Plugin][36]                      | [Apache-2.0][11]                            |
| [Apache Maven Enforcer Plugin][37]                      | [Apache-2.0][11]                            |
| [Maven Flatten Plugin][38]                              | [Apache Software License][11]               |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][39] | [ASL2][40]                                  |
| [Maven Surefire Plugin][41]                             | [Apache-2.0][11]                            |
| [Versions Maven Plugin][42]                             | [Apache License, Version 2.0][11]           |
| [duplicate-finder-maven-plugin Maven Mojo][43]          | [Apache License 2.0][44]                    |
| [Apache Maven Artifact Plugin][45]                      | [Apache-2.0][11]                            |
| [Apache Maven Assembly Plugin][46]                      | [Apache-2.0][11]                            |
| [Apache Maven JAR Plugin][47]                           | [Apache-2.0][11]                            |
| [Artifact reference checker and unifier][48]            | [MIT License][49]                           |
| [Apache Maven Dependency Plugin][50]                    | [Apache-2.0][11]                            |
| [Maven Failsafe Plugin][51]                             | [Apache-2.0][11]                            |
| [JaCoCo :: Maven Plugin][52]                            | [EPL-2.0][32]                               |
| [Quality Summarizer Maven Plugin][53]                   | [MIT License][54]                           |
| [error-code-crawler-maven-plugin][55]                   | [MIT License][56]                           |
| [Git Commit Id Maven Plugin][57]                        | [GNU Lesser General Public License 3.0][58] |
| [Project Keeper Maven plugin][59]                       | [The MIT License][60]                       |
| [Apache Maven Clean Plugin][61]                         | [Apache-2.0][11]                            |
| [Apache Maven Resources Plugin][62]                     | [Apache-2.0][11]                            |
| [Apache Maven Install Plugin][63]                       | [Apache-2.0][11]                            |
| [Apache Maven Site Plugin][64]                          | [Apache-2.0][11]                            |

[0]: https://github.com/exasol/virtual-schema-common-jdbc/
[1]: https://github.com/exasol/virtual-schema-common-jdbc/blob/main/LICENSE
[2]: https://github.com/exasol/error-reporting-java/
[3]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[4]: http://hamcrest.org/JavaHamcrest/
[5]: https://raw.githubusercontent.com/hamcrest/JavaHamcrest/master/LICENSE
[6]: https://junit.org/
[7]: https://www.eclipse.org/legal/epl-v20.html
[8]: https://github.com/mockito/mockito
[9]: https://opensource.org/licenses/MIT
[10]: https://github.com/elastic/elasticsearch-java/
[11]: https://www.apache.org/licenses/LICENSE-2.0.txt
[12]: https://github.com/FasterXML/jackson-core
[13]: https://github.com/FasterXML/jackson
[14]: https://github.com/elastic/elasticsearch
[15]: https://raw.githubusercontent.com/elastic/elasticsearch/v8.19.15/licenses/ELASTIC-LICENSE-2.0.txt
[16]: https://projects.eclipse.org/projects/ee4j.yasson
[17]: http://www.eclipse.org/legal/epl-v20.html
[18]: http://www.eclipse.org/org/documents/edl-v10.php
[19]: https://java.testcontainers.org
[20]: http://opensource.org/licenses/MIT
[21]: https://github.com/exasol/exasol-testcontainers/
[22]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[23]: https://github.com/exasol/test-db-builder-java/
[24]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[25]: https://github.com/exasol/hamcrest-resultset-matcher/
[26]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[27]: https://github.com/exasol/udf-debugging-java/
[28]: https://github.com/exasol/udf-debugging-java/blob/main/LICENSE
[29]: http://www.slf4j.org
[30]: https://opensource.org/license/mit
[31]: https://www.eclemma.org/jacoco/index.html
[32]: https://www.eclipse.org/legal/epl-2.0/
[33]: https://docs.sonarsource.com/sonarqube-server/latest/extension-guide/developing-a-plugin/plugin-basics/sonar-scanner-maven/sonar-maven-plugin/
[34]: http://www.gnu.org/licenses/lgpl.txt
[35]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[36]: https://maven.apache.org/plugins/maven-compiler-plugin/
[37]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[38]: https://www.mojohaus.org/flatten-maven-plugin/
[39]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[40]: http://www.apache.org/licenses/LICENSE-2.0.txt
[41]: https://maven.apache.org/surefire/maven-surefire-plugin/
[42]: https://www.mojohaus.org/versions/versions-maven-plugin/
[43]: https://basepom.github.io/duplicate-finder-maven-plugin
[44]: http://www.apache.org/licenses/LICENSE-2.0.html
[45]: https://maven.apache.org/plugins/maven-artifact-plugin/
[46]: https://maven.apache.org/plugins/maven-assembly-plugin/
[47]: https://maven.apache.org/plugins/maven-jar-plugin/
[48]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[49]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[50]: https://maven.apache.org/plugins/maven-dependency-plugin/
[51]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[52]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[53]: https://github.com/exasol/quality-summarizer-maven-plugin/
[54]: https://github.com/exasol/quality-summarizer-maven-plugin/blob/main/LICENSE
[55]: https://github.com/exasol/error-code-crawler-maven-plugin/
[56]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[57]: https://github.com/git-commit-id/git-commit-id-maven-plugin
[58]: http://www.gnu.org/licenses/lgpl-3.0.txt
[59]: https://github.com/exasol/project-keeper/
[60]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[61]: https://maven.apache.org/plugins/maven-clean-plugin/
[62]: https://maven.apache.org/plugins/maven-resources-plugin/
[63]: https://maven.apache.org/plugins/maven-install-plugin/
[64]: https://maven.apache.org/plugins/maven-site-plugin/
