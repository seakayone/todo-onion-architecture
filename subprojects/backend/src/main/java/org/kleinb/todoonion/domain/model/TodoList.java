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
package org.kleinb.todoonion.domain.model;

import static org.kleinb.todoonion.domain.model.UuidHelper.randomUuid;

import io.vavr.collection.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class TodoList {

  @Default
  @NonNull String id = randomUuid();
  @Default
  @NonNull List<TodoItem> items = List.empty();

  public TodoList add(TodoItem someItem) {
    return this.toBuilder().items(items.prepend(someItem)).build();
  }

  public TodoList remove(TodoItem someItem) {
    return this.toBuilder().items(items.remove(someItem)).build();
  }
}
