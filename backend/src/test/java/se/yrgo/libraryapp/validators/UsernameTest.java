package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class UsernameTest {

    @Test
    void correctUsername() {

        assertThat(Username.validate("Bosse")).isTrue();
    }

    @ValueSource(strings = {"name with space", "123!!!||12", "ab", "ab3c"})
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
