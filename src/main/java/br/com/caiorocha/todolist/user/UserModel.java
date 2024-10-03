package br.com.caiorocha.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_Users")
public class UserModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(unique = true)
  private String username;

  private String name;

  private String password;

  @CreationTimestamp
  private LocalDateTime createdAt;
}

/*
 * Como seria a divisão/arquitetura de um Projeto Spring Boot?
 * - O projeto seria divido em Camadas, principalmente no escopo relacionado aos
 * Repositories, Controllers e Services.
 *
 * Como você faria um endpoint que busque por um nome e retorne uma lista
 * paginada?
 *
 *
 *
 *
 */