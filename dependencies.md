<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                      | License          |
| ------------------------------- | ---------------- |
| [Virtual Schema Common JDBC][0] | [MIT License][1] |
| [error-reporting-java][2]       | [MIT License][3] |

## Test Dependencies

| Dependency                                      | License                                                                                                        |
| ----------------------------------------------- | -------------------------------------------------------------------------------------------------------------- |
| [Hamcrest][4]                                   | [BSD License 3][5]                                                                                             |
| [JUnit Jupiter (Aggregator)][6]                 | [Eclipse Public License v2.0][7]                                                                               |
| [mockito-junit-jupiter][8]                      | [MIT][9]                                                                                                       |
| [Elasticsearch Java API Client][10]             | [The Apache Software License, Version 2.0][11]                                                                 |
| [Eclipse Parsson][12]                           | [Eclipse Public License 2.0][13]; [GNU General Public License, version 2 with the GNU Classpath Exception][14] |
| [jdbc][15]                                      | [Elastic License 2.0][16]                                                                                      |
| [Yasson][17]                                    | [Eclipse Public License v. 2.0][18]; [Eclipse Distribution License v. 1.0][19]                                 |
| [Testcontainers :: JUnit Jupiter Extension][20] | [MIT][21]                                                                                                      |
| [Testcontainers :: elasticsearch][20]           | [MIT][21]                                                                                                      |
| [Test containers for Exasol on Docker][22]      | [MIT License][23]                                                                                              |
| [Test Database Builder for Java][24]            | [MIT License][25]                                                                                              |
| [Matcher for SQL Result Sets][26]               | [MIT License][27]                                                                                              |
| [udf-debugging-java][28]                        | [MIT License][29]                                                                                              |
| [SLF4J JDK14 Provider][30]                      | [MIT License][31]                                                                                              |

## Plugin Dependencies

| Dependency                                              | License                                     |
| ------------------------------------------------------- | ------------------------------------------- |
| [Apache Maven Clean Plugin][32]                         | [Apache-2.0][11]                            |
| [Apache Maven Install Plugin][33]                       | [Apache-2.0][11]                            |
| [Apache Maven Resources Plugin][34]                     | [Apache-2.0][11]                            |
| [Apache Maven Site Plugin][35]                          | [Apache-2.0][11]                            |
| [SonarQube Scanner for Maven][36]                       | [GNU LGPL 3][37]                            |
| [Apache Maven Toolchains Plugin][38]                    | [Apache-2.0][11]                            |
| [Project Keeper Maven plugin][39]                       | [The MIT License][40]                       |
| [Apache Maven Compiler Plugin][41]                      | [Apache-2.0][11]                            |
| [Apache Maven Enforcer Plugin][42]                      | [Apache-2.0][11]                            |
| [Maven Flatten Plugin][43]                              | [Apache Software Licenese][11]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][44] | [ASL2][45]                                  |
| [Maven Surefire Plugin][46]                             | [Apache-2.0][11]                            |
| [Versions Maven Plugin][47]                             | [Apache License, Version 2.0][11]           |
| [duplicate-finder-maven-plugin Maven Mojo][48]          | [Apache License 2.0][49]                    |
| [Apache Maven Artifact Plugin][50]                      | [Apache-2.0][11]                            |
| [Apache Maven Assembly Plugin][51]                      | [Apache-2.0][11]                            |
| [Apache Maven JAR Plugin][52]                           | [Apache-2.0][11]                            |
| [Apache Maven Dependency Plugin][53]                    | [Apache-2.0][11]                            |
| [Artifact reference checker and unifier][54]            | [MIT License][55]                           |
| [Maven Failsafe Plugin][56]                             | [Apache-2.0][11]                            |
| [JaCoCo :: Maven Plugin][57]                            | [EPL-2.0][58]                               |
| [Quality Summarizer Maven Plugin][59]                   | [MIT License][60]                           |
| [error-code-crawler-maven-plugin][61]                   | [MIT License][62]                           |
| [Git Commit Id Maven Plugin][63]                        | [GNU Lesser General Public License 3.0][64] |

