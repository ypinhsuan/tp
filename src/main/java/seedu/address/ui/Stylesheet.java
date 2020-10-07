package seedu.address.ui;

public enum Stylesheet {
    LIGHT("/view/LightTheme.css"),
    CHOCOLATE_MINT("/view/PastelTheme.css"),
    ALTERNATE("/view/GreenTheme.css"),
    DARK("/view/DarkTheme.css");

    private final String path;

    Stylesheet(String path) {
        this.path = path;
    }

    public String getStylesheet() {
        return getClass().getResource(path).toExternalForm();
    }
}
