package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";
    public static final String INVALID_FIELD_MESSAGE_FORMAT = "Lesson's %s field is invalid!";

    private final String startTime;
    private final String endTime;
    private final String day;
    private final String numberOfOccurences;
    private final String venue;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime,
                             @JsonProperty("day") String day,
                             @JsonProperty("numberOfOccurences") String numberOfOccurences,
                             @JsonProperty("venue") String venue) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.numberOfOccurences = numberOfOccurences;
        this.venue = venue;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        day = source.getDay().toString();
        numberOfOccurences = source.getNumberOfOccurrences().toString();
        venue = source.getVenue().toString();
    }
}
