package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UtilsTest {


    @Test
    void onlyLettersAndWhiteSpace() {
        assertThat(Utils.onlyLettersAndWhitespace("Hejsan-HOPPSAN")).isEqualTo("hejsanhoppsan");
    }

    @Test
    void onlyLettersAndWhiteSpace_null() {
        assertThat(Utils.onlyLettersAndWhitespace(null)).isNull();
    }

    @Test
    void onlyLettersAndWhiteSpace_empty() {
        assertThat(Utils.onlyLettersAndWhitespace("")).isEmpty();
    }

    @Test
    void onlyLettersAndWhiteSpace_whiteSpace() {
        assertThat(Utils.onlyLettersAndWhitespace(" ")).isEqualTo(" ");
    }


    @Test
    void cleanAndUnleet() {
        assertThat(Utils.cleanAndUnLeet("H3js4n")).isEqualTo("hejsan");
        assertThat(Utils.cleanAndUnLeet("h0pps4n")).isEqualTo("hoppsan");
        assertThat(Utils.cleanAndUnLeet("7u83n")).isEqualTo("tuben");
    }

    @Test
    void cleanAndUnleet_null() {
        assertThat(Utils.cleanAndUnLeet(null)).isNull();
    }

    @Test
    void cleanAndUnleet_empty() {
        assertThat(Utils.cleanAndUnLeet("")).isEmpty();
    }

    @Test
    void cleanAndUnleet_whiteSpace() {
        assertThat(Utils.cleanAndUnLeet(" ")).isEqualTo(" ");
    }

    @Test
    void cleanAndUnleet_B_OR_B() {
        assertThat(Utils.cleanAndUnLeet("6ajen")).isEqualTo("bajen");
        assertThat(Utils.cleanAndUnLeet("8ajen")).isEqualTo("bajen");
    }

    @Test
    void cleanAndUnLeet_specialKey() {
        assertThat(Utils.cleanAndUnLeet("6a-jen")).isEqualTo("bajen");
    }





}
