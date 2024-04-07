package com.example.demo.model;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("api/v1/student")

public class StudentController {
    private final StudentService studentService;
    
	public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/test")
	public List<Student> getStudentsInfo() {
        return studentService.getStudentsInfo();
	}

    @PostMapping("/add")
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        studentService.deleteStudentByID(id);   
    }

    @PutMapping("update/{id}")
    public void updateStudent(
        @PathVariable("id") Long id, 
        @RequestParam(required = false) String name, 
        @RequestParam(required = false) String email) {
        studentService.updateStudentByID(id, name, email);
    }
    
}
