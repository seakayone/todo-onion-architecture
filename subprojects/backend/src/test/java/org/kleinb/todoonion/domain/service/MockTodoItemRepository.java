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

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import lombok.NonNull;
import org.kleinb.todoonion.domain.model.TodoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class MockTodoItemRepository implements TodoItemRepository {

  private Map<String, TodoItem> entities = HashMap.empty();

  private MockTodoItemRepository() {
    // use factory methods
  }

  public static MockTodoItemRepository createEmpty() {
    return new MockTodoItemRepository();
  }

  @Override
  public @NonNull Option<TodoItem> findById(@NonNull String id) {
    return entities.get(id);
  }

  @Override
  public TodoItem save(TodoItem item) {
    entities = entities.put(item.getId(), item);
    return item;
  }

  @Override
  public Page<TodoItem> findAll(Pageable pageable) {
    final var content = entities.values().slice((int) pageable.getOffset(), pageable.getPageSize());
    return new PageImpl<>(content.asJava(), pageable, content.size());
  }
}
