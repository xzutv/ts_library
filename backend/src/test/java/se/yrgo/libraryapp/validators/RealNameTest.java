package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class RealNameTest {

    @Test
    void validate_InvalidMiddleName() {
        assertThat(RealName.validate("John heck Doe")).isFalse();
    }

    @Test
    void validate_ValidName() {
        assertThat(RealName.validate("John Doe")).isTrue();
    }

    @Test
    void validate_emptyName() {
        assertThat(RealName.validate("")).isTrue();
    }

    @Test
    void validate_nullName() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> RealName.validate(null));
    }

    @Test
    void validate_toUpperCase_ShouldWork() {
        assertThat(RealName.validate("John HECK Doe")).isFalse();
    }
}