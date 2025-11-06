package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsernameTest {
    @Test
    void correctUsername() {

        assertThat(Username.validate("Bosse")).isTrue();
    }

    @Test
    void incorrectUsername() {

        assertThat(Username.validate("name with space")).isFalse();
    }
}
