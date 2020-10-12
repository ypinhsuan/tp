package seedu.address.ui;

public enum Stylesheet {
    LIGHT("/view/LightTheme.css"),
    ALTERNATE("/view/AlternateTheme.css"),
    DARK("/view/DarkTheme.css"),
    EXTENSION("/view/Extensions.css");

    private final String path;

    Stylesheet(String path) {
        this.path = path;
    }

    public String getStylesheet() {
        return getClass().getResource(path).toExternalForm();
    }
}
