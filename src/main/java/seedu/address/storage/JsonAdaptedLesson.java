package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.components.tag.Tag;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.NumberOfOccurrences;
import seedu.address.model.lesson.Venue;
import seedu.address.model.lesson.exceptions.InvalidDayException;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
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
    private final int numberOfOccurrences;
    private final String venue;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime,
                             @JsonProperty("day") String day,
                             @JsonProperty("numberOfOccurrences") int numberOfOccurrences,
                             @JsonProperty("venue") String venue) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.numberOfOccurrences = numberOfOccurrences;
        this.venue = venue;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        day = source.getDay().toString();
        numberOfOccurrences = source.getNumberOfOccurrences().value;
        venue = source.getVenue().toString();
    }

    private LocalTime convertToTime(String time) throws IllegalValueException {
        try {
            return LocalTime.parse(time, Lesson.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(INVALID_FIELD_MESSAGE_FORMAT);
        }
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (startTime == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        final LocalTime modelStartTime = convertToTime(startTime);

        if (endTime == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        final LocalTime modelEndTime = convertToTime(endTime);

        if (day == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        final Day modelDay;
        try {
            modelDay = Day.createDay(day);
        } catch (InvalidDayException e) {
            throw new IllegalValueException(INVALID_FIELD_MESSAGE_FORMAT);
        }

        if (!NumberOfOccurrences.isValidNumberOfOccurrences(numberOfOccurrences)) {
            throw new IllegalValueException(INVALID_FIELD_MESSAGE_FORMAT);
        }
        final NumberOfOccurrences modelNumberOfOccurrences = new NumberOfOccurrences(numberOfOccurrences);

        if (venue == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (!Venue.isValidVenue(venue)) {
            throw new IllegalValueException(INVALID_FIELD_MESSAGE_FORMAT);
        }
        final Venue modelVenue = new Venue(venue);

        return new Lesson(modelStartTime, modelEndTime, modelDay, modelNumberOfOccurrences, modelVenue);
    }
}
