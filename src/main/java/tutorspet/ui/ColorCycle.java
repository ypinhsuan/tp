package tutorspet.ui;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a cycle where colors can be chosen from to set on ui objects.
 */
public class ColorCycle<T> {

    enum Color {
        CYAN("#E0FEFE"),
        VIOLET("#C7CEEA"),
        ORANGE("#FFC247"),
        PINK("#FFD1DC"),
        YELLOW("#FFFFD8"),
        GREEN("#B5EAD7"),
        RED("#FF756D"),
        BLUE("#A9E5FF"),
        PURPLE("#B399D4"),
        BROWN("#E1C6AC");

        private final String hexValue;

        Color(String hexValue) {
            this.hexValue = hexValue;
        }

        public String getHexValue() {
            return hexValue;
        }
    }

    private static final Color[] COLORS = Color.values();
    private int pointer = 0;
    private final Map<T, Color> colorMap = new HashMap<>();

    public ColorCycle() {}

    /**
     * Increments the pointer by 1.
     * If the pointer is more than the total number of {@code Color}s, reset to 0.
     */
    private void shiftPointer() {
        if (pointer == COLORS.length - 1) {
            pointer = 0;
        } else if (0 <= pointer && pointer < COLORS.length - 1) {
            pointer++;
        } else {
            assert false;
        }
    }

    /**
     * Gets the color hex value of the given key.
     * If the key already exists in {@code colorMap}, return the hex value of that key.
     * Otherwise, put the color at which the pointer is currently pointing towards into {@code colorMap}.
     * Afterwards, shift the pointer.
     */
    public String getColorHexValue(T key) {
        if (!colorMap.containsKey(key)) {
            colorMap.put(key, COLORS[pointer]);
            shiftPointer();
        }

        return colorMap.get(key).getHexValue();
    }
}
