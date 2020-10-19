package tutorspet.logic.parser;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.Messages;
import tutorspet.logic.commands.DeleteLessonCommand;
import tutorspet.testutil.TypicalIndexes;

/**
 * As we are only doing white-box testing, out test cases do not cover path variations
 * outside of the DeleteLessonCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteLessonCommand, and therefore we test only one of them.
 * The path variations for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteLessonCommandParserTest {

    private DeleteLessonCommandParser parser = new DeleteLessonCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteLessonCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, " c/1 l/1",
                new DeleteLessonCommand(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_FIRST_ITEM));

        // multiple class indexes -> last class index accepted
        CommandParserTestUtil.assertParseSuccess(parser, " c/1 l/1 c/2",
                new DeleteLessonCommand(TypicalIndexes.INDEX_SECOND_ITEM, TypicalIndexes.INDEX_FIRST_ITEM));

        // multiple lesson indexes -> last lesson index accepted
        CommandParserTestUtil.assertParseSuccess(parser, " l/1 c/1 l/2",
                new DeleteLessonCommand(TypicalIndexes.INDEX_FIRST_ITEM, TypicalIndexes.INDEX_SECOND_ITEM));
    }

    @Test
    public void parse_missingClassIndex_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " l/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingLessonIndex_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " c/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidClassIndex_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " c/a l/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidLessonIndex_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/a",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyClassIndex_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " c/ l/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyLessonIndex_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " c/1 l/",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " a",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }
}
