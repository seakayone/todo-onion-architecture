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

import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.kleinb.todoonion.domain.model.TodoItem;
import org.kleinb.todoonion.domain.model.TodoList;
import org.kleinb.todoonion.domain.service.TodoListRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringDataTodoListRepository implements TodoListRepository {

  private final JpaTodoListRepository repo;

  @Override
  public @NonNull Option<TodoList> findById(@NonNull String id) {
    return repo.findById(id).map(it ->
        TodoList.builder()
            .id(it.getId())
            .items(asEntities(it))
            .build());
  }

  private List<TodoItem> asEntities(JpaTodoList it) {
    return List.ofAll(it.getItems())
        .map(JpaTodoItemMapper.INSTANCE::jpaTodoItemToTodoItem);
  }

  @Override
  public TodoList save(TodoList list) {
    final JpaTodoList jpa = JpaTodoList.of(list);
    repo.save(jpa);
    return list;
  }
}
