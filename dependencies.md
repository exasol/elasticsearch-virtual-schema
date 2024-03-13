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

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][32]                       | [GNU LGPL 3][33]                               |
| [Apache Maven Toolchains Plugin][34]                    | [Apache License, Version 2.0][11]              |
| [Apache Maven Compiler Plugin][35]                      | [Apache-2.0][11]                               |
| [Apache Maven Enforcer Plugin][36]                      | [Apache-2.0][11]                               |
| [Maven Flatten Plugin][37]                              | [Apache Software Licenese][11]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][38] | [ASL2][39]                                     |
| [Maven Surefire Plugin][40]                             | [Apache-2.0][11]                               |
| [Versions Maven Plugin][41]                             | [Apache License, Version 2.0][11]              |
| [duplicate-finder-maven-plugin Maven Mojo][42]          | [Apache License 2.0][43]                       |
| [Apache Maven Assembly Plugin][44]                      | [Apache-2.0][11]                               |
| [Apache Maven JAR Plugin][45]                           | [Apache License, Version 2.0][11]              |
| [Maven Dependency Plugin][46]                           | [The Apache Software License, Version 2.0][39] |
| [Artifact reference checker and unifier][47]            | [MIT License][48]                              |
| [Maven Failsafe Plugin][49]                             | [Apache-2.0][11]                               |
| [JaCoCo :: Maven Plugin][50]                            | [Eclipse Public License 2.0][51]               |
| [error-code-crawler-maven-plugin][52]                   | [MIT License][53]                              |
| [Reproducible Build Maven Plugin][54]                   | [Apache 2.0][39]                               |
| [Project Keeper Maven plugin][55]                       | [The MIT License][56]                          |

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
[32]: http://sonarsource.github.io/sonar-scanner-maven/
[33]: http://www.gnu.org/licenses/lgpl.txt
[34]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[35]: https://maven.apache.org/plugins/maven-compiler-plugin/
[36]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[37]: https://www.mojohaus.org/flatten-maven-plugin/
[38]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[39]: http://www.apache.org/licenses/LICENSE-2.0.txt
[40]: https://maven.apache.org/surefire/maven-surefire-plugin/
[41]: https://www.mojohaus.org/versions/versions-maven-plugin/
[42]: https://basepom.github.io/duplicate-finder-maven-plugin
[43]: http://www.apache.org/licenses/LICENSE-2.0.html
[44]: https://maven.apache.org/plugins/maven-assembly-plugin/
[45]: https://maven.apache.org/plugins/maven-jar-plugin/
[46]: http://maven.apache.org/plugins/maven-dependency-plugin/
[47]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[48]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[49]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[50]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[51]: https://www.eclipse.org/legal/epl-2.0/
[52]: https://github.com/exasol/error-code-crawler-maven-plugin/
[53]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[54]: http://zlika.github.io/reproducible-build-maven-plugin
[55]: https://github.com/exasol/project-keeper/
[56]: https://github.com/exasol/project-keeper/blob/main/LICENSE
