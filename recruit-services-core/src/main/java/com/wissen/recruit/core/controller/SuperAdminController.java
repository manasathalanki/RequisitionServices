
package com.wissen.recruit.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/superadmin")
public interface SuperAdminController {

//	@Autowired
//	SuperAdminService superAdminService;
//
//	Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
//
//	@GetMapping("/")
//	public String getDefaultInformation(HttpServletRequest req) {
//		return String.valueOf(req.getLocalPort());
//	}
//
//	/**
//	 * API creates user with either admin or user role
//	 * 
//	 * @param user
//	 * @return user
//	 */
//
//	/**
//	 * API used to reset to the default password by superAdmin to the admin or user
//	 * 
//	 * @param userId
//	 * @return
//	 * @throws NotFoundException
//	 */
//	@SuppressWarnings("unused")
//	@PutMapping("/resetpswd/{userId}")
//	public ResponseEntity<?> resetPswd(@PathVariable("userId") String userId) throws NotFoundException {
//		logger.info("super admin change password of the other user admin started... ");
//		User user = superAdminService.resetPswd(userId);
//		if (user != null) {
//			logger.info("super admin resets default password to the user with userId successfuly - {}", userId);
//			return new ResponseEntity<>(user, HttpStatus.OK);
//		} else {
//			logger.error("super admin password reset for the user with userId-{} was unsuccessful", userId);
//			return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
//		}
//	}
}
