package seedu.address.ui;

public enum Stylesheet {
    LIGHT("/view/LightTheme.css"),
    CHOCOLATE_MINT("/view/ChocoMintTheme.css"),
    ALTERNATE("/view/AlternateTheme.css"),
    DARK("/view/DarkTheme.css");

    private final String path;

    Stylesheet(String path) {
        this.path = path;
    }

    public String getStylesheet() {
        return getClass().getResource(path).toExternalForm();
    }
}
