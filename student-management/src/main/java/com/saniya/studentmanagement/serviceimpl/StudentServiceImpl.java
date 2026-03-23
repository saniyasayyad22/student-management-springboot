package com.saniya.studentmanagement.serviceimpl;

import com.saniya.studentmanagement.dto.StudentDTO;
import com.saniya.studentmanagement.exception.ResourceNotFoundException;
import com.saniya.studentmanagement.model.Student;
import com.saniya.studentmanagement.repository.StudentRepository;
import com.saniya.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// @Service marks this as a Spring-managed service bean
// Spring will auto-create and inject this wherever StudentService is needed
@Service
public class StudentServiceImpl implements StudentService {

    // @Autowired = Dependency Injection - Spring injects the repository automatically
    // No need to manually create: new StudentRepository() - Spring handles it!
    @Autowired
    private StudentRepository studentRepository;

    // ── CREATE ──────────────────────────────────────────────
    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Check for duplicate email before saving
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new RuntimeException("Student with email " + studentDTO.getEmail() + " already exists");
        }

        // Convert DTO → Entity (never save DTO directly to DB)
        Student student = mapToEntity(studentDTO);

        // JPA save() → INSERT INTO students ...
        Student savedStudent = studentRepository.save(student);

        // Convert Entity → DTO and return
        return mapToDTO(savedStudent);
    }

    // ── READ (single) ────────────────────────────────────────
    @Override
    public StudentDTO getStudentById(Long id) {
        // findById returns Optional<Student>
        // orElseThrow throws our custom exception if student not found
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        return mapToDTO(student);
    }

    // ── READ (all) ───────────────────────────────────────────
    @Override
    public List<StudentDTO> getAllStudents() {
        // findAll() → SELECT * FROM students
        // Using Java Streams to convert each Student entity to StudentDTO
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDTO)        // method reference - calls mapToDTO for each
                .collect(Collectors.toList());
    }

    // ── UPDATE ───────────────────────────────────────────────
    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        // First check if student exists
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        // Update fields
        existingStudent.setFirstName(studentDTO.getFirstName());
        existingStudent.setLastName(studentDTO.getLastName());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());
        existingStudent.setDepartment(studentDTO.getDepartment());
        existingStudent.setCgpa(studentDTO.getCgpa());

        // JPA save() on existing entity → UPDATE SQL
        Student updatedStudent = studentRepository.save(existingStudent);
        return mapToDTO(updatedStudent);
    }

    // ── DELETE ───────────────────────────────────────────────
    @Override
    public void deleteStudent(Long id) {
        // Check existence first
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        // deleteById → DELETE FROM students WHERE id = ?
        studentRepository.deleteById(id);
    }

    // ── SEARCH by department ─────────────────────────────────
    @Override
    public List<StudentDTO> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── SEARCH by name ───────────────────────────────────────
    @Override
    public List<StudentDTO> searchStudentsByName(String name) {
        return studentRepository.searchByName(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── FILTER by CGPA ───────────────────────────────────────
    @Override
    public List<StudentDTO> getStudentsByCgpa(Double minCgpa) {
        return studentRepository.findByCgpaGreaterThanEqual(minCgpa)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── HELPER: Entity → DTO ─────────────────────────────────
    // Private helper method - converts Student entity to StudentDTO
    private StudentDTO mapToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setDepartment(student.getDepartment());
        dto.setCgpa(student.getCgpa());
        return dto;
    }

    // ── HELPER: DTO → Entity ─────────────────────────────────
    private Student mapToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setDepartment(dto.getDepartment());
        student.setCgpa(dto.getCgpa());
        return student;
    }
}
