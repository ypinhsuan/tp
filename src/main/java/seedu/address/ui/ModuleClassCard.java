package seedu.address.ui;

import static seedu.address.model.lesson.Lesson.TIME_FORMATTER;

import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.lesson.Lesson;
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
    private VBox vBox;
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

        List<Lesson> lessons = moduleClass.getLessons();
        int index = 1;
        for (Lesson lesson: lessons) {
            vBox.getChildren().add(createLessonLabel(index, lesson));
            index++;
        }
    }

    private Label createLessonLabel(int index, Lesson lesson) {
        final StringBuilder builder = new StringBuilder();
        builder.append(index)
                .append(". ")
                .append(lesson.getDay())
                .append(" ")
                .append(TIME_FORMATTER.format(lesson.getStartTime()))
                .append(" to ")
                .append(TIME_FORMATTER.format(lesson.getEndTime()))
                .append("\nVenue: ")
                .append(lesson.getVenue())
                .append("\nNumber of occurrences: ")
                .append(lesson.getNumberOfOccurrences());
        Label label = new Label(builder.toString());
        label.setPadding(new Insets(0, 0, 15, 0));
        return label;
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
