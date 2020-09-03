package com.red.services.user;

import com.red.model.User;
import com.red.services.IGeneralService;
import org.springframework.security.authentication.LockedException;

import java.util.Optional;

public interface UserService extends IGeneralService<User> {
	Optional<User> findByEmail(String email);
	void resetFailAttempts(String email);
	void updateFailAttempts(String email) throws LockedException;
}
