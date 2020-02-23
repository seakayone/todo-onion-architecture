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
package org.kleinb.todoonion.adapter.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kleinb.todoonion.domain.model.TodoList;
import org.kleinb.todoonion.domain.service.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
class SpringDataTodoListRepositoryTest {

  @Autowired
  private JpaTodoListRepository jpaRepo;

  private TodoListRepository repo;

  @BeforeEach
  void setup() {
    this.repo = new SpringDataTodoListRepository(jpaRepo);
  }

  @Test
  void should_save_new_entity() {
    final var item = TodoList.builder()
        .id(UUID.randomUUID().toString())
        .build();
    repo.save(item);

    Assertions.assertThat(repo.findById(item.getId()))
        .usingFieldByFieldElementComparator()
        .containsExactly(item);
  }
}
