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

import io.vavr.control.Option;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.kleinb.todoonion.domain.model.TodoItem;
import org.kleinb.todoonion.domain.service.TodoItemRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SpringDataTodoItemRepositoryTest {

  @Autowired
  private JpaTodoItemRepository jpaRepo;

  private TodoItemRepository repo;

  @BeforeEach
  void setup() {
    repo = new SpringDataTodoItemRepository(jpaRepo, Mappers.getMapper(JpaTodoItemMapper.class));
  }

  @Test
  void should_save_new_entity() {
    final var item = TodoItem.builder().id(UUID.randomUUID().toString())
        .archived(true)
        .dueDate(Option.some(Instant.now()))
        .description("foo").build();
    repo.save(item);

    assertThat(repo.findById(item.getId()))
        .usingFieldByFieldElementComparator()
        .containsExactly(item);
  }
}
