package tutorspet.testutil;

import static java.time.LocalTime.parse;
import static tutorspet.model.lesson.Day.FRIDAY;
import static tutorspet.model.lesson.Day.THURSDAY;
import static tutorspet.model.lesson.Day.TUESDAY;
import static tutorspet.model.lesson.Day.WEDNESDAY;
import static tutorspet.model.lesson.Lesson.TIME_FORMATTER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tutorspet.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLesson {

    public static final Lesson LESSON_WED_2_TO_4 = new LessonBuilder()
            .withStartTime(parse("14:00", TIME_FORMATTER))
            .withEndTime(parse("16:00", TIME_FORMATTER))
            .withDay(WEDNESDAY).withNumberOfOccurrences(7).withVenue("COM1-B111").build();

    public static final Lesson LESSON_FRI_8_TO_10 = new LessonBuilder()
            .withStartTime(parse("08:00", TIME_FORMATTER))
            .withEndTime(parse("10:00", TIME_FORMATTER))
            .withDay(FRIDAY).withNumberOfOccurrences(13).withVenue("S17-0302").build();

    public static final Lesson LESSON_THU_10_TO_11 = new LessonBuilder()
            .withStartTime(parse("10:00", TIME_FORMATTER))
            .withEndTime(parse("11:00", TIME_FORMATTER))
            .withDay(THURSDAY).withNumberOfOccurrences(10).withVenue("AS6-0211").build();

    public static final Lesson ONLINE_LESSON_TUE_1030_1130 = new LessonBuilder()
            .withStartTime(parse("10:30", TIME_FORMATTER))
            .withEndTime(parse("11:30", TIME_FORMATTER))
            .withDay(TUESDAY).withNumberOfOccurrences(3)
            .withVenue("https://zoom/j/95317249?)pwd=Ulld2tWY3MwMkRibjQyUkdZZz09").build();

    public static final Lesson ONLINE_LESSON_WED_1_TO_3 = new LessonBuilder()
            .withStartTime(parse("13:00", TIME_FORMATTER))
            .withEndTime(parse("15:00", TIME_FORMATTER))
            .withDay(WEDNESDAY).withNumberOfOccurrences(3)
            .withVenue("zoom").build();

    private TypicalLesson() {} // prevents instantiation

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(LESSON_WED_2_TO_4, LESSON_FRI_8_TO_10, LESSON_THU_10_TO_11,
                ONLINE_LESSON_TUE_1030_1130, ONLINE_LESSON_WED_1_TO_3));
    }
}
