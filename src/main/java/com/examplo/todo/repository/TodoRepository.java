package com.examplo.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examplo.todo.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
