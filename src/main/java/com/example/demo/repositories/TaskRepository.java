package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

    List<Task> findByUser_Id(Long id); // maneira automatizada do spring boot para fazer query

    //@Query(value= "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    //List<Task> findByUser_Id(Long id);

}
