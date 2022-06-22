package com.example.springboot.service;

import com.example.springboot.entity.CourseSelection;
import com.example.springboot.entity.Lesson;
import com.example.springboot.entity.Student;
import com.example.springboot.exception.AlreadyExistsException;
import com.example.springboot.repository.CourseSelectionRepository;
import com.example.springboot.repository.LessonRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.request.LessonRequest;
import com.example.springboot.response.LessonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseSelectionRepository courseSelectionRepository;

    @Autowired
    UserRepository userRepository;

    public void saveLesson(LessonRequest lessonRequest) {
        Lesson lesson = new Lesson(lessonRequest.getLesson_name()
                , lessonRequest.getLesson_credit(), lessonRequest.getLesson_status());
        Optional<Lesson> lessonOptional = lessonRepository.findByLessonName(lesson.getLessonName());
        if(lessonOptional.isPresent()){
            throw new AlreadyExistsException("Save failed, the lesson already exist.");
        }else{
            lessonRepository.save(lesson);
        }
    }

    public List<LessonResponse> getNotSelectLesson(String account) {
        List<LessonResponse> lessonResponseList = new ArrayList<>();
        Optional<Student> student = userRepository.findByAccount(account);
        Optional<List<CourseSelection>> courseSelections = courseSelectionRepository.findCourseSelectionByStudent(student.get());
        int lessonSize = lessonRepository.findAll().size();
        List<Lesson> lessonList = lessonRepository.findAll();
        for (int i = 0; i < lessonSize; i++) {
            if (courseSelections.isPresent()) {
                int courseSelectSize = courseSelections.get().size();
                boolean isSelected = false;
                for (int j = 0; j < courseSelectSize; j++) {
                    if (lessonList.get(i).getLessonName() == courseSelections.get().get(j).getLesson().getLessonName()) {
                        isSelected = true;
                        break;
                    }
                }
                if (!isSelected) {
                    Lesson lesson = lessonList.get(i);
                    LessonResponse response = new LessonResponse(
                            lesson.getLessonId().intValue(),
                            lesson.getLessonName(),
                            lesson.getLessonCredit(),
                            lesson.getLessonStatus()
                    );
                    lessonResponseList.add(response);
                }
            }
        }
        return lessonResponseList;
}

    public List<LessonResponse> getAllLesson() {
        List<LessonResponse> lessonResponseList = new ArrayList<>();
        int lessonSize = lessonRepository.findAll().size();
        for (int i = 0; i < lessonSize; i++) {
            Lesson lesson = lessonRepository.findAll().get(i);
            LessonResponse response = new LessonResponse(
                    lesson.getLessonId().intValue(),
                    lesson.getLessonName(),
                    lesson.getLessonCredit(),
                    lesson.getLessonStatus()
            );
            lessonResponseList.add(response);
        }
        return lessonResponseList;
    }

    public void deleteLesson(String lessonName) {
        Optional<Lesson> lesson = lessonRepository.findByLessonName(lessonName);
        if (lesson.isPresent()) {
            lessonRepository.delete(lesson.get());
        }
    }
}
