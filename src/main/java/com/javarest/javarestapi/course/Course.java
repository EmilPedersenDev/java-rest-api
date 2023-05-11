package com.javarest.javarestapi.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javarest.javarestapi.author.Author;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue
    private long id;
    @NotEmpty(message = "The name cannot be empty")
    private String name;
    @NotEmpty(message = "The link cannot be empty")
    @URL(message = "The link must be a valid URL")
    private String link;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Author author;


    public Course() {
    }

    public Course(long id, String name, String link, Author author) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", author=" + author +
                '}';
    }
}
