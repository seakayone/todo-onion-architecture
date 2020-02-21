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

import static io.vavr.control.Option.some;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.vavr.control.Option;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.kleinb.todoonion.domain.model.TodoItem;
import org.kleinb.todoonion.domain.service.TodoActionService;
import org.kleinb.todoonion.domain.service.TodoSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest
@Import(WebMvcTestContext.class)
public class TodoItemsRestApiTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TodoActionService todoActionService;

  @MockBean
  private TodoSearchService todoSearchService;

  @Nested
  class Post_Method {

    @Test
    void description_given_should_create_() throws Exception {
      when(todoActionService.createNewTodo(anyString()))
          .thenReturn(TodoItem.builder().description("foo").build());
      mockMvc.perform(
          post("/api/v1/todo/items")
              .content("{\"description\":\"foo\"}")
              .contentType(APPLICATION_JSON)
              .accept(APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void no_description_given_then_should_return_bad_request() throws Exception {
      mockMvc.perform(
          post("/api/v1/todo/items")
              .content("{}")
              .contentType(APPLICATION_JSON)
              .accept(APPLICATION_JSON))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  class GetMethod {

    @Test
    void when_no_item_found_should_return_not_found() throws Exception {
      when(todoSearchService.findById(anyString())).thenReturn(Option.none());
      mockMvc.perform(
          get("/api/v1/todo/items/1")
              .contentType(APPLICATION_JSON)
              .accept(APPLICATION_JSON))
          .andExpect(status().isNotFound());
    }

    @Test
    void when_item_found_should_return_ok() throws Exception {
      when(todoSearchService.findById(anyString()))
          .thenReturn(some(TodoItem.builder().description("foo").build()));
      mockMvc.perform(
          get("/api/v1/todo/items/1")
              .contentType(APPLICATION_JSON)
              .accept(APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void when_no_items_found_pagination_should_return_ok() throws Exception {
      when(todoSearchService.findAll(any(Pageable.class)))
          .thenReturn(Page.empty());
      mockMvc.perform(
          get("/api/v1/todo/items?page=0&size=1")
              .contentType(APPLICATION_JSON)
              .accept(APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void when_items_found_pagination_should_return_ok() throws Exception {
      when(todoSearchService.findAll(any(Pageable.class)))
          .thenReturn(new PageImpl<>(List.of(TodoItem.builder().description("foo").build()),
              PageRequest.of(0, 1), 1));
      mockMvc.perform(
          get("/api/v1/todo/items?page=0&size=1")
              .contentType(APPLICATION_JSON)
              .accept(APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)));
    }
  }
}
