package com.project.schoolmanagment.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.schoolmanagment.entity.abstracts.User;
import com.project.schoolmanagment.entity.concretes.businnes.StudentInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Teacher extends User {

	//TODO orphanRemoval should be learned
	@JsonIgnore
	@OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL,orphanRemoval = true)
	private AdvisoryTeacher advisoryTeacher;

	@Column(name ="isAdvisor")
	private Boolean isAdvisory;

	@Column(unique = true)
	private String email;

	@OneToMany(mappedBy = "teacher",cascade = CascadeType.REMOVE)
	private List<StudentInfo>studentInfos;
}
