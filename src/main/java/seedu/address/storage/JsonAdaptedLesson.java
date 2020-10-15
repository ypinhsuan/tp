package seedu.address.storage;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.NumberOfOccurrences;
import seedu.address.model.lesson.Venue;
import seedu.address.model.lesson.exceptions.InvalidDayException;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";
    public static final String INVALID_FIELD_MESSAGE_FORMAT = "Lesson's %s field is invalid!";
    public static final String START_TIME_FIELD = "start time";
    public static final String END_TIME_FIELD = "end time";

    private final String startTime;
    private final String endTime;
    private final String day;
    private final int numberOfOccurrences;
    private final String venue;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("startTime") String startTime,
                             @JsonProperty("endTime") String endTime,
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

    private LocalTime convertToTime(String time, String errorMsg) throws IllegalValueException {
        try {
            return LocalTime.parse(time, Lesson.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(errorMsg);
        }
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, START_TIME_FIELD));
        }
        final LocalTime modelStartTime = convertToTime(
                startTime, String.format(INVALID_FIELD_MESSAGE_FORMAT, START_TIME_FIELD));

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, END_TIME_FIELD));
        }
        final LocalTime modelEndTime = convertToTime(
                endTime, String.format(INVALID_FIELD_MESSAGE_FORMAT, END_TIME_FIELD));

        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName()));
        }
        final Day modelDay;
        try {
            modelDay = Day.createDay(day);
        } catch (InvalidDayException e) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }

        if (!NumberOfOccurrences.isValidNumberOfOccurrences(numberOfOccurrences)) {
            throw new IllegalValueException(NumberOfOccurrences.MESSAGE_CONSTRAINTS);
        }
        final NumberOfOccurrences modelNumberOfOccurrences = new NumberOfOccurrences(numberOfOccurrences);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        if (!Venue.isValidVenue(venue)) {
            throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
        }
        final Venue modelVenue = new Venue(venue);

        return new Lesson(modelStartTime, modelEndTime, modelDay, modelNumberOfOccurrences, modelVenue);
    }
}
