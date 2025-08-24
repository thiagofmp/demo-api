package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id); //esse optional serve pra evitar erro caso o usuario não exista no banco
        
        return user.orElseThrow(() -> new RuntimeException( // se for preenchido vai buscar no banco e retornar o user senão ele avisa que não achou
            "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional // serve pra que as coisas não sejam salvas pela metade, ou salva tudo q foi passado ou não salva nada
    public User create(User obj) {
        obj.setId(null); // se n fizer isso pode atualizar o id do usuario no banco
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());

        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há tarefas relacionadas.");
        }
    }
}