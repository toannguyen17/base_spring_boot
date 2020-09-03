package com.red.services.user;

import com.red.model.User;
import com.red.repository.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private static final int MAX_ATTEMPTS = 6;

	@Autowired
	private UsersRepository repository;

	@Override
	public Iterable<User> findAll() {
		return repository.findAll();
	}

	@Override
	public void save(User users) {
		repository.save(users);
	}

	@Override
	public void update(User users) {
		repository.save(users);
	}

	@Override
	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void delete(User entity) {
		repository.deleteById(entity.getId());
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public void resetFailAttempts(String email) {
		Optional<User> user = findByEmail(email);
		if (user.isPresent()){
			User users = user.get();
			users.setAttempts(0);
			users.setAccountNonLocked(true);
			update(users);
		}
	}

	@Override
	public void updateFailAttempts(String email) throws LockedException {
		Optional<User> user = findByEmail(email);
		if (user.isPresent()){
			User users = user.get();
			int attempts = users.getAttempts();
			users.setAttempts(++attempts);

			if (attempts >= MAX_ATTEMPTS) {
				users.setAccountNonLocked(false);
			}

			update(users);

			if (attempts >= MAX_ATTEMPTS) {
				throw new LockedException("User Account is locked!");
			}
		}
	}
}
