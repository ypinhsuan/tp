package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static tutorspet.logic.commands.UnlinkCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.jupiter.api.Test;

import tutorspet.logic.commands.UnlinkCommand;

public class UnlinkCommandParserTest {

    private UnlinkCommandParser parser = new UnlinkCommandParser();

    @Test
    public void parse_validArgs_returnsUnlinkCommand() {
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "1",
                new UnlinkCommand(INDEX_SECOND_ITEM, INDEX_FIRST_ITEM));

        // multiple class indexes -> last class index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "3",
                new UnlinkCommand(INDEX_THIRD_ITEM, INDEX_FIRST_ITEM));

        // multiple student indexes -> last student accepted
        assertParseSuccess(parser, " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "3",
                new UnlinkCommand(INDEX_SECOND_ITEM, INDEX_THIRD_ITEM));
    }

    @Test
    public void parse_missingStudentIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClassIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + "a" + " "
                + PREFIX_CLASS_INDEX + "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidClassIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyStudentIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyClassIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, " "
                        + PREAMBLE_NON_EMPTY + " "
                        + PREFIX_STUDENT_INDEX + "1" + " "
                        + PREFIX_CLASS_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
