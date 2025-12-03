package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class UsernameTest {

    @ParameterizedTest
    @ValueSource(strings = {"b@sse", "A@._-", "ab.123", "ab3c", "test-user_123@com.com", "abc123def456ghi789jkl0longname"})
    void correctUsername(String name) {

        assertThat(Username.validate(name)).isTrue();
    }

    @ValueSource(strings = {"name with space", "123!!!||12", "ab", "._-@", "abc123def456ghi789jkl0longnameasdas"})
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
