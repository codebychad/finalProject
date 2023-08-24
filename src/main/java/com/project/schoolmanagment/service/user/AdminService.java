package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.entity.concretes.user.Admin;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.mappers.AdminMapper;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.user.AdminRequest;
import com.project.schoolmanagment.payload.response.message.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.AdminResponse;
import com.project.schoolmanagment.repository.user.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminMapper adminMapper;
	private final AdminRepository adminRepository;
	private final UserRoleService userRoleService;

	public ResponseMessage<AdminResponse>saveAdmin(AdminRequest adminRequest){

		// we must map DTO -> Entity
		Admin admin = adminMapper.mapAdminRequestToAdmin(adminRequest);

		//we are setting role type from roles table
		admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

		//superAdmin will not be deleted or changed
		if(Objects.equals(adminRequest.getUsername(),"superAdmin")){
			admin.setBuiltIn(true);
		}

		Admin savedAdmin= adminRepository.save(admin);

		// we are returning response DTO by mapping the saved version of admin.
		return ResponseMessage.<AdminResponse>builder()
				.message(SuccessMessages.ADMIN_CREATE)
				.object(adminMapper.mapAdminToAdminResponse(savedAdmin))
				.build();

	}
}
