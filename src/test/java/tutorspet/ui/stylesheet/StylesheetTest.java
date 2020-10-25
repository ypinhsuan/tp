package tutorspet.ui.stylesheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static tutorspet.ui.stylesheet.Stylesheet.constructStylesheet;

import org.junit.jupiter.api.Test;

import tutorspet.ui.stylesheet.exceptions.StylesheetException;

class StylesheetTest {

    @Test
    public void lightThemeExists() {
        try {
            Stylesheet.LIGHT.getStylesheet();
        } catch (StylesheetException e) {
            fail();
        }
    }

    @Test
    public void alternateThemeExists() {
        try {
            Stylesheet.ALTERNATE.getStylesheet();
        } catch (StylesheetException e) {
            fail();
        }
    }

    @Test
    public void darkThemeExists() {
        try {
            Stylesheet.DARK.getStylesheet();
        } catch (StylesheetException e) {
            fail();
        }
    }

    @Test
    public void extensionsExists() {
        try {
            Stylesheet.EXTENSION.getStylesheet();
        } catch (StylesheetException e) {
            fail();
        }
    }

    @Test
    public void testConstructStyleSheet() {
        assertEquals(Stylesheet.LIGHT, constructStylesheet("LightTheme.css"));
        assertEquals(Stylesheet.ALTERNATE, constructStylesheet("AlternateTheme.css"));
        assertEquals(Stylesheet.DARK, constructStylesheet("DarkTheme.css"));
        assertEquals(Stylesheet.DEFAULT_STYLESHEET, constructStylesheet("invalid"));
    }
}
