package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TelegramTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Telegram(null));
    }

    @Test
    public void constructor_invalidTelegram_throwsIllegalArgumentException() {
        String invalidTelegram = "";
        assertThrows(IllegalArgumentException.class, () -> new Telegram(invalidTelegram));
    }

    private static Stream<Arguments> getIsValidTelegramArguments() {
        return Stream.of(
                // invalid telegram handles
                Arguments.of(false, ""), // empty string
                Arguments.of(false, "tEle"), // less than 4 characters
                Arguments.of(false, "te1e-gram"), // usage of special character
                Arguments.of(false, "Tele Gram"), // usage of white space

                // valid telegram handles
                Arguments.of(true, "Tel3g"), // exactly 5 characters
                Arguments.of(true, "gR4MYeLe_pK8"), // special and upper case alphanumeric characters
                Arguments.of(true, "alex_merier3471") // typical telegram handle
        );
    }

    @ParameterizedTest
    @MethodSource("getIsValidTelegramArguments")
    public void isValidTelegram(boolean expected, String telegram) {
        assertEquals(expected, Telegram.isValidTelegram(telegram));
    }

    @Test
    public void isValidTelegram_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> Telegram.isValidTelegram(null));
    }
}
