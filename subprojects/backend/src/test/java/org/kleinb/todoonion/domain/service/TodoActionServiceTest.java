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
package org.kleinb.todoonion.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.kleinb.todoonion.domain.model.TodoItem;
import org.kleinb.todoonion.domain.model.TodoList;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TodoActionServiceTest {

  private final TodoItemRepository itemRepo = MockTodoItemRepository.createEmpty();
  private final TodoListRepository listRepo = MockTodoListRepository.createEmpty();
  private final TodoActionService service = new TodoActionService(itemRepo, listRepo);

  @Nested
  class createNewTodo {

    @Test
    void should_create_with_description() {
      final var item = service.createNewTodo("description");
      Assertions.assertThat(item).extracting(TodoItem::getDescription).isEqualTo("description");
    }

    @Test
    void should_persist_created_item() {
      final var item = service.createNewTodo("description");
      Assertions.assertThat(itemRepo.findById(item.getId()).get()).isEqualToComparingFieldByField(item);
    }
  }

  @Nested
  class createNewTodoList {

    @Test
    void should_create() {
      final TodoList list = service.createNewTodoList();
      assertThat(list).isNotNull();
    }

    @Test
    void should_persist() {
      final TodoList list = service.createNewTodoList();
      Assertions.assertThat(listRepo.findById(list.getId()).get()).isEqualToComparingFieldByField(list);
    }
  }

  @Nested
  class addItemToList {

    private TodoItem someItem;
    private TodoList list;

    @BeforeEach
    void setup() {
      this.someItem = TodoItem.builder().description("foo").build();
      itemRepo.save(someItem);
      this.list = TodoList.builder().build();
      listRepo.save(list);
    }

    @Test
    void should_add_item_to_list() {
      final var listWithItem = service.addItemToList(someItem, list);
      assertThat(listWithItem.getItems()).contains(someItem);
    }

    @Test
    void should_persist_list_with_new_item() {
      service.addItemToList(someItem, list);
      Assertions.assertThat(listRepo.findById(list.getId()).get().getItems()).contains(someItem);
    }
  }

  @Nested
  class removeItemFromList {

    private TodoItem someItem;
    private TodoList list;

    @BeforeEach
    void setup() {
      var tempItem = TodoItem.builder().description("foo").build();
      someItem = itemRepo.save(tempItem);
      var tempList = TodoList.builder().build();
      listRepo.save(tempList);
      list = service.addItemToList(someItem, tempList);
    }

    @Test
    void should_remove_item_from_list() {
      final var listWithoutItem = service.removeItemFromList(someItem, list);
      assertThat(listWithoutItem.getItems()).doesNotContain(someItem);
    }

    @Test
    void should_persist_list_without_removed_item() {
      service.removeItemFromList(someItem, list);
      Assertions.assertThat(listRepo.findById(list.getId()).get().getItems()).doesNotContain(someItem);
    }
  }

  @Nested
  class archiveItem {

    private TodoItem someItem;

    @BeforeEach
    void setup() {
      this.someItem = TodoItem.builder().description("foo").build();
      itemRepo.save(someItem);
    }

    @Test
    void should_archive_item() {
      final TodoItem archived = service.archiveItem(someItem);
      assertThat(archived.isArchived()).isTrue();
    }

    @Test
    void should_persist_archived_item() {
      service.archiveItem(someItem);
      Assertions.assertThat(itemRepo.findById(someItem.getId()).get().isArchived()).isTrue();
    }
  }
}
