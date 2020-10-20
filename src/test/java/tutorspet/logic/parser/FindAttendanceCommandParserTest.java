package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.CommandTestUtil.INVALID_WEEK_LOWER_BOUND_DESC;
import static tutorspet.logic.commands.CommandTestUtil.INVALID_WEEK_UPPER_BOUND_DESC;
import static tutorspet.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_VALUE_5;
import static tutorspet.logic.commands.CommandTestUtil.WEEK_DESC_WEEK_VALUE_3;
import static tutorspet.logic.commands.CommandTestUtil.WEEK_DESC_WEEK_VALUE_5;
import static tutorspet.logic.commands.FindAttendanceCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_WEEK;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorspet.model.attendance.Week.MESSAGE_CONSTRAINTS;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.FindAttendanceCommand;
import tutorspet.model.attendance.Week;

public class FindAttendanceCommandParserTest {

    private FindAttendanceCommandParser parser = new FindAttendanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Week expectedWeek = new Week(Index.fromOneBased(VALID_WEEK_VALUE_5));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + WEEK_DESC_WEEK_VALUE_5,
                new FindAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        expectedWeek));

        // multiple class indexes - last class index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5,
                new FindAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        expectedWeek));

        // multiple lesson indexes - last class index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5,
                new FindAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        expectedWeek));

        // multiple student indexes - last student index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5,
                new FindAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        expectedWeek));

        // multiple week value indexes - last week value accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + WEEK_DESC_WEEK_VALUE_3 + WEEK_DESC_WEEK_VALUE_5,
                new FindAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        expectedWeek));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // missing class prefix
        assertParseFailure(parser, " "
                + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5, expectedMessage);

        // missing lesson prefix
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5, expectedMessage);

        // missing student prefix
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + "1"
                + WEEK_DESC_WEEK_VALUE_5, expectedMessage);

        // missing week prefix
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + VALID_WEEK_VALUE_5, expectedMessage);

        // missing class index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5, expectedMessage);

        // missing lesson index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5, expectedMessage);

        // missing student index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX
                + WEEK_DESC_WEEK_VALUE_5, expectedMessage);

        // missing week value
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + PREFIX_WEEK, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // invalid class index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "&" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5, expectedMessage);

        // invalid lesson index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "&" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5, expectedMessage);

        // invalid student index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "&"
                + WEEK_DESC_WEEK_VALUE_5, expectedMessage);

        // invalid week value - lower-bound
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + INVALID_WEEK_LOWER_BOUND_DESC, MESSAGE_CONSTRAINTS);

        // invalid week value - upper-bound
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + INVALID_WEEK_UPPER_BOUND_DESC, MESSAGE_CONSTRAINTS);
    }
}
