package br.com.caiorocha.todolist.task;

import br.com.caiorocha.todolist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    taskModel.setIdUser((UUID) userId);

    var currentDate = LocalDateTime.now();

    boolean mustBeHigherDate = currentDate.isAfter(taskModel.getStartedAt())
        || currentDate.isAfter(taskModel.getEndedAt());

    boolean mustBeLowerDate = taskModel.getStartedAt().isAfter(taskModel.getEndedAt());

    if (mustBeHigherDate) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("A data de início / data de término deve ser maior do que a data atual");
    }

    if (mustBeLowerDate) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("A data de início deve ser menor do que a data de término");
    }

    var task = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(task);
  }

  @GetMapping("/")
  public List<TaskModel> getAllTasks(HttpServletRequest request) {
    var userId = request.getAttribute("userId");

    var tasks = this.taskRepository.findByIdUser((UUID) userId);

    return tasks;
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
    var userId = request.getAttribute("userId");

    var task = this.taskRepository.findById(id).orElse(null);

    if (task == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A tarefa não foi encontrada.");
    }

    if (!task.getIdUser().equals(userId)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não tem permissão para alterar essa tarefa.");
    }

    Utils.copyNonNullProperties(taskModel, task);

    taskModel.setIdUser((UUID) userId);
    taskModel.setId(id);

    var updatedTask = this.taskRepository.save(taskModel);

    return ResponseEntity.ok().body(updatedTask);
  }
}
