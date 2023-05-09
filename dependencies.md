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
| [TestContainers :: elasticsearch][17]           | [MIT][18]                                                                      |
| [Test containers for Exasol on Docker][19]      | [MIT License][20]                                                              |
| [Test Database Builder for Java][21]            | [MIT License][22]                                                              |
| [Matcher for SQL Result Sets][23]               | [MIT License][24]                                                              |
| [udf-debugging-java][25]                        | [MIT License][26]                                                              |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][27]                       | [GNU LGPL 3][28]                               |
| [Apache Maven Compiler Plugin][29]                      | [Apache-2.0][11]                               |
| [Apache Maven Enforcer Plugin][30]                      | [Apache-2.0][11]                               |
| [Maven Flatten Plugin][31]                              | [Apache Software Licenese][11]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][32] | [ASL2][33]                                     |
| [Maven Surefire Plugin][34]                             | [Apache-2.0][11]                               |
| [Versions Maven Plugin][35]                             | [Apache License, Version 2.0][11]              |
| [duplicate-finder-maven-plugin Maven Mojo][36]          | [Apache License 2.0][37]                       |
| [Apache Maven Assembly Plugin][38]                      | [Apache License, Version 2.0][11]              |
| [Apache Maven JAR Plugin][39]                           | [Apache License, Version 2.0][11]              |
| [Maven Dependency Plugin][40]                           | [The Apache Software License, Version 2.0][33] |
| [Artifact reference checker and unifier][41]            | [MIT License][42]                              |
| [Maven Failsafe Plugin][43]                             | [Apache-2.0][11]                               |
| [JaCoCo :: Maven Plugin][44]                            | [Eclipse Public License 2.0][45]               |
| [error-code-crawler-maven-plugin][46]                   | [MIT License][47]                              |
| [Reproducible Build Maven Plugin][48]                   | [Apache 2.0][33]                               |
| [Project keeper maven plugin][49]                       | [The MIT License][50]                          |
| [Maven Clean Plugin][51]                                | [The Apache Software License, Version 2.0][33] |
| [Maven Resources Plugin][52]                            | [The Apache Software License, Version 2.0][33] |
| [Maven Install Plugin][53]                              | [The Apache Software License, Version 2.0][33] |
| [Maven Deploy Plugin][54]                               | [The Apache Software License, Version 2.0][33] |
| [Maven Site Plugin 3][55]                               | [The Apache Software License, Version 2.0][33] |

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
[13]: https://raw.githubusercontent.com/elastic/elasticsearch/v8.7.1/licenses/ELASTIC-LICENSE-2.0.txt
[14]: https://projects.eclipse.org/projects/ee4j.yasson
[15]: http://www.eclipse.org/legal/epl-v20.html
[16]: http://www.eclipse.org/org/documents/edl-v10.php
[17]: https://testcontainers.org
[18]: http://opensource.org/licenses/MIT
[19]: https://github.com/exasol/exasol-testcontainers/
[20]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[21]: https://github.com/exasol/test-db-builder-java/
[22]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[23]: https://github.com/exasol/hamcrest-resultset-matcher/
[24]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[25]: https://github.com/exasol/udf-debugging-java/
[26]: https://github.com/exasol/udf-debugging-java/blob/main/LICENSE
[27]: http://sonarsource.github.io/sonar-scanner-maven/
[28]: http://www.gnu.org/licenses/lgpl.txt
[29]: https://maven.apache.org/plugins/maven-compiler-plugin/
[30]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[31]: https://www.mojohaus.org/flatten-maven-plugin/
[32]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[33]: http://www.apache.org/licenses/LICENSE-2.0.txt
[34]: https://maven.apache.org/surefire/maven-surefire-plugin/
[35]: https://www.mojohaus.org/versions/versions-maven-plugin/
[36]: https://github.com/basepom/duplicate-finder-maven-plugin
[37]: http://www.apache.org/licenses/LICENSE-2.0.html
[38]: https://maven.apache.org/plugins/maven-assembly-plugin/
[39]: https://maven.apache.org/plugins/maven-jar-plugin/
[40]: http://maven.apache.org/plugins/maven-dependency-plugin/
[41]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[42]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[43]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[44]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[45]: https://www.eclipse.org/legal/epl-2.0/
[46]: https://github.com/exasol/error-code-crawler-maven-plugin/
[47]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[48]: http://zlika.github.io/reproducible-build-maven-plugin
[49]: https://github.com/exasol/project-keeper/
[50]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[51]: http://maven.apache.org/plugins/maven-clean-plugin/
[52]: http://maven.apache.org/plugins/maven-resources-plugin/
[53]: http://maven.apache.org/plugins/maven-install-plugin/
[54]: http://maven.apache.org/plugins/maven-deploy-plugin/
[55]: http://maven.apache.org/plugins/maven-site-plugin/
