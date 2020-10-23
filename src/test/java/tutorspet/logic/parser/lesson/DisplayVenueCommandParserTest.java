package tutorspet.logic.parser.lesson;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tutorspet.logic.commands.lesson.DisplayVenueCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorspet.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import tutorspet.logic.commands.lesson.DisplayVenueCommand;

public class DisplayVenueCommandParserTest {

    private DisplayVenueCommandParser parser = new DisplayVenueCommandParser();

    @Test
    public void parse_allFieldsPresentSuccess() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "1" + " ",
                new DisplayVenueCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM));

        // multiple class indexes - last class index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "2" + " "
                + PREFIX_LESSON_INDEX + "1" + " "
                + PREFIX_CLASS_INDEX + "1",
                new DisplayVenueCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM));

        // multiple lesson indexes - last lesson index accepted
        assertParseSuccess(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "2" + " "
                + PREFIX_LESSON_INDEX + "1",
                new DisplayVenueCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // missing class prefix
        assertParseFailure(parser, " "
                + "1" + " "
                + PREFIX_LESSON_INDEX + "1", expectedMessage);

        // missing lesson prefix
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + "1", expectedMessage);

        // missing class index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + " "
                + PREFIX_LESSON_INDEX + "1", expectedMessage);

        // missing lesson index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        // invalid class index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "&" + " "
                + PREFIX_LESSON_INDEX + "1", expectedMessage);

        // invalid lesson index
        assertParseFailure(parser, " "
                + PREFIX_CLASS_INDEX + "1" + " "
                + PREFIX_LESSON_INDEX + "&", expectedMessage);
    }
}
