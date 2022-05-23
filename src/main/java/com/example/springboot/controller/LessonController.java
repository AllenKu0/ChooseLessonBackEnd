package com.example.springboot.controller;

import com.example.springboot.constant.SecurityConstants;
import com.example.springboot.entity.User;
import com.example.springboot.request.LoginRequest;
import com.example.springboot.response.ListLessonResponse;
import com.example.springboot.service.LessonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    @Autowired
    LessonService lessonService;
    @GetMapping("/list")
    @ApiOperation(value = "登入")
    public ResponseEntity<ListLessonResponse> login() {
        ListLessonResponse response=lessonService.getAllLesson();
        if (response.getLessonResponseList().size()>0){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
