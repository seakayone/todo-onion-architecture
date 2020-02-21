package org.kleinb.todoonion.adapter.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcTestContext {

  @Bean
  public TodoItemResourceMapper todoItemResourceMapper() {
    return TodoItemResourceMapper.INSTANCE;
  }
}
