package com.example.springboot.repository;

import com.example.springboot.entity.CourseSelection;
import com.example.springboot.entity.Lesson;
import com.example.springboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseSelectionRepository extends JpaRepository<CourseSelection, Long> {
    Optional<CourseSelection> findCourseSelectionByStudentAndLesson(Student user, Lesson lesson);

    //@Query(value = "SELECT *\n" +
    //       "FROM lesson AS L inner join course_selection AS C on L.lesson_id=C.pk_lesson inner join student AS S on C.pk_student = S.id\n" +
    //        "WHERE S.id = :student_id",nativeQuery = true)
    //相等
    Optional<List<CourseSelection>> findCourseSelectionByStudent(Student student);

    //所有欄位都要取
    @Query(value = "SELECT *\n" +
            "FROM lesson AS L inner join course_selection AS C on L.lesson_id=C.pk_lesson inner join student AS S on C.pk_student = S.id\n" +
            "WHERE S.id = :student_id",nativeQuery = true)
    Optional<List<CourseSelection>> findCourseByStudentId(@Param("student_id") Long student_id);

}
