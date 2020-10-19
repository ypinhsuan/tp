package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_33;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static seedu.address.logic.commands.EditAttendanceCommand.EditAttendanceDescriptor;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAttendanceCommand;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Week;
import seedu.address.testutil.EditAttendanceDescriptorBuilder;

public class EditAttendanceCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAttendanceCommand.MESSAGE_USAGE);

    private EditAttendanceCommandParser parser = new EditAttendanceCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no class index specified
        assertParseFailure(parser, " l/1 s/1 w/1 p/70", MESSAGE_INVALID_FORMAT);

        // no lesson index specified
        assertParseFailure(parser, " c/1 s/1 w/1 p/70", MESSAGE_INVALID_FORMAT);

        // no student index specified
        assertParseFailure(parser, " c/1 l/1 w/1 p/70", MESSAGE_INVALID_FORMAT);

        // no week specified
        assertParseFailure(parser, " c/1 l/1 s/1 p/70", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " 1 some random string c/1 l/0 s/1 w/1 p/70", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, " c/-1 l/1 s/1 k/1 p/70", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid class index
        assertParseFailure(parser, " c/-1 l/1 s/1 w/1 p/70", MESSAGE_INVALID_FORMAT);

        // invalid lesson index
        assertParseFailure(parser, " c/1 l/0 s/1 w/1 p/70", MESSAGE_INVALID_FORMAT);

        // invalid student index
        assertParseFailure(parser, " c/1 l/1 s/* w/1 p/70", MESSAGE_INVALID_FORMAT);

        // invalid week
        assertParseFailure(parser, " c/1 l/1 s/1 w/ p/70", Week.MESSAGE_CONSTRAINTS);

        // invalid participation score
        assertParseFailure(parser, " c/1 l/1 s/1 w/1 p/1000", Attendance.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week week = VALID_WEEK_1;
        int participationScore = VALID_PARTICIPATION_SCORE_33;
        EditAttendanceDescriptor descriptor =
                new EditAttendanceDescriptorBuilder().withParticipationScore(participationScore).build();
        EditAttendanceCommand expectedCommand =
                new EditAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, week, descriptor);

        String userInput = " c/1 l/1 s/1 w/1 p/33";
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week week = VALID_WEEK_1;
        int participationScore = VALID_PARTICIPATION_SCORE_33;
        EditAttendanceDescriptor descriptor =
                new EditAttendanceDescriptorBuilder().withParticipationScore(participationScore).build();
        EditAttendanceCommand expectedCommand =
                new EditAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, week, descriptor);

        String userInput = " c/2 l/4 s/10 w/100 p/90 c/1 l/1 s/1 w/1 p/33";
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
