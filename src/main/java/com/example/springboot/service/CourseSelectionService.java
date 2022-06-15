package com.example.springboot.service;

import com.example.springboot.entity.CourseSelection;
import com.example.springboot.entity.Lesson;
import com.example.springboot.entity.Student;
import com.example.springboot.repository.CourseSelectionRepository;
import com.example.springboot.repository.LessonRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.response.CourseSelectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseSelectionService {
    @Autowired
    private CourseSelectionRepository courseSelectionRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveCourse(com.example.springboot.request.CourseSelectionRequest courseSelectionRequest) throws Exception {

        Optional<Student> user = userRepository.findByAccount(courseSelectionRequest.getUser_name());
        Optional<Lesson> lesson = lessonRepository.findById(courseSelectionRequest.getLesson_id());
        if (lesson.isPresent() && user.isPresent()) {
            CourseSelection courseSelection = new CourseSelection(lesson.get(),
                    user.get());
            courseSelectionRepository.save(courseSelection);
        } else {
            throw new Exception("no find");
        }
    }

    public void deleteCourse(com.example.springboot.request.CourseSelectionRequest courseSelectionRequest) throws Exception {
        Optional<Student> user = userRepository.findByAccount(courseSelectionRequest.getUser_name());
        Optional<Lesson> lesson = lessonRepository.findById(courseSelectionRequest.getLesson_id());
        if (lesson.isPresent() && user.isPresent()) {
            Optional<CourseSelection> courseSelection = courseSelectionRepository.findCourseSelectionByStudentAndLesson(user.get(), lesson.get());
            courseSelection.ifPresent(selection -> courseSelectionRepository.delete(selection));
        } else {
            throw new Exception("no find");
        }
    }

    public List<CourseSelectionResponse> getAllLesson(String account) {
        List<CourseSelectionResponse> courseSelectionResponses = new ArrayList<>();
        Optional<Student> student = userRepository.findByAccount(account);
        if (student.isPresent()) {
            if (courseSelectionRepository.findCourseSelectionByStudent(student.get()).isPresent()) {
                for (int i = 0; i < courseSelectionRepository.findCourseSelectionByStudent(student.get()).get().size(); i++) {
                    CourseSelection courseSelection = courseSelectionRepository.findCourseSelectionByStudent(student.get()).get().get(i);
                    courseSelectionResponses.add(new CourseSelectionResponse(
                            courseSelection.getLesson().getLessonId(),
                            courseSelection.getLesson().getLessonName(),
                            courseSelection.getLesson().getLessonCredit(),
                            courseSelection.getLesson().getLessonStatus()
                    ));
                }
            }
        }
        return courseSelectionResponses;
    }
}
