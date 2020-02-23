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
package org.kleinb.todoonion.adapter.rest;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.control.Option;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.kleinb.todoonion.domain.model.TodoItem;
import org.mapstruct.factory.Mappers;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TodoItemResourceMapperTest {

  private TodoItemResourceMapper mapper = Mappers.getMapper(TodoItemResourceMapper.class);

  @Test
  void todoItemToResource() {
    final var dueDate = Instant.now().plus(5, DAYS);
    final var item = TodoItem.builder()
        .description("foo")
        .dueDate(Option.of(dueDate))
        .archived(true)
        .build();

    final var actual = mapper.todoItemResourceToTodoItem(item);

    assertThat(actual.getDescription()).isEqualTo(item.getDescription());
    assertThat(actual.getId()).isEqualTo(item.getId());
    assertThat(actual.getDueDate()).containsExactly(dueDate);
    assertThat(actual.isArchived()).isTrue();
  }

  @Test
  void todoItemResourceToItem() {
    final var resource = new TodoItemResource();
    resource.setId(UUID.randomUUID().toString());
    resource.setDescription("bar");

    final var actual = mapper.todoItemToTodoItemResource(resource);

    assertThat(actual.getDescription()).isEqualTo(resource.getDescription());
    assertThat(actual.getId()).isEqualTo(resource.getId());
    assertThat(actual.getDueDate()).isEmpty();
    assertThat(actual.isArchived()).isFalse();
  }
}
