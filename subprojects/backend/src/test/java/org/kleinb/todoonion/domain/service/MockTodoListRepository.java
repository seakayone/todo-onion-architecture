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
import org.kleinb.todoonion.domain.model.TodoList;

public class MockTodoListRepository implements TodoListRepository {

  private Map<String, TodoList> entities = HashMap.empty();

  private MockTodoListRepository() {
    // use factory methods
  }

  public static TodoListRepository createEmpty() {
    return new MockTodoListRepository();
  }

  @Override
  public Option<TodoList> findById(String id) {
    return entities.get(id);
  }

  @Override
  public TodoList save(TodoList list) {
    entities = entities.put(list.getId(), list);
    return list;
  }
}
