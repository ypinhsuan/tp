package tutorspet.ui;

import static tutorspet.model.lesson.Lesson.TIME_FORMATTER;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import tutorspet.model.lesson.Lesson;

/**
 * A UI component that displays information of a {@code Lesson}.
 */
public class LessonCard extends UiPart<Region> {

    private static final String FXML = "LessonCard.fxml";

    public final Lesson lesson;

    @FXML
    private HBox lessonCard;
    @FXML
    private Label index;
    @FXML
    private Label dayAndTime;
    @FXML
    private Label venue;
    @FXML
    private Label occurrence;

    /**
     * Creates a {@code Lesson} with the given {@code lesson} and index to display.
     */
    public LessonCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;

        final StringBuilder builder = new StringBuilder();
        builder.append(displayedIndex)
                .append(". ")
                .append(lesson.getDay())
                .append(" ")
                .append(TIME_FORMATTER.format(lesson.getStartTime()))
                .append(" to ")
                .append(TIME_FORMATTER.format(lesson.getEndTime()));

        dayAndTime.setText(builder.toString());
        venue.setText("Venue: " + lesson.getVenue().toString());
        occurrence.setText("Number of Occurrences: " + lesson.getNumberOfOccurrences().toString());
        setStyle(displayedIndex);
    }

    private void setStyle(int index) {
        if (index % 2 == 0) {
            lessonCard.getStyleClass().add("cell_lesson_label_even");
        } else {
            lessonCard.getStyleClass().add("cell_lesson_label_odd");
        }
    }

    @Override
    public boolean equals(Object other) {

        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonCard)) {
            return false;
        }

        // state check
        LessonCard card = (LessonCard) other;
        return index.getText().equals(card.index.getText())
                && lesson.equals(card.lesson);
    }
}
