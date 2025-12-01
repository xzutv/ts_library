package se.yrgo.libraryapp.validators;

import com.typesafe.config.ConfigException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class UtilsTest {

    @Test
    void onlyLettersAndWhiteSpace_whiteSpaceOnly_shouldReturnEmptyString() {
        assertThat(Utils.onlyLettersAndWhitespace("      ")).isEqualTo("");
    }

    @Test
    void onlyLettersAndWhiteSpace_empty() {
        assertThat(Utils.onlyLettersAndWhitespace("")).isEmpty();
    }

    @Test
    void onlyLettersAndWhiteSpace_ShouldRemoveWhiteSpaceAtStartAndEnd() {
        assertThat(Utils.onlyLettersAndWhitespace("  nowhite space  ")).isEqualTo("nowhite space");
    }

    @ParameterizedTest
    @CsvSource({"abc123def, abcdef", "hällö, hällö,", "12345,''", "hello@world!, helloworld"})
    void onlyLettersAndWhiteSpace_specialCharacters(String input, String expected) {
        assertThat(Utils.onlyLettersAndWhitespace(input)).isEqualTo(expected);
    }


    @Test
    void onlyLettersAndWhiteSpace_Null_ShouldThrowNullPointerException() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> Utils.onlyLettersAndWhitespace(null));
    }

    @Test
    void cleanAndUnLeet_Null_ShouldThrowNullPointerException() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> Utils.cleanAndUnLeet(null));
    }

    @Test
    void cleanAndUnleet_EmptyString_ShouldReturnEmptyString() {
        assertThat(Utils.cleanAndUnLeet("")).isEqualTo("");
    }

    @ParameterizedTest
    @CsvSource({"H3j S4N, hej san", "h0pps4n, hoppsan", "7u83n, tuben", "6ajen, bajen", "8ajen, bajen", "tub-en, tuben", "31337, eleet", "hello, hello"})
    void cleanAndUnleet(String input, String expected) {
        assertThat(Utils.cleanAndUnLeet(input)).isEqualTo(expected);
    }






}
