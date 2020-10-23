package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_5;
import static tutorspet.logic.commands.attendance.DeleteAttendanceCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_WEEK;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorspet.model.attendance.Week.MESSAGE_CONSTRAINTS;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_SECOND_ITEM;

import org.junit.jupiter.api.Test;

import tutorspet.logic.commands.attendance.DeleteAttendanceCommand;

/**
 * As we are only doing white-box testing, out test cases do not cover path variations
 * outside of the DeleteAttendanceCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteAttendanceCommand, and therefore we test only one of them.
 * The path variations for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteAttendanceCommandParserTest {

    private DeleteAttendanceCommandParser parser = new DeleteAttendanceCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAttendanceCommand() {
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1",
                new DeleteAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, VALID_WEEK_1));

        // multiple class indexes -> last class index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1",
                new DeleteAttendanceCommand(INDEX_SECOND_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, VALID_WEEK_1));

        // multiple lesson indexes -> last lesson index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "2" + " "
                + PREFIX_WEEK + "1",
                new DeleteAttendanceCommand(INDEX_FIRST_ITEM, INDEX_SECOND_ITEM, INDEX_FIRST_ITEM, VALID_WEEK_1));

        // multiple student indexes -> last student index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_STUDENT_INDEX + "2",
                new DeleteAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_SECOND_ITEM, VALID_WEEK_1));

        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_WEEK + "5",
                new DeleteAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, VALID_WEEK_5));
    }

    @Test
    public void parse_missingClassIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingLessonIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingStudentIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_WEEK + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingWeekIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidClassIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "a" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidLessonIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "a" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "a" + " "
                + PREFIX_WEEK + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidWeekIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "a",
                String.format(MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_emptyClassIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyLessonIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyStudentIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + " "
                + PREFIX_WEEK + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyWeekIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK,
                String.format(MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidArgs_throwParseException() {
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
