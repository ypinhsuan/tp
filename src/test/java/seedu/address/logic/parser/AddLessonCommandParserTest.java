package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.CLASS_INDEX_DESC_LESSON_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_LESSON_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_LESSON_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_LESSON_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_LESSON_WED_2_TO_4;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalLesson.LESSON_WED_2_TO_4;
import static seedu.address.testutil.TypicalLesson.LESSON_FRI_8_TO_10;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;

public class AddLessonCommandParserTest {

    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Lesson expectedLesson = new LessonBuilder(LESSON_WED_2_TO_4).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CLASS_INDEX_DESC_LESSON_WED_2_TO_4
                + DAY_DESC_LESSON_WED_2_TO_4 + START_TIME_DESC_LESSON_WED_2_TO_4
                + END_TIME_DESC_LESSON_WED_2_TO_4 + VENUE_DESC_LESSON_WED_2_TO_4
                + NUMBER_OF_OCCURRENCES_DESC_LESSON_WED_2_TO_4,
                new AddLessonCommand(INDEX_FIRST_ITEM, expectedLesson));

        // multiple days - last day accepted
    }
}
