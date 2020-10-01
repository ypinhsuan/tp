package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.moduleclass.ModuleClass;

/**
 * A UI component that displays information of a {@code ModuleClass}.
 */
public class ModuleClassCard extends UiPart<Region> {

    private static final String FXML = "ModuleClassListCard.fxml";

    public final ModuleClass moduleClass;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label studentCount;

    /**
     * Creates a {@code ModuleCode} with the given {@code ModuleClass} and index to display.
     */
    public ModuleClassCard(ModuleClass moduleClass, int displayedIndex) {
        super(FXML);
        this.moduleClass = moduleClass;
        id.setText(displayedIndex + ". ");
        name.setText(moduleClass.getName().fullName);
        studentCount.setText(Integer.toString(moduleClass.getStudentUuids().size()));
    }

    @Override
    public boolean equals(Object other) {

        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleClassCard)) {
            return false;
        }

        // state check
        ModuleClassCard card = (ModuleClassCard) other;
        return id.getText().equals(card.id.getText())
                && moduleClass.equals(card.moduleClass);
    }
}
