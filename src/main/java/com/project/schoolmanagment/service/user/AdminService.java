package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.entity.concretes.user.Admin;
import com.project.schoolmanagment.payload.mappers.AdminMapper;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.user.AdminRequest;
import com.project.schoolmanagment.payload.response.message.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.AdminResponse;
import com.project.schoolmanagment.repository.user.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminMapper adminMapper;
	private final AdminRepository adminRepository;

	public ResponseMessage<AdminResponse>saveAdmin(AdminRequest adminRequest){

		// we must map DTO -> Entity
		Admin admin = adminMapper.mapAdminRequestToAdmin(adminRequest);

		Admin savedAdmin= adminRepository.save(admin);

		// we are returning response DTO by mapping the saved version of admin.
		return ResponseMessage.<AdminResponse>builder()
				.message(SuccessMessages.ADMIN_CREATE)
				.object(adminMapper.mapAdminToAdminResponse(savedAdmin))
				.build();

	}
}
