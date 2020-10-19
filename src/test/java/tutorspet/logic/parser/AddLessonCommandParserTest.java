package tutorspet.logic.parser;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.Messages;
import tutorspet.logic.commands.AddLessonCommand;
import tutorspet.logic.commands.CommandTestUtil;
import tutorspet.model.lesson.Day;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.lesson.NumberOfOccurrences;
import tutorspet.model.lesson.Venue;
import tutorspet.testutil.LessonBuilder;
import tutorspet.testutil.TypicalIndexes;
import tutorspet.testutil.TypicalLesson;

public class AddLessonCommandParserTest {

    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Lesson expectedLesson = new LessonBuilder(TypicalLesson.LESSON_FRI_8_TO_10).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + " c/1"
                + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                new AddLessonCommand(TypicalIndexes.INDEX_FIRST_ITEM, expectedLesson));

        // multiple class indexes - last class index accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + " c/1" + " c/1"
                        + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                new AddLessonCommand(TypicalIndexes.INDEX_FIRST_ITEM, expectedLesson));

        // multiple days - last day accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1" + CommandTestUtil.DAY_DESC_LESSON_WED_2_TO_4
                        + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                new AddLessonCommand(TypicalIndexes.INDEX_FIRST_ITEM, expectedLesson));

        // multiple start times - last start time accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1"
                        + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_WED_2_TO_4
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                new AddLessonCommand(TypicalIndexes.INDEX_FIRST_ITEM, expectedLesson));

        // multiple end times - last end time accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1" + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_WED_2_TO_4
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                new AddLessonCommand(TypicalIndexes.INDEX_FIRST_ITEM, expectedLesson));

        // multiple number of venues - last venue accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1" + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_WED_2_TO_4
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                new AddLessonCommand(TypicalIndexes.INDEX_FIRST_ITEM, expectedLesson));

        // multiple number of occurrences - last occurrence value accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1" + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_WED_2_TO_4
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                new AddLessonCommand(TypicalIndexes.INDEX_FIRST_ITEM, expectedLesson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);

        // missing class prefix
        CommandParserTestUtil.assertParseFailure(parser, " 1"
                        + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                expectedMessage);

        // missing day prefix
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                        + CommandTestUtil.VALID_DAY_FRI_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                expectedMessage);

        // missing start time prefix
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                        + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VALID_START_TIME_0800_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                expectedMessage);

        // missing end time prefix
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                        + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VALID_END_TIME_1000_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                expectedMessage);

        // missing venue prefix
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                        + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VALID_VENUE_S17_0302_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                expectedMessage);

        // missing number of occurrences prefix
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                        + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VALID_NUMBER_OF_OCCURRENCES_13_LESSON_FRI_8_TO_10,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid class index
        CommandParserTestUtil.assertParseFailure(parser, " c/&"
                + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));

        // invalid class index
        CommandParserTestUtil.assertParseFailure(parser, " s/1"
                        + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                        + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));

        // invalid day
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                + CommandTestUtil.INVALID_DAY_DESC + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10, Day.MESSAGE_CONSTRAINTS);

        // invalid start time
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.INVALID_START_TIME_DESC
                + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10, ParserUtil.MESSAGE_INVALID_TIME);

        // invalid end time
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.INVALID_END_TIME_DESC + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10, ParserUtil.MESSAGE_INVALID_TIME);

        // invalid venue
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.INVALID_VENUE_DESC
                + CommandTestUtil.NUMBER_OF_OCCURRENCES_DESC_LESSON_FRI_8_TO_10, Venue.MESSAGE_CONSTRAINTS);

        // invalid number of occurrences
        CommandParserTestUtil.assertParseFailure(parser, " c/1"
                + CommandTestUtil.DAY_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.START_TIME_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.END_TIME_DESC_LESSON_FRI_8_TO_10 + CommandTestUtil.VENUE_DESC_LESSON_FRI_8_TO_10
                + CommandTestUtil.INVALID_NUMBER_OF_OCCURRENCES_DESC, NumberOfOccurrences.MESSAGE_CONSTRAINTS);
    }
}