[0]: https://github.com/exasol/virtual-schema-common-jdbc/
[1]: https://github.com/exasol/virtual-schema-common-jdbc/blob/main/LICENSE
[2]: https://github.com/exasol/error-reporting-java/
[3]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[4]: http://hamcrest.org/JavaHamcrest/
[5]: http://opensource.org/licenses/BSD-3-Clause
[6]: https://junit.org/junit5/
[7]: https://www.eclipse.org/legal/epl-v20.html
[8]: https://github.com/mockito/mockito
[9]: https://opensource.org/licenses/MIT
[10]: https://github.com/elastic/elasticsearch-java/
[11]: https://www.apache.org/licenses/LICENSE-2.0.txt
[12]: https://github.com/eclipse-ee4j/parsson
[13]: https://projects.eclipse.org/license/epl-2.0
[14]: https://projects.eclipse.org/license/secondary-gpl-2.0-cp
[15]: https://github.com/elastic/elasticsearch
[16]: https://raw.githubusercontent.com/elastic/elasticsearch/v8.12.2/licenses/ELASTIC-LICENSE-2.0.txt
[17]: https://projects.eclipse.org/projects/ee4j.yasson
[18]: http://www.eclipse.org/legal/epl-v20.html
[19]: http://www.eclipse.org/org/documents/edl-v10.php
[20]: https://java.testcontainers.org
[21]: http://opensource.org/licenses/MIT
[22]: https://github.com/exasol/exasol-testcontainers/
[23]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[24]: https://github.com/exasol/test-db-builder-java/
[25]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[26]: https://github.com/exasol/hamcrest-resultset-matcher/
[27]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[28]: https://github.com/exasol/udf-debugging-java/
[29]: https://github.com/exasol/udf-debugging-java/blob/main/LICENSE
[30]: http://www.slf4j.org
[31]: http://www.opensource.org/licenses/mit-license.php
[32]: https://maven.apache.org/plugins/maven-clean-plugin/
[33]: https://maven.apache.org/plugins/maven-install-plugin/
[34]: https://maven.apache.org/plugins/maven-resources-plugin/
[35]: https://maven.apache.org/plugins/maven-site-plugin/
[36]: http://docs.sonarqube.org/display/PLUG/Plugin+Library/sonar-scanner-maven/sonar-maven-plugin
[37]: http://www.gnu.org/licenses/lgpl.txt
[38]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[39]: https://github.com/exasol/project-keeper/
[40]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[41]: https://maven.apache.org/plugins/maven-compiler-plugin/
[42]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[43]: https://www.mojohaus.org/flatten-maven-plugin/
[44]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[45]: http://www.apache.org/licenses/LICENSE-2.0.txt
[46]: https://maven.apache.org/surefire/maven-surefire-plugin/
[47]: https://www.mojohaus.org/versions/versions-maven-plugin/
[48]: https://basepom.github.io/duplicate-finder-maven-plugin
[49]: http://www.apache.org/licenses/LICENSE-2.0.html
[50]: https://maven.apache.org/plugins/maven-artifact-plugin/
[51]: https://maven.apache.org/plugins/maven-assembly-plugin/
[52]: https://maven.apache.org/plugins/maven-jar-plugin/
[53]: https://maven.apache.org/plugins/maven-dependency-plugin/
[54]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[55]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[56]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[57]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[58]: https://www.eclipse.org/legal/epl-2.0/
[59]: https://github.com/exasol/quality-summarizer-maven-plugin/
[60]: https://github.com/exasol/quality-summarizer-maven-plugin/blob/main/LICENSE
[61]: https://github.com/exasol/error-code-crawler-maven-plugin/
[62]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[63]: https://github.com/git-commit-id/git-commit-id-maven-plugin
[64]: http://www.gnu.org/licenses/lgpl-3.0.txt
