package seedu.address.testutil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.NumberOfOccurrences;
import seedu.address.model.lesson.Venue;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_START_TIME = "08:00";
    public static final String DEFAULT_END_TIME = "10:00";
    public static final String DEFAULT_DAY = "TUESDAY";
    public static final int DEFAULT_NUMBER_OF_OCCURRENCES = 13;
    public static final String DEFAULT_VENUE = "COM1-0211";

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private LocalTime startTime;
    private LocalTime endTime;
    private Day day;
    private NumberOfOccurrences numberOfOccurrences;
    private Venue venue;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() {
        startTime = LocalTime.parse(DEFAULT_START_TIME, TIME_FORMATTER);
        endTime = LocalTime.parse(DEFAULT_END_TIME, TIME_FORMATTER);
        day = Day.valueOf(DEFAULT_DAY);
        numberOfOccurrences = new NumberOfOccurrences(DEFAULT_NUMBER_OF_OCCURRENCES);
        venue = new Venue(DEFAULT_VENUE);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        startTime = lessonToCopy.getStartTime();
        endTime = lessonToCopy.getEndTime();
        day = lessonToCopy.getDay();
        numberOfOccurrences = lessonToCopy.getNumberOfOccurrence();
        venue = lessonToCopy.getVenue();
    }

    /**
     * Sets the {@code startTime} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime, TIME_FORMATTER);
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime, TIME_FORMATTER);
        return this;
    }

    /**
     * Sets the {@code day} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDay(String day) {
        this.day = Day.valueOf(day);
        return this;
    }

    /**
     * Sets the {@code numberOfOccurrences} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withNumberOfOccurrences(int numberOfOccurrences) {
        this.numberOfOccurrences = new NumberOfOccurrences(numberOfOccurrences);
        return this;
    }

    /**
     * Sets the {@code venue} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    public Lesson build() {
        return new Lesson(startTime, endTime, day, numberOfOccurrences, venue);
    }
}
