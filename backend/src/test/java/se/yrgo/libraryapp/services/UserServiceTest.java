package se.yrgo.libraryapp.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.yrgo.libraryapp.dao.UserDao;
import se.yrgo.libraryapp.entities.LoginInfo;
import se.yrgo.libraryapp.entities.UserId;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Test
    void isNameAvailable_true() {
        final String username = "username";
        final PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();

        UserService userService = new UserService(userDao, encoder);

        when(userDao.isNameAvailable(username)).thenReturn(true);

        assertThat(userService.isNameAvailable(username)).isTrue();
    }

    @Test
    void isNameAvailable_false() {
        final String username = "username";
        final PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
        UserService userService = new UserService(userDao, encoder);
        when(userDao.isNameAvailable(username)).thenReturn(false);
        assertThat(userService.isNameAvailable(username)).isFalse();
    }

    @Test
    void registerUser_ShouldSucceed() {
        final String username = "username";
        final String realName = "Hassan O'riley";
        final String password = "password";
        final PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();

        UserService userService = new UserService(userDao, encoder);

        when(userDao.register(eq(username), eq("Hassan O\\'riley"), eq(password))).thenReturn(true);

        assertThat(userService.register(username, realName, password)).isTrue();

    }

    @Test
    void registerWithInvalidUsername_ShouldReturnFalse() {
        final String username = "123";
        final String realName = "Hassan";
        final String password = "password";
        final PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();

        UserService userService = new UserService(userDao, encoder);

        assertThat(userService.register(username, realName, password)).isFalse();
    }

    @Test
    @SuppressWarnings("deprecation")
    void correctLogin() {
        final String userId = "1";
        final UserId id = UserId.of(userId);
        final String username = "testuser";
        final String password = "password";
        final String passwordHash = "password";
        final LoginInfo info = new LoginInfo(id, passwordHash);
        final PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();

        when(userDao.getLoginInfo(username)).thenReturn(Optional.of(info));
        UserService userService = new UserService(userDao, encoder);
        assertThat(userService.validate(username,
                password)).isEqualTo(Optional.of(id));
    }
}