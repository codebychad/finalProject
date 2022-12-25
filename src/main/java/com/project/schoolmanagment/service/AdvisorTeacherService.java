package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.AdvisorTeacher;
import com.project.schoolmanagment.entity.concretes.Teacher;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.response.AdvisorTeacherResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.payload.response.StudentResponse;
import com.project.schoolmanagment.repository.AdvisoryTeacherRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvisorTeacherService {

    private final AdvisoryTeacherRepository advisoryTeacherRepository;
    private final UserRoleService userRoleService;

    public void saveAdvisorTeacher(Teacher teacher) {
        AdvisorTeacher advisorTeacherBuilder = AdvisorTeacher.builder().teacher(teacher).userRole(userRoleService.getUserRole(Role.ADVISORTEACHER)).build();
        advisoryTeacherRepository.save(advisorTeacherBuilder);
    }

    public void updateAdvisorTeacher(boolean status, Teacher teacher) {
        Optional<AdvisorTeacher> advisorTeacher = advisoryTeacherRepository.getAdvisorTeacherByTeacher_Id(teacher.getId());
        AdvisorTeacher.AdvisorTeacherBuilder advisorTeacherBuilder = AdvisorTeacher.builder().teacher(teacher);
        if (advisorTeacher.isPresent()) {
            advisorTeacher.get().setUserRole(userRoleService.getUserRole(Role.ADVISORTEACHER));
            if (status) {
                advisorTeacherBuilder.id(advisorTeacher.get().getId());
                advisoryTeacherRepository.save(advisorTeacherBuilder.build());
            } else {
                advisoryTeacherRepository.deleteById(advisorTeacher.get().getId());
            }
        } else {
            advisoryTeacherRepository.save(advisorTeacherBuilder.build());
        }
    }

    public List<AdvisorTeacherResponse> getAllAdvisorTeacher() {
        //response obje oluşturulacak
        return advisoryTeacherRepository.findAll().stream().map(this::createResponseObject).collect(Collectors.toList());
    }

    public Optional<AdvisorTeacher> getAdvisorTeacherById(Long id) {
        return advisoryTeacherRepository.findById(id);

    }

    public ResponseMessage deleteAdvisorTeacher(Long id) {
        Optional<AdvisorTeacher> advisorTeacher = advisoryTeacherRepository.findById(id);
        if (!advisorTeacher.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE, id));
        }
        advisoryTeacherRepository.deleteById(advisorTeacher.get().getId());
        advisoryTeacherRepository.flush();
        return ResponseMessage.<AdvisorTeacher>builder()
                .message("Advisor Teacher deleted Successfully")
                .httpStatus(HttpStatus.OK).build();
    }

    private AdvisorTeacherResponse createResponseObject(AdvisorTeacher advisorTeacher) {
        return AdvisorTeacherResponse.builder().advisorTeacherId(advisorTeacher.getId())
                .teacherName(advisorTeacher.getTeacher().getName())
                .teacherSurname(advisorTeacher.getTeacher().getSurname())
                .teacherSSN(advisorTeacher.getTeacher().getSsn()).build();
    }

}
