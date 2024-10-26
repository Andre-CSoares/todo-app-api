package net.app.todo.domain.service;

import lombok.AllArgsConstructor;
import net.app.todo.domain.model.Todo;
import net.app.todo.domain.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AddTodoService {
    private final TodoRepository todoRepository;

    @Transactional
    public Todo add(Todo todo){
        return todoRepository.save(todo);
    }

    @Transactional
    public void remove (Long todoId) {
        todoRepository.deleteById(todoId);
    }
}
