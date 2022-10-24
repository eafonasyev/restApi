package com.luv2code.springdemo.rest;


import com.luv2code.springdemo.entity.Student;
import com.luv2code.springdemo.error.StudentErrorResponse;
import com.luv2code.springdemo.error.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    List<Student> students = new ArrayList<Student>();
    @GetMapping("/student")
    public Student getStudent(){
        Student student = new Student("john","litlgow");
        return student;
    }

    @PostConstruct
    public void loadData(){

        students.add(new Student("sara","mishel"));
        students.add(new Student("sara1","mishel1"));
        students.add(new Student("sara2","mishel2"));
        students.add(new Student("sara3","mishel3"));
    }

    @GetMapping("/students/{studentId}")
    public Student getStudents(@PathVariable int studentId){
        if (studentId>students.size() || studentId<0 ){
           throw  new StudentNotFoundException("Not Found student with id "+studentId);
        }
        return students.get(studentId);
    }
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> exceptionHundeler(StudentNotFoundException ex){

        StudentErrorResponse errorResponse = new StudentErrorResponse();

        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
