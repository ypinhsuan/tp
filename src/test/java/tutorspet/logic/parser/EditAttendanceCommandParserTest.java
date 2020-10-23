package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.CommandTestUtil.INVALID_PARTICIPATION_SCORE_101;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_33;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_80;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static tutorspet.logic.commands.attendance.EditAttendanceCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_PARTICIPATION_SCORE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_WEEK;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.attendance.EditAttendanceCommand;
import tutorspet.logic.commands.attendance.EditAttendanceCommand.EditAttendanceDescriptor;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.Week;
import tutorspet.testutil.EditAttendanceDescriptorBuilder;

public class EditAttendanceCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

    private EditAttendanceCommandParser parser = new EditAttendanceCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no class index specified
        assertParseFailure(parser, " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, MESSAGE_INVALID_FORMAT);

        // no lesson index specified
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, MESSAGE_INVALID_FORMAT);

        // no student index specified
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, MESSAGE_INVALID_FORMAT);

        // no week specified
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " 1 some random string" + " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "0" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "-1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + "k\\" + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid class index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "-1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, MESSAGE_INVALID_FORMAT);

        // invalid lesson index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "0" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, MESSAGE_INVALID_FORMAT);

        // invalid student index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "*" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, MESSAGE_INVALID_FORMAT);

        // invalid week
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_80, Week.MESSAGE_CONSTRAINTS);

        // invalid participation score
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + INVALID_PARTICIPATION_SCORE_101, Attendance.MESSAGE_CONSTRAINTS);
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

        String userInput = " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_33;
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

        String userInput = " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_LESSON_INDEX + "4" + " "
                + PREFIX_STUDENT_INDEX + "10" + " "
                + PREFIX_WEEK + "100" + " "
                + PREFIX_PARTICIPATION_SCORE + "90" + " "
                + " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_WEEK + "1" + " "
                + PREFIX_PARTICIPATION_SCORE + VALID_PARTICIPATION_SCORE_33;
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
