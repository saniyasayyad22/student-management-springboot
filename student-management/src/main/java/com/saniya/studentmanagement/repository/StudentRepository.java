package com.saniya.studentmanagement.repository;

import com.saniya.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// JpaRepository gives us built-in methods for FREE:
// save(), findById(), findAll(), deleteById(), count(), existsById() etc.
// We just extend it and Spring creates the implementation automatically!
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Spring Data JPA generates the SQL automatically from method name:
    // SELECT * FROM students WHERE email = ?
    Optional<Student> findByEmail(String email);

    // SELECT * FROM students WHERE department = ?
    List<Student> findByDepartment(String department);

    // Custom JPQL query - searches students by name (case-insensitive)
    @Query("SELECT s FROM Student s WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> searchByName(@Param("name") String name);

    // Find students with CGPA greater than a given value
    List<Student> findByCgpaGreaterThanEqual(Double cgpa);

    // Check if email already exists (for duplicate check)
    boolean existsByEmail(String email);
}
