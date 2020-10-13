package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLesson {

    public static final Lesson WED_2_TO_4 = new LessonBuilder().withStartTime("14:00").withEndTime("16:00")
            .withDay("WEDNESDAY").withNumberOfOccurrences(7).withVenue("COM1-B111").build();

    public static final Lesson FRI_8_TO_10 = new LessonBuilder().withStartTime("08:00").withEndTime("10:00")
            .withDay("FRIDAY").withNumberOfOccurrences(13).withVenue("S17-0302").build();

    public static final Lesson THU_10_TO_11 = new LessonBuilder().withStartTime("10:00").withEndTime("11:00")
            .withDay("THURSDAY").withNumberOfOccurrences(10).withVenue("AS6-0211").build();

    private TypicalLesson() {} // prevents instantiation

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(WED_2_TO_4, FRI_8_TO_10, THU_10_TO_11));
    }
}
