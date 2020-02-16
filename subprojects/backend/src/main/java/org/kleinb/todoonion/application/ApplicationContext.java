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
package org.kleinb.todoonion.application;

import org.kleinb.todoonion.domain.service.TodoActionService;
import org.kleinb.todoonion.domain.service.TodoItemRepository;
import org.kleinb.todoonion.domain.service.TodoListRepository;
import org.kleinb.todoonion.domain.service.TodoSearchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContext {

  @Bean
  public TodoActionService todoService(TodoItemRepository itemRepo, TodoListRepository listRepo) {
    return new TodoActionService(itemRepo, listRepo);
  }

  @Bean
  public TodoSearchService todoSearchService(TodoItemRepository itemRepo) {
    return new TodoSearchService(itemRepo);
  }
}