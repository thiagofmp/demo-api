package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;

// REPOSITORIOS servem para fazer as buscas no banco de dados

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
