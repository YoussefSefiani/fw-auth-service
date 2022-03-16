package fw.authservice;

import fw.authservice.model.User;
import fw.authservice.repository.UserRepository;
import fw.authservice.service.UserService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceApplicationTests {

//	@Mock
//	private UserRepository userRepository;
//
//	@Mock
//	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
	}

//	@Test
//	public void givenUserId_whenUserExists_ThenReturnUserOptional() {
//		User user = new User(5L, "testuser", "password", true, "USER");
//		when(userRepository.findById(5L)).thenReturn(Optional.of(user));
//
//		UserService userService = new UserService(userRepository, passwordEncoder);
//
//		assertEquals(user, userService.getUser(5L));
//	}
//
//	@Test
//	public void givenUserId_whenUserIdNotFound_ThenThrowIllegalStateException() {
//		when(userRepository.findById(543L)).thenReturn(Optional.empty());
//
//		UserService userService = new UserService(userRepository, passwordEncoder);
//
//		assertThrows(IllegalStateException.class, () -> userService.getUser(543L));
//	}
//
//	@Test
//	public void givenUser_WhenRegisterSuccessful_ThenReturnUser() {
//		User user = new User(null, "testuser", "password", true, "USER");
//		String encryptedPassword = "encryptedpassword";
//		when(userRepository.findByUserNameEqualsIgnoreCase(user.getUserName())).thenReturn(Optional.empty());
//		when(userRepository.save(user)).thenReturn(user);
//		when(passwordEncoder.encode(user.getPassword())).thenReturn(encryptedPassword);
//		UserService userService = new UserService(userRepository, passwordEncoder);
//
//		User registeredUser = userService.registerUser(user);
//		assertEquals(encryptedPassword, registeredUser.getPassword());
//		assertNotEquals(null, registeredUser);
//	}
//
//	@Test
//	public void givenUser_WhenUsernameAlreadyTaken_ThenThrowIllegalStateException() {
//		User user = new User(null, "testuser", "password", true, "USER");
//		when(userRepository.findByUserNameEqualsIgnoreCase(user.getUserName())).thenReturn(Optional.of(user));
//
//		UserService userService = new UserService(userRepository, passwordEncoder);
//		assertThrows(IllegalStateException.class, () -> userService.registerUser(user));
//	}
//
//	@Test
//	public void givenUsernameAndPassword_WhenUsernameAndPasswordUpdateSuccessful_ThenReturnStatus200() throws JSONException {
//		User user = new User(12L, "testuser", "password", true, "USER");
//		User updatedUser = new User(12L, "updatedusername", "updatedpassword", true, "USER");
//
//		when(userRepository.findById(12L)).thenReturn(Optional.of(user));
//		UserService userService = new UserService(userRepository, passwordEncoder);
//		userService.updateUser(12L, updatedUser);
//
//		assertEquals("updatedusername", user.getUserName());
//		assertEquals(passwordEncoder.encode("updatedpassword"), user.getPassword());
//
//	}
//
//	@Test
//	public void givenUsername_WhenUsernameUpdateSuccessful_ThenReturnStatus200() {
//		User user = new User(12L, "testuser", "password", true, "USER");
//		User updatedUser = new User(12L, "updateduser", "password", true, "USER");
//
//		when(userRepository.findById(12L)).thenReturn(Optional.of(user));
//		UserService userService = new UserService(userRepository, passwordEncoder);
//		userService.updateUser(12L, updatedUser);
//
//		assertEquals("updateduser", user.getUserName());
//
//	}
//
//	@Test
//	public void givenPassword_WhenPasswordUpdateSuccessful_ThenReturnStatus200() {
//		User user = new User(12L, "testuser", "password", true, "USER");
//		User updatedUser = new User(12L, "test", "updatedpassword", true, "USER");
//
//		when(userRepository.findById(12L)).thenReturn(Optional.of(user));
//		UserService userService = new UserService(userRepository, passwordEncoder);
//		userService.updateUser(12L, updatedUser);
//
//		assertEquals(passwordEncoder.encode("updatedpassword"), user.getPassword());
//
//	}
//
//	@Test
//	public void givenUsername_WhenUsernameAlreadyExists_ThenThrowIllegalStateException() {
//		User user = new User(12L, "testuser", "password", true, "USER");
//		User updatedUser = new User(12L, "testuser", "password", true, "USER");
//
//		UserService userService = new UserService(userRepository, passwordEncoder);
//		assertThrows(IllegalStateException.class, () -> userService.updateUser(12L, updatedUser));
//	}
//
//	@Test
//	public void givenUserId_WhenUserIdNotFound_ThenThrowIllegalStateException() {
//		User updatedUser = new User(12L, "testuser", "password", true, "USER");
//		UserService userService = new UserService(userRepository, passwordEncoder);
//		assertThrows(IllegalStateException.class, () -> userService.updateUser(567L, updatedUser));
//	}
//
//
//	@Test
//	public void givenUserId_whenDeleteSuccessful_ThenReturnStatus200() {
//		when(userRepository.existsById(5L)).thenReturn(true);
//		UserService userService = new UserService(userRepository, passwordEncoder);
//		userService.deleteUser(5L);
//		verify(userRepository).deleteById(5L);
//	}
//
//	@Test
//	public void givenUserId_whenIdOfUserToDeleteNotFound_ThenThrowIllegalStateException() {
//		when(userRepository.existsById(567L)).thenReturn(false);
//		UserService userService = new UserService(userRepository, passwordEncoder);
//		assertThrows(IllegalStateException.class, () -> userService.deleteUser(567L));
//	}


}
