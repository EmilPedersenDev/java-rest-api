package com.javarest.javarestapi.course;

import com.javarest.javarestapi.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
