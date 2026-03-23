package com.saniya.studentmanagement.service;

import com.saniya.studentmanagement.dto.StudentDTO;

import java.util.List;

// Service Interface - defines the CONTRACT (what operations are available)
// Using an interface is good OOP practice:
// - Loose coupling between layers
// - Easy to swap implementations (e.g. for testing with mocks)
// - Follows the Dependency Inversion Principle
public interface StudentService {

    // Create a new student
    StudentDTO createStudent(StudentDTO studentDTO);

    // Get student by ID
    StudentDTO getStudentById(Long id);

    // Get all students
    List<StudentDTO> getAllStudents();

    // Update student
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    // Delete student
    void deleteStudent(Long id);

    // Get students by department
    List<StudentDTO> getStudentsByDepartment(String department);

    // Search students by name
    List<StudentDTO> searchStudentsByName(String name);

    // Get students by minimum CGPA
    List<StudentDTO> getStudentsByCgpa(Double minCgpa);
}
