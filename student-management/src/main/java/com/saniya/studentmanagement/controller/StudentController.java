package com.saniya.studentmanagement.controller;

import com.saniya.studentmanagement.dto.StudentDTO;
import com.saniya.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController = @Controller + @ResponseBody
// All methods return JSON automatically
// @RequestMapping sets the base URL for all endpoints in this controller
@RestController
@RequestMapping("/api/students")
public class StudentController {

    // Injecting the SERVICE (interface) not the implementation - good practice!
    @Autowired
    private StudentService studentService;

    // ── POST /api/students ───────────────────────────────────
    // Create a new student
    // @RequestBody maps incoming JSON to StudentDTO object
    // @Valid triggers validation annotations on the DTO
    // Returns 201 CREATED status
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    // ── GET /api/students/{id} ───────────────────────────────
    // Get student by ID
    // @PathVariable extracts {id} from the URL
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);  // 200 OK
    }

    // ── GET /api/students ────────────────────────────────────
    // Get all students
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // ── PUT /api/students/{id} ───────────────────────────────
    // Update a student (full update)
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    // ── DELETE /api/students/{id} ────────────────────────────
    // Delete a student
    // Returns 204 NO CONTENT (successful delete with no body)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
    }

    // ── GET /api/students/department/{dept} ──────────────────
    // Get students by department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentDTO>> getStudentsByDepartment(
            @PathVariable String department) {
        List<StudentDTO> students = studentService.getStudentsByDepartment(department);
        return ResponseEntity.ok(students);
    }

    // ── GET /api/students/search?name=saniya ─────────────────
    // Search students by name
    // @RequestParam extracts query parameter from URL
    @GetMapping("/search")
    public ResponseEntity<List<StudentDTO>> searchStudents(
            @RequestParam String name) {
        List<StudentDTO> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(students);
    }

    // ── GET /api/students/cgpa?min=8.0 ───────────────────────
    // Get students with CGPA above a minimum value
    @GetMapping("/cgpa")
    public ResponseEntity<List<StudentDTO>> getStudentsByCgpa(
            @RequestParam Double min) {
        List<StudentDTO> students = studentService.getStudentsByCgpa(min);
        return ResponseEntity.ok(students);
    }
}
