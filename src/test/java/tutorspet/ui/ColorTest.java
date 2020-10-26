package tutorspet.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ColorTest {

    @Test
    public void testCreateColor_validColor_success() {
        assertEquals(Color.CYAN.getHexValue(), "#E0FEFE");
        assertEquals(Color.VIOLET.getHexValue(), "#C7CEEA");
        assertEquals(Color.ORANGE.getHexValue(), "#FFC247");
        assertEquals(Color.PINK.getHexValue(), "#FFD1DC");
        assertEquals(Color.YELLOW.getHexValue(), "#FFFFD8");
        assertEquals(Color.GREEN.getHexValue(), "#B5EAD7");
        assertEquals(Color.RED.getHexValue(), "#FF756D");
        assertEquals(Color.BLUE.getHexValue(), "#A9E5FF");
        assertEquals(Color.PURPLE.getHexValue(), "#B399D4");
        assertEquals(Color.BROWN.getHexValue(), "#E1C6AC");
    }
}
