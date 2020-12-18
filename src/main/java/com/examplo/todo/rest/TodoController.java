package com.examplo.todo.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.examplo.todo.model.Todo;
import com.examplo.todo.repository.TodoRepository;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TodoController {
	@Autowired
	private TodoRepository repository;

	@PostMapping
	public Todo save(@RequestBody Todo todo) {
		return repository.save(todo);
	}

	@GetMapping
	public List<Todo> getAll() {
		return repository.findAll();
	}

	@GetMapping("{id}")
	public Todo getById(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY));
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

	// Ex.: http://localhost:8080/api/todos/1/done
	@PatchMapping("{id}/done")
	public Todo markAsdone(@PathVariable Long id) {
		return repository.findById(id).map(todo -> {
			todo.setDone(true);
			todo.setDoneDate(LocalDateTime.now());
			repository.save(todo);
			return todo;
		}).orElse(null);
	}

}
