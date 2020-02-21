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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kleinb.todoonion.domain.service.TodoActionService;
import org.kleinb.todoonion.domain.service.TodoSearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo/items")
public class TodoItemsRestApi {

  private final TodoActionService service;
  private final TodoSearchService todoSearchService;
  private final TodoItemResourceMapper mapper;

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<TodoItemResource> create(@Valid @RequestBody TodoItemResource itemDto) {
    final var created = service.createNewTodo(itemDto.getDescription());
    itemDto.setId(created.getId());
    return new ResponseEntity<>(itemDto, CREATED);
  }

  @GetMapping(value = "{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<TodoItemResource> get(@PathVariable String id) {
    return todoSearchService.findById(id)
        .map(mapper::todoItemResourceToTodoItem)
        .map(it -> new ResponseEntity<>(it, OK))
        .getOrElse(() -> new ResponseEntity<>(NOT_FOUND));
  }

  @GetMapping(params = {"page", "size"},
      consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<TodoItemResource>> getPage(
      @RequestParam int page,
      @RequestParam int size
  ) {
    final var result = todoSearchService.findAll(PageRequest.of(page, size))
        .map(mapper::todoItemResourceToTodoItem)
        .toList();
    return new ResponseEntity<>(result, OK);
  }
}
