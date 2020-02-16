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

import lombok.RequiredArgsConstructor;
import org.kleinb.todoonion.domain.model.TodoList;
import org.kleinb.todoonion.domain.model.TodoItem;

@RequiredArgsConstructor
public class TodoActionService {

  private final TodoItemRepository itemRepo;
  private final TodoListRepository listRepo;

  public TodoItem createNewTodo(String description) {
    final var item = TodoItem.builder()
        .description(description)
        .build();
    return itemRepo.save(item);
  }

  public TodoList createNewTodoList() {
    final var list = TodoList.builder().build();
    return listRepo.save(list);
  }

  public TodoList addItemToList(TodoItem someItem, TodoList list) {
    final var newList = list.add(someItem);
    return listRepo.save(newList);
  }

  public TodoList removeItemFromList(TodoItem someItem, TodoList list) {
    final var newList = list.remove(someItem);
    return listRepo.save(newList);
  }

  public TodoItem archiveItem(TodoItem archiveThis) {
    return itemRepo.save(archiveThis.archive());
  }

}
