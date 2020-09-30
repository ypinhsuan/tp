package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindModuleClassCommand;
import seedu.address.model.components.name.NameContainsKeywordsPredicate;

public class FindModuleClassCommandParserTest {

    private FindModuleClassCommandParser parser = new FindModuleClassCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindModuleClassCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindModuleClassCommand() {
        FindModuleClassCommand expectedFindModuleClassCommand =
                new FindModuleClassCommand(new NameContainsKeywordsPredicate<>(Arrays.asList("CS1101S", "Tutorial")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "CS1101S Tutorial", expectedFindModuleClassCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS1101S \n \t Tutorial  \t", expectedFindModuleClassCommand);
    }
}
