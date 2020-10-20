package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.AddAttendanceCommand.MESSAGE_USAGE;
import static tutorspet.logic.commands.CommandTestUtil.INVALID_PARTICIPATION_LOWER_BOUND_SCORE_DESC;
import static tutorspet.logic.commands.CommandTestUtil.INVALID_PARTICIPATION_UPPER_BOUND_SCORE_DESC;
import static tutorspet.logic.commands.CommandTestUtil.INVALID_WEEK_LOWER_BOUND_DESC;
import static tutorspet.logic.commands.CommandTestUtil.INVALID_WEEK_UPPER_BOUND_DESC;
import static tutorspet.logic.commands.CommandTestUtil.PARTICIPATION_SCORE_DESC_51;
import static tutorspet.logic.commands.CommandTestUtil.PARTICIPATION_SCORE_DESC_80;
import static tutorspet.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_80;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_VALUE_5;
import static tutorspet.logic.commands.CommandTestUtil.WEEK_DESC_WEEK_VALUE_3;
import static tutorspet.logic.commands.CommandTestUtil.WEEK_DESC_WEEK_VALUE_5;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_PARTICIPATION_SCORE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_WEEK;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.AddAttendanceCommand;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.Week;

public class AddAttendanceCommandParserTest {

    private AddAttendanceCommandParser parser = new AddAttendanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Attendance expectedAttendance = new Attendance(VALID_PARTICIPATION_SCORE_80);
        Week expectedWeek = new Week(Index.fromOneBased(VALID_WEEK_VALUE_5));

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, PREAMBLE_WHITESPACE + " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80,
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple class indexes - last class index accepted
        CommandParserTestUtil.assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80,
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple lesson indexes - last lesson index accepted
        CommandParserTestUtil.assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80,
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple student indexes - last student index accepted
        CommandParserTestUtil.assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80,
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple week values - last week value accepted
        CommandParserTestUtil.assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_3
                + PARTICIPATION_SCORE_DESC_80
                + WEEK_DESC_WEEK_VALUE_5,
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple participation scores - last week value accepted
        CommandParserTestUtil.assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + PARTICIPATION_SCORE_DESC_51
                + PARTICIPATION_SCORE_DESC_80
                + WEEK_DESC_WEEK_VALUE_5,
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_USAGE);

        // missing class prefix
        CommandParserTestUtil.assertParseFailure(parser, " "
                + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // missing lesson prefix
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // missing student prefix
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // missing week prefix
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + VALID_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // missing participation score prefix
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + VALID_PARTICIPATION_SCORE_80, expectedMessage);

        // missing class index
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // missing lesson index
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // missing student index
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // missing week value
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + PREFIX_WEEK
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // missing participation score
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PREFIX_PARTICIPATION_SCORE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // invalid class index
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "&" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // invalid lesson index
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "&" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // invalid student index
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "&"
                + WEEK_DESC_WEEK_VALUE_5
                + PARTICIPATION_SCORE_DESC_80, expectedMessage);

        // invalid week value - lower-bound
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + INVALID_WEEK_LOWER_BOUND_DESC
                + PARTICIPATION_SCORE_DESC_80, Week.MESSAGE_CONSTRAINTS);

        // invalid week value - upper-bound
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + INVALID_WEEK_UPPER_BOUND_DESC + PARTICIPATION_SCORE_DESC_80, Week.MESSAGE_CONSTRAINTS);

        // invalid participation score - lower-bound
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + INVALID_PARTICIPATION_LOWER_BOUND_SCORE_DESC, Attendance.MESSAGE_CONSTRAINTS);

        // invalid participation score - upper-bound
        CommandParserTestUtil.assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1"
                + WEEK_DESC_WEEK_VALUE_5
                + INVALID_PARTICIPATION_UPPER_BOUND_SCORE_DESC, Attendance.MESSAGE_CONSTRAINTS);
    }
}
