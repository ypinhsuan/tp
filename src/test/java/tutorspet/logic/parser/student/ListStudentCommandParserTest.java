package tutorspet.logic.parser.student;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static tutorspet.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tutorspet.logic.commands.student.ListStudentInClassCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_SECOND_ITEM;

import org.junit.jupiter.api.Test;

import tutorspet.logic.commands.student.ListStudentCommand;
import tutorspet.logic.commands.student.ListStudentInClassCommand;

public class ListStudentCommandParserTest {

    private ListStudentCommandParser parser = new ListStudentCommandParser();

    @Test
    public void parse_validArgs_returnsLinkCommand() {
        // no args
        assertParseSuccess(parser, " ",
                new ListStudentCommand());

        // class specified
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "2",
                new ListStudentInClassCommand(INDEX_SECOND_ITEM));

        // multiple class indexes specified -> last class index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_CLASS_INDEX + "1",
                new ListStudentInClassCommand(INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_withPreamble_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_CLASS_INDEX + "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        assertParseFailure(parser, PREAMBLE_WHITESPACE + "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyClassIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_CLASS_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
