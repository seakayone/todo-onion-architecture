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

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.kleinb.todoonion.domain.model.TodoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class TodoSearchService {

  private final TodoItemRepository itemRepo;

  public Option<TodoItem> findById(String id) {
    return itemRepo.findById(id);
  }

  public Page<TodoItem> findAll(Pageable pageable) {
    return itemRepo.findAll(pageable);
  }
}