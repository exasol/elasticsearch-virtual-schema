<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                      | License          |
| ------------------------------- | ---------------- |
| [Virtual Schema Common JDBC][0] | [MIT License][1] |
| [error-reporting-java][2]       | [MIT License][3] |

## Test Dependencies

| Dependency                                      | License                                                                                                           |
| ----------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| [Hamcrest][4]                                   | [BSD-3-Clause][5]                                                                                                 |
| [JUnit Jupiter (Aggregator)][6]                 | [Eclipse Public License v2.0][7]                                                                                  |
| [mockito-junit-jupiter][8]                      | [MIT][9]                                                                                                          |
| [Elasticsearch Java API Client][10]             | [The Apache Software License, Version 2.0][11]                                                                    |
| [jdbc][12]                                      | [Elastic License 2.0][13]                                                                                         |
| [Yasson][14]                                    | [Eclipse Public License v. 2.0][15]; [GNU General Public License, version 2 with the GNU Classpath Exception][16] |
| [Testcontainers :: JUnit Jupiter Extension][17] | [MIT][18]                                                                                                         |
| [Testcontainers :: elasticsearch][17]           | [MIT][18]                                                                                                         |
| [Test containers for Exasol on Docker][19]      | [MIT License][20]                                                                                                 |
| [Test Database Builder for Java][21]            | [MIT License][22]                                                                                                 |
| [Matcher for SQL Result Sets][23]               | [MIT License][24]                                                                                                 |
| [udf-debugging-java][25]                        | [MIT License][26]                                                                                                 |
| [SLF4J JDK14 Provider][27]                      | [MIT][28]                                                                                                         |
| [JaCoCo :: Agent][29]                           | [EPL-2.0][30]                                                                                                     |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][31]                       | [GNU LGPL 3][32]                               |
| [Apache Maven Toolchains Plugin][33]                    | [Apache-2.0][11]                               |
| [Apache Maven Compiler Plugin][34]                      | [Apache-2.0][11]                               |
| [Apache Maven Enforcer Plugin][35]                      | [Apache-2.0][11]                               |
| [Maven Flatten Plugin][36]                              | [Apache Software License][11]                  |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][37] | [ASL2][38]                                     |
| [Maven Surefire Plugin][39]                             | [Apache-2.0][11]                               |
| [Versions Maven Plugin][40]                             | [Apache License, Version 2.0][11]              |
| [duplicate-finder-maven-plugin Maven Mojo][41]          | [Apache License 2.0][42]                       |
| [Apache Maven Artifact Plugin][43]                      | [Apache-2.0][11]                               |
| [Apache Maven Assembly Plugin][44]                      | [Apache-2.0][11]                               |
| [Apache Maven JAR Plugin][45]                           | [Apache-2.0][11]                               |
| [Artifact reference checker and unifier][46]            | [MIT License][47]                              |
| [spdx-maven-plugin Maven Plugin][48]                    | [The Apache Software License, Version 2.0][38] |
| [Apache Maven Dependency Plugin][49]                    | [Apache-2.0][11]                               |
| [Maven Failsafe Plugin][50]                             | [Apache-2.0][11]                               |
| [JaCoCo :: Maven Plugin][51]                            | [EPL-2.0][30]                                  |
| [error-code-crawler-maven-plugin][52]                   | [MIT License][53]                              |
| [Git Commit Id Maven Plugin][54]                        | [GNU Lesser General Public License 3.0][55]    |
| [Project Keeper Maven plugin][56]                       | [The MIT License][57]                          |
| [Apache Maven Clean Plugin][58]                         | [Apache-2.0][11]                               |
| [Apache Maven Resources Plugin][59]                     | [Apache-2.0][11]                               |
| [Apache Maven Install Plugin][60]                       | [Apache-2.0][11]                               |
| [Apache Maven Site Plugin][61]                          | [Apache-2.0][11]                               |

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
[12]: https://github.com/elastic/elasticsearch
[13]: https://raw.githubusercontent.com/elastic/elasticsearch/v9.5.1/licenses/ELASTIC-LICENSE-2.0.txt
[14]: https://projects.eclipse.org/projects/ee4j/yasson
[15]: https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt
[16]: https://www.gnu.org/software/classpath/license.html
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
[28]: https://opensource.org/license/mit
[29]: https://www.eclemma.org/jacoco/index.html
[30]: https://www.eclipse.org/legal/epl-2.0/
[31]: https://docs.sonarsource.com/sonarqube-server/latest/extension-guide/developing-a-plugin/plugin-basics/sonar-scanner-maven/sonar-maven-plugin/
[32]: http://www.gnu.org/licenses/lgpl.txt
[33]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[34]: https://maven.apache.org/plugins/maven-compiler-plugin/
[35]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[36]: https://www.mojohaus.org/flatten-maven-plugin/
[37]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[38]: http://www.apache.org/licenses/LICENSE-2.0.txt
[39]: https://maven.apache.org/surefire/maven-surefire-plugin/
[40]: https://www.mojohaus.org/versions/versions-maven-plugin/
[41]: https://basepom.github.io/duplicate-finder-maven-plugin
[42]: http://www.apache.org/licenses/LICENSE-2.0.html
[43]: https://maven.apache.org/plugins/maven-artifact-plugin/
[44]: https://maven.apache.org/plugins/maven-assembly-plugin/
[45]: https://maven.apache.org/plugins/maven-jar-plugin/
[46]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[47]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[48]: https://github.com/spdx/spdx-maven-plugin
[49]: https://maven.apache.org/plugins/maven-dependency-plugin/
[50]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[51]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[52]: https://github.com/exasol/error-code-crawler-maven-plugin/
[53]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[54]: https://github.com/git-commit-id/git-commit-id-maven-plugin
[55]: http://www.gnu.org/licenses/lgpl-3.0.txt
[56]: https://github.com/exasol/project-keeper/
[57]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[58]: https://maven.apache.org/plugins/maven-clean-plugin/
[59]: https://maven.apache.org/plugins/maven-resources-plugin/
[60]: https://maven.apache.org/plugins/maven-install-plugin/
[61]: https://maven.apache.org/plugins/maven-site-plugin/
