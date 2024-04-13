package com.example.demo.service;

import java.util.List;
import java.util.Objects;


import org.springframework.stereotype.Service;

import com.example.demo.DAL.StudentRepository;
import com.example.demo.model.Student;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudentsInfo() {
		return studentRepository.findAll();
	}

    public void addNewStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudentByID(Long id) {
        boolean exist = studentRepository.existsById(id);
        if (!exist) {
            throw new IllegalStateException("Student "+ id + " does not exist!");
        }else {
            studentRepository.deleteById(id);
        }

    }

    @Transactional
    public void updateStudentByID(Long id, String name, String email) {
        Student student =studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("studnet "+ id +" dose not exist!"));
        if(name != null && name.length() > 0 && !Objects.equals(name, student.getName())) {
            student.setName(name);
        }
        if(email != null && email.length() > 0 && !Objects.equals(email, student.getEmail())) {
            student.setEmail(email);
        }
    }
}
