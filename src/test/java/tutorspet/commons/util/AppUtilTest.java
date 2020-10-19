package tutorspet.commons.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tutorspet.commons.util.AppUtil.checkArgument;
import static tutorspet.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AppUtilTest {

    @Test
    public void getImage_exitingImage() {
        assertNotNull(AppUtil.getImage("/images/address_book_32.png"));
    }

    @Test
    public void getImage_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.getImage(null));
    }

    @Test
    public void checkArgument_true_nothingHappens() {
        checkArgument(true);
        checkArgument(true, "");
    }

    @Test
    public void checkArgument_falseWithoutErrorMessage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> checkArgument(false));
    }

    @Test
    public void checkArgument_falseWithErrorMessage_throwsIllegalArgumentException() {
        String errorMessage = "error message";
        assertThrows(IllegalArgumentException.class, errorMessage, () -> checkArgument(false, errorMessage));
    }
}
