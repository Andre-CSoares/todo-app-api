package net.app.todo.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.app.todo.domain.exception.BussinessException;
import net.app.todo.domain.model.Todo;
import net.app.todo.domain.repository.TodoRepository;
import net.app.todo.domain.service.AddTodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {
    private final AddTodoService addTodoService;
    private final TodoRepository todoRepository;

    @GetMapping("/all")
    public List<Todo> list() { return todoRepository.findAll(); }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> listById(@PathVariable long todoId){
        Optional<Todo> todo = todoRepository.findById(todoId);

        if(todo.isPresent()){
            return ResponseEntity.ok(todo.get());
        }

       return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Todo add(@Valid @RequestBody Todo todo){ return addTodoService.add(todo); }

    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> update (@PathVariable Long todoId, @Valid @RequestBody Todo todo){
        if(!todoRepository.existsById(todoId)){
            return ResponseEntity.notFound().build();
        }

        todo.setId(todoId);
        todo = addTodoService.add(todo);

        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> remove(@PathVariable Long todoId){
        if(!todoRepository.existsById(todoId)){
            return ResponseEntity.notFound().build();
        }

        addTodoService.remove(todoId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BussinessException.class)
    public ResponseEntity<String> capture(BussinessException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
