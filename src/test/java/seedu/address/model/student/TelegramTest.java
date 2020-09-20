package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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

    @Test
    public void isValidTelegram() {
        // null telegram handle
        assertThrows(NullPointerException.class, () -> Telegram.isValidTelegram(null));

        // invalid telegram handle
        assertFalse(Telegram.isValidTelegram("")); // empty string
        assertFalse(Telegram.isValidTelegram(" ")); // spaces only
        assertFalse(Telegram.isValidTelegram("91")); // less than 3 numbers
        assertFalse(Telegram.isValidTelegram("telegram")); // non-numeric
        assertFalse(Telegram.isValidTelegram("9011p041")); // alphabets within digits
        assertFalse(Telegram.isValidTelegram("9312 1534")); // spaces within digits

        // valid telegram handle
        assertTrue(Telegram.isValidTelegram("911")); // exactly 3 numbers
        assertTrue(Telegram.isValidTelegram("93121534"));
        assertTrue(Telegram.isValidTelegram("124293842033123")); // long telegram handle
    }
}
