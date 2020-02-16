/*
 *    Copyright 2020 Christian Kleinbölting
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.kleinb.todoonion;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

@AnalyzeClasses(packages = "org.kleinb.todoonion")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HexagonalArchitectureTest {

  @ArchTest
  static final ArchRule onion_architecture_is_respected = onionArchitecture()
      .domainModels("..domain.model..")
      .domainServices("..domain.service..")
      .applicationServices("..application..")
      .adapter("persistence", "..adapter.persistence..")
      .adapter("rest", "..adapter.rest..");

}

