package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.User;
import com.hugo.itireland.repository.UserRepository;
import com.hugo.itireland.service.UserService;
import com.hugo.itireland.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User add(User user){
        user.setCtime(LocalDateTime.now());
        user.setPassword(PasswordUtil.md5(user.getPassword()));
        return userRepository.save(user);
    }



    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).toList();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean exist(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public User login(String username, String password) {
        password = PasswordUtil.md5(password);
        return userRepository.findByUsernameAndPassword(username,password);
    }
}
