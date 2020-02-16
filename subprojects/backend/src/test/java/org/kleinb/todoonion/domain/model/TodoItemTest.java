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
package org.kleinb.todoonion.domain.model;


import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.vavr.control.Option;
import java.time.Instant;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TodoItemTest {

  @Test
  void must_have_a_description() {
    assertThatThrownBy(() -> TodoItem.builder().build())
        .isInstanceOf(NullPointerException.class)
        .hasMessage("description is marked non-null but is null");
  }

  @Test
  void initially_item_is_not_archived() {
    final var item = TodoItem.builder().description("foo").build();
    assertThat(item.isArchived()).isFalse();
  }

  @Test
  void can_be_archived() {
    final var item = TodoItem.builder().description("foo").build();
    item.archive();

    assertThat(item.isArchived());
  }

  @Test
  void may_have_a_due_date() {
    final var dueDate = Instant.now().plus(5, DAYS);
    final var item = TodoItem.builder().description("foo")
        .dueDate(Option.of(dueDate)).build();

    assertThat(item.getDueDate()).contains(dueDate);
  }
}
