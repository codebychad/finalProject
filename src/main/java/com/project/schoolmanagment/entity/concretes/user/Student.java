package com.project.schoolmanagment.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.schoolmanagment.entity.abstracts.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Student extends User {

	private String motherName;

	private String fatherName;

	private int studentNumber;

	private boolean isActive;

	@Column(unique = true)
	private String email;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "advisory_teacher_id")
	private AdvisoryTeacher advisoryTeacher;




}
