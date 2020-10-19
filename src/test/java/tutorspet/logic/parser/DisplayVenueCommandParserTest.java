package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tutorspet.logic.commands.DisplayVenueCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import tutorspet.logic.commands.DisplayVenueCommand;

public class DisplayVenueCommandParserTest {

    private DisplayVenueCommandParser parser = new DisplayVenueCommandParser();

    @Test
    public void parse_allFieldsPresentSuccess() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " c/1 l/1 ",
                new DisplayVenueCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM));

        // multiple class indexes - last class index accepted
        assertParseSuccess(parser, " c/2 l/1 c/1",
                new DisplayVenueCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM));

        // multiple lesson indexes - last class index accepted
        assertParseSuccess(parser, " c/1 l/2 l/1",
                new DisplayVenueCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // missing class prefix
        assertParseFailure(parser, " 1 l/1", expectedMessage);

        // missing lesson prefix
        assertParseFailure(parser, " c/1 1", expectedMessage);

        // missing class index
        assertParseFailure(parser, " c/ l/1", expectedMessage);

        // missing lesson index
        assertParseFailure(parser, " c/1 l/", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // invalid class index
        assertParseFailure(parser, " c/& l/1", expectedMessage);

        // invalid lesson index
        assertParseFailure(parser, " c/1 l/&", expectedMessage);
    }
}
