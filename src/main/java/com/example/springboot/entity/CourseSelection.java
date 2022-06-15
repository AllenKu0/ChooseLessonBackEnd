package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CourseSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chooseId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pk_lesson", referencedColumnName = "lessonId")
    private Lesson lesson;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pk_student", referencedColumnName = "id")
    private Student student;

    public CourseSelection(Lesson lesson, Student student) {
        this.lesson = lesson;
        this.student = student;
    }

    public Long getChooseId() {
        return chooseId;
    }

    public void setChooseId(Long chooseId) {
        this.chooseId = chooseId;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
