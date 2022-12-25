package com.project.schoolmanagment.entity.concretes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.schoolmanagment.entity.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonProgram implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Day day;

    private LocalTime startTime;

    private LocalTime stopTime;

    @ManyToMany
    private Set<Lesson> lesson;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(mappedBy = "lessonsProgramList")
    private Set<Teacher> teachers;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(mappedBy = "lessonsProgramList")
    private Set<Student> students;

    //@OneToMany(mappedBy = "lessonProgram")
    //private List<StudentInfo> studentInfoList;
}
