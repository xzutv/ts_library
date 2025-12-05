package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class UsernameTest {

    @ParameterizedTest
    @ValueSource(strings = {"123ABC","b@sse", "A@._-", "ab.123", "ab3c", "test-user_123@com.com", "abc1234LongUserNameUpto30chars"})
    void correctUsername(String name) {

        assertThat(Username.validate(name)).isTrue();
    }

    @ValueSource(strings = {"12345", "name with space", "123!!!||12", "ab", "._-@", "abc1234LongUserNameLongerThan30Chars"})
    @ParameterizedTest
    void incorrectUsername(String name) {

        assertThat(Username.validate(name)).isFalse();
    }

    @Test
    void nullUsername() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> Username.validate(null));
    }

    @Test
    void emptyUsername() {
        assertThat(Username.validate("")).isFalse();
    }

    @Test
    void blankUsername() {
        assertThat(Username.validate("      ")).isFalse();
    }


}
