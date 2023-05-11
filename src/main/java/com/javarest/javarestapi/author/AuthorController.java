package com.javarest.javarestapi.author;

import com.javarest.javarestapi.course.Course;
import com.javarest.javarestapi.course.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorRepository repository;

    private CourseRepository courseRepository;

    public AuthorController(AuthorRepository repository, CourseRepository courseRepository) {
        this.repository = repository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid author id found");
        }

        Optional<Author> author = repository.findById(id);

        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        } else {
            return new ResponseEntity<>(author.get(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<List<Course>> getCoursesCreatedByAuthor(@PathVariable long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid author id found");
        }

        Optional<Author> author = repository.findById(id);

        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        } else {
            return new ResponseEntity<>(author.get().getCourses(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        if (author.getName() == null || author.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author name cannot be empty");
        } else {
            repository.save(author);
            return new ResponseEntity<>(author, HttpStatus.CREATED);
        }
    }

    @PostMapping("/{id}/courses")
    public ResponseEntity<List<Course>> addCourseCreatedByAuthor(@PathVariable long id, @Valid @RequestBody Course course) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid author id found");
        }

        Optional<Author> author = repository.findById(id);

        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the author");
        } else {
            course.setAuthor(author.get());
            courseRepository.save(course);
            return new ResponseEntity<>(author.get().getCourses(), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}/courses")
    public ResponseEntity<Author> updateCourseCreatedByAuthor(@PathVariable long id, @Valid @RequestBody Course course) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid author id found");
        }

        Optional<Author> author = repository.findById(id);

        if (author.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the author");
        }


        Optional<Course> savedCourse = courseRepository.findById(course.getId());

        if (savedCourse.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No course was found");
        }

        course.setAuthor(author.get());
        courseRepository.save(course);
        return new ResponseEntity<>(author.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid author id found");
        } else {
            Optional<Author> author = repository.findById(id);

            if (author.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
            }

//            for (Course course : author.get().getCourses()) {
//                courseRepository.deleteById(course.getId());
//            }
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
