package com.example.springboot.utitlity;

import com.example.springboot.entity.Student;
import com.example.springboot.request.UserRegisterRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public Student convertOfUserRegisterDTO(UserRegisterRequest dto) {
        Student user = new Student();
        BeanUtils.copyProperties(dto, user);

        return user;
    }
}
