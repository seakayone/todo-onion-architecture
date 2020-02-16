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
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kleinb.todoonion.domain.model.TodoItem;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaTodoItem {

  @Id
  private String id;

  @NotNull
  private String description;

  private Instant dueDate;
  private boolean archived;

  public static JpaTodoItem of(TodoItem item) {
    final var jpa = new JpaTodoItem();
    jpa.setDescription(item.getDescription());
    jpa.setId(item.getId());
    jpa.setArchived(item.isArchived());
    jpa.setDueDate(item.getDueDate().getOrNull());
    return jpa;
  }

  public TodoItem asEntity() {
    return TodoItem.builder()
        .id(this.getId())
        .description(this.getDescription())
        .archived(this.isArchived())
        .dueDate(Option.of(this.getDueDate()))
        .build();
  }
}
