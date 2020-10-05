package seedu.address.ui;

/**
 * Colours that can be used for tag.
 */
public enum Colour {

    CYAN("#E0FEFE"),
    VIOLET("#C7CEEA"),
    ORANGE("#FFC247"),
    PINK("#FFD1DC"),
    YELLOW("#FFFFD8"),
    GREEN("#B5EAD7"),
    RED(" #FF756D"),
    BLUE("#A9E5FF"),
    PURPLE("#B399D4"),
    BROWN("#E1C6AC");

    private final String hexValue;

    Colour(String hexValue) {
        this.hexValue = hexValue;
    }

    public String getHexValue() {
        return hexValue;
    }
}
