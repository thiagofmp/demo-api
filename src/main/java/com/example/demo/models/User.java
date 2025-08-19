package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = User.TABLE_NAME) // ou name = "user"

public class User {
    public interface  CreateUser {} // acho q serve pra enviar as excessões qnd o usuario cria a conta
    public interface UpdateUser {} // msm coisa mas pra qnd da update creio

    public static final String TABLE_NAME = "user";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //serve pra dizer ao sql q é tipo um auto increment
    @Column(name = "id", unique = true) // cria a coluna | unique garante que sera unico
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true) // esse nullable serve pra criar o banco de dados, qnd ficar null da erro de banco
    @NotNull(groups = CreateUser.class) // aqui serve pro spring dar o erro explicando
    @NotEmpty(groups = CreateUser.class) // garante que não é uma string vazia
    @Size(groups = CreateUser.class, min=2, max = 100)
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY) // serve para garantir que a senha só vai escrever, não retorna nada
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class},min = 8, max = 60)
    private String password;

    @OneToMany(mappedBy = "user") // quer dizer q a variavel user em Task.java que mapeia isso
    private List<Task> tasks = new ArrayList<Task>();

    public User() { // o spring utiliza construtores vazios
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (obj == null){
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        if(this.id == null)
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        return Objects.equals(this.id, other.id) && Objects.equals(this.username, other.username) 
        && Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

}
