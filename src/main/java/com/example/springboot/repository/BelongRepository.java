package com.example.springboot.repository;

import com.example.springboot.entity.Belong;
import com.example.springboot.entity.CourseSelection;
import com.example.springboot.entity.Teacher;
import com.example.springboot.request.TeacherRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BelongRepository extends JpaRepository<Belong, Long> {
    Optional<Belong> findOfficeByTeacher(Teacher teacher);

    @Query(value = "SELECT *\n" +
            "FROM teacher inner join trelationc AS R ON teacher.teacher_id = R.pk_teacher inner join office As O ON R.pk_office = O.office_id\n" +
            "WHERE teacher.teacher_name = :teacher_name",nativeQuery = true)
    Optional<Belong> findOfficeByTeacherWithQuery(@Param("teacher_name")String teacher_name);
}
