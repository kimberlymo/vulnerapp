package ch.bbw.m183.vulnerapp.service;

import java.util.stream.Stream;

import ch.bbw.m183.vulnerapp.datamodel.UserEntity;
import ch.bbw.m183.vulnerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@PreAuthorize("hasAuthority('ADMIN')")
	public UserEntity createUser(UserEntity newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<UserEntity> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteUser(String username) {
		userRepository.deleteById(username);
	}

	@EventListener(ContextRefreshedEvent.class)
	public void loadTestUsers() {
		Stream.of(new UserEntity().setUsername("admin@admin.ch").setFullname("Super Admin").setPassword("super5ecret").setRole("ADMIN"),
						new UserEntity().setUsername("fuu@fuu.ch").setFullname("Johanna Doe").setPassword("barbarbar").setRole("USER"))
				.forEach(this::createUser);
	}
}
