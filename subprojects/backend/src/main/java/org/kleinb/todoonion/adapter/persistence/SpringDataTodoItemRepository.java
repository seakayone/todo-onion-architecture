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

import io.vavr.control.Option;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.kleinb.todoonion.domain.model.TodoItem;
import org.kleinb.todoonion.domain.service.TodoItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringDataTodoItemRepository implements TodoItemRepository {

  private final JpaTodoItemRepository jpaRepo;
  private final JpaTodoItemMapper mapper;

  @Override
  public @NonNull Option<TodoItem> findById(@NonNull String id) {
    return jpaRepo.findById(id).map(mapper::jpaTodoItemToTodoItem);
  }

  @Override
  public TodoItem save(TodoItem item) {
    jpaRepo.save(mapper.todoItemToJpaTodoItem(item));
    return item;
  }

  @Override
  public Page<TodoItem> findAll(Pageable pageable) {
    final var page = jpaRepo.findAll(pageable);
    return page.map(mapper::jpaTodoItemToTodoItem);
  }
}
