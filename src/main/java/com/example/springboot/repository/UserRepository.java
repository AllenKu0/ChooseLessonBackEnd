package com.example.springboot.repository;

import com.example.springboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
//    Optional<User> findByHandle(ByteArray handle);
    Optional<Student> findById(Long id);

    Optional<Student> findByAccount(String account);

}
