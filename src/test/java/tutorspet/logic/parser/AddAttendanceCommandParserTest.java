package tutorspet.logic.parser;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.Messages;
import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.AddAttendanceCommand;
import tutorspet.logic.commands.CommandTestUtil;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.Week;
import tutorspet.testutil.TypicalIndexes;

public class AddAttendanceCommandParserTest {

    private AddAttendanceCommandParser parser = new AddAttendanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Attendance expectedAttendance = new Attendance(CommandTestUtil.VALID_PARTICIPATION_SCORE_80);
        Week expectedWeek = new Week(Index.fromOneBased(CommandTestUtil.VALID_WEEK_VALUE_5));

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + " c/1 l/1 s/1 "
                + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                new AddAttendanceCommand(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_FIRST_ITEM,
                        TypicalIndexes.INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple class indexes - last class index accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/2 l/1 s/1 c/1"
                + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                new AddAttendanceCommand(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_FIRST_ITEM,
                        TypicalIndexes.INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple lesson indexes - last lesson index accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1 l/2 s/1 l/1"
                + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                new AddAttendanceCommand(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_FIRST_ITEM,
                        TypicalIndexes.INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple student indexes - last student index accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1 l/1 s/2 s/1"
                + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                new AddAttendanceCommand(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_FIRST_ITEM,
                        TypicalIndexes.INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple week values - last week value accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1 l/1 s/1"
                + CommandTestUtil.WEEK_DESC_WEEK_VALUE_3 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5,
                new AddAttendanceCommand(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_FIRST_ITEM,
                        TypicalIndexes.INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));

        // multiple participation scores - last week value accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1 l/1 s/1"
                + CommandTestUtil.PARTICIPATION_SCORE_DESC_51 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5,
                new AddAttendanceCommand(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_FIRST_ITEM,
                        TypicalIndexes.INDEX_FIRST_ITEM, expectedWeek, expectedAttendance));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddAttendanceCommand.MESSAGE_USAGE);

        // missing class prefix
        CommandParserTestUtil.assertParseFailure(parser, " 1 l/1 s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // missing lesson prefix
        CommandParserTestUtil.assertParseFailure(parser, " c/1 1 s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // missing student prefix
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // missing week prefix
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/1"
                        + CommandTestUtil.VALID_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // missing participation score prefix
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.VALID_PARTICIPATION_SCORE_80,
                expectedMessage);

        // missing class index
        CommandParserTestUtil.assertParseFailure(parser, " c/ l/1 s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // missing lesson index
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/ s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // missing student index
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // missing week value
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/1"
                        + CliSyntax.PREFIX_WEEK + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // missing participation score
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CliSyntax.PREFIX_PARTICIPATION_SCORE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddAttendanceCommand.MESSAGE_USAGE);

        // invalid class index
        CommandParserTestUtil.assertParseFailure(parser, " c/& l/1 s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // invalid lesson index
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/& s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // invalid student index
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/&"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5 + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                expectedMessage);

        // invalid week value - lower-bound
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/1"
                        + CommandTestUtil.INVALID_WEEK_LOWER_BOUND_DESC + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                Week.MESSAGE_CONSTRAINTS);

        // invalid week value - upper-bound
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/1"
                        + CommandTestUtil.INVALID_WEEK_UPPER_BOUND_DESC + CommandTestUtil.PARTICIPATION_SCORE_DESC_80,
                Week.MESSAGE_CONSTRAINTS);

        // invalid participation score - lower-bound
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5
                        + CommandTestUtil.INVALID_PARTICIPATION_LOWER_BOUND_SCORE_DESC,
                Attendance.MESSAGE_CONSTRAINTS);

        // invalid participation score - upper-bound
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/1 s/1"
                        + CommandTestUtil.WEEK_DESC_WEEK_VALUE_5
                        + CommandTestUtil.INVALID_PARTICIPATION_UPPER_BOUND_SCORE_DESC,
                Attendance.MESSAGE_CONSTRAINTS);
    }
}
