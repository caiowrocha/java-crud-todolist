package br.com.caiorocha.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  private UUID idUser;

  private String description;
  @Column(length = 50)
  private String title;
  private String priority;

  private LocalDateTime startedAt;
  private LocalDateTime endedAt;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public void setTitle(String title) throws Exception {

    if (title.length() > 50) {
      throw new Exception("O campo title deve conter no máximo 50 caracteres");
    }

    this.title = title;
  }
}
