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

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kleinb.todoonion.domain.model.TodoList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaTodoList {

  @Id
  private String id;

  @OneToMany
  private List<JpaTodoItem> items;

  public static JpaTodoList of(TodoList list) {
    final var items = list.getItems().map(JpaTodoItemMapper.INSTANCE::todoItemToJpaTodoItem);
    final var jpa = new JpaTodoList();
    jpa.setId(list.getId());
    jpa.setItems(items.toJavaList());
    return jpa;
  }
}
