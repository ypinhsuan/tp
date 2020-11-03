package tutorspet.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tutorspet.ui.ColorCycle.Color;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColorCycleTest {

    private static final Color[] COLORS = Color.values();

    private ColorCycle<MockClass> colorCycle;
    private MockClass defaultMockClass;

    @BeforeEach
    public void setUp() {
        colorCycle = new ColorCycle<>();
        defaultMockClass = new MockClass("Default", 0);
    }

    @Test
    public void getColorHexValue_emptyColorCycle_success() {
        assertEquals(COLORS[0].getHexValue(), colorCycle.getColorHexValue(defaultMockClass));
    }

    @Test
    public void getColorHexValue_existingColor_success() {
        colorCycle.getColorHexValue(defaultMockClass);
        assertEquals(COLORS[0].getHexValue(), colorCycle.getColorHexValue(defaultMockClass));
    }

    @Test
    public void getColorHexValue_multipleColors_success() {
        colorCycle.getColorHexValue(defaultMockClass);
        colorCycle.getColorHexValue(new MockClass("1", 1));
        colorCycle.getColorHexValue(new MockClass("2", 2));
        colorCycle.getColorHexValue(new MockClass("3", 3));
        assertEquals(COLORS[0].getHexValue(), colorCycle.getColorHexValue(defaultMockClass));

        // re-instantiate MockClass to ensure equality is not due to reference
        assertEquals(COLORS[2].getHexValue(), colorCycle.getColorHexValue(new MockClass("2", 2)));
        assertNotEquals(colorCycle.getColorHexValue(new MockClass("1", 1)),
                colorCycle.getColorHexValue(new MockClass("2", 2)));
    }

    @Test
    public void getColorHexValue_exceedTotalColors_success() {
        for (int i = 0; i < COLORS.length * 2; i++) {
            colorCycle.getColorHexValue(new MockClass(String.valueOf(i), i));
        }

        assertNotEquals(colorCycle.getColorHexValue(new MockClass("0", 0)),
                colorCycle.getColorHexValue(new MockClass(String.valueOf(COLORS.length - 1), COLORS.length - 1)));
        assertEquals(colorCycle.getColorHexValue(new MockClass("1", 1)),
                colorCycle.getColorHexValue(new MockClass(String.valueOf(COLORS.length + 1), COLORS.length + 1)));
        assertEquals(colorCycle.getColorHexValue(new MockClass("3", 3)),
                colorCycle.getColorHexValue(new MockClass(String.valueOf(COLORS.length + 3), COLORS.length + 3)));
    }

    private class MockClass {

        private final String s;
        private final int x;

        public MockClass(String s, int x) {
            this.s = s;
            this.x = x;
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                    || (other instanceof MockClass
                    && ((MockClass) other).s.equals(s))
                    && ((MockClass) other).x == x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(s, x);
        }
    }
}
