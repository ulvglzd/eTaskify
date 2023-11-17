package com.etaskify.etaskifybackend.repository;

import com.etaskify.etaskifybackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
