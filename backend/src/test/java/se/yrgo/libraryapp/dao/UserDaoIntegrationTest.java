package se.yrgo.libraryapp.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.radcortez.flyway.test.annotation.H2;
import se.yrgo.libraryapp.entities.LoginInfo;
import se.yrgo.libraryapp.entities.User;
import se.yrgo.libraryapp.entities.UserId;

@Tag("integration")
@H2
class UserDaoIntegrationTest {
    private static DataSource ds;

    @BeforeAll
    static void initDataSource() {
        // this way we do not need to create a new datasource every time
        final JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        UserDaoIntegrationTest.ds = ds;
    }

    @Test
    void getUserById() {
    // this data comes from the test migration files
        final String username = "test";
        final UserId userId = UserId.of(1);
        UserDao userDao = new UserDao(ds);
        Optional<User> maybeUser = userDao.get(Integer.toString(userId.getId()));
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get().getName()).isEqualTo(username);
        assertThat(maybeUser.get().getId()).isEqualTo(userId);
    }

    @Test
    void getUserById_NotFound() {

        final UserId userId = UserId.of(-1);
        UserDao userDao = new UserDao(ds);
        Optional<User> maybeUser = userDao.get(Integer.toString(userId.getId()));
        assertThat(maybeUser).isEmpty();

    }

    @Test
    void getLoginInfo_ShouldSucceed() {
        final String username = "test";
        final UserId userId = UserId.of(1);
        final String hash = "$argon2i$v=19$m=16,t=2,p=1$MTIzNDU2Nzg5MDEyMzQ1NjA$LmFqTZeUWwqsnbZCS2E8XQ";

        UserDao userDao = new UserDao(ds);
        LoginInfo info = userDao.getLoginInfo(username).get();

        assertThat(info).isNotNull();
        assertThat(info.getUserId()).isEqualTo(userId);
        assertThat(info.getPasswordHash()).isEqualTo(hash);
    }

    @Test
    void getLoginInfo_wrongUsername() {
        final String username = "notFound";
        UserDao userDao = new UserDao(ds);
        assertThat(userDao.getLoginInfo(username).isEmpty()).isTrue();
    }





}