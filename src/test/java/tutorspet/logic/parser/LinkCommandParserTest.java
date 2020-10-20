package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.jupiter.api.Test;

import tutorspet.logic.commands.LinkCommand;

public class LinkCommandParserTest {

    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_validArgs_returnsLinkCommand() {
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "1",
                new LinkCommand(INDEX_SECOND_ITEM, INDEX_FIRST_ITEM));

        // multiple class indexes -> last class index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "3", new LinkCommand(INDEX_THIRD_ITEM, INDEX_FIRST_ITEM));

        // multiple student indexes -> last student accepted
        assertParseSuccess(parser, " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_STUDENT_INDEX + "3", new LinkCommand(INDEX_SECOND_ITEM, INDEX_THIRD_ITEM));
    }

    @Test
    public void parse_missingStudentIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClassIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + "a" + " "
                + PREFIX_CLASS_INDEX + "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidClassIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyStudentIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + " "
                + PREFIX_CLASS_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyClassIndex_throwsParseException() {
        assertParseFailure(parser, " "
                + PREFIX_STUDENT_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }
}
