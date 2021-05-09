package com.example.lab_2_testcontainers.models;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public Long getId() {
    return id;
  }

  public Book setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Book setName(String name) {
    this.name = name;
    return this;
  }
}