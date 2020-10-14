package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2100_LAB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2103T_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2100_LAB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2103T_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddModuleClassCommand;
import seedu.address.model.components.name.Name;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.testutil.ModuleClassBuilder;

public class AddModuleClassCommandParserTest {

    private AddModuleClassCommandParser parser = new AddModuleClassCommandParser();

    @Test
    public void parse_namePresent_success() {
        ModuleClass expectedModuleClass = new ModuleClassBuilder()
                .withName(VALID_NAME_CS2103T_TUTORIAL)
                .withStudentUuids().withLessons().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS2103T_TUTORIAL,
                new AddModuleClassCommand(expectedModuleClass));

        // multiple names - last name accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS2100_LAB + NAME_DESC_CS2103T_TUTORIAL,
                new AddModuleClassCommand(expectedModuleClass));
    }

    @Test
    public void parse_nameMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleClassCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_CS2100_LAB, expectedMessage);
    }

    @Test
    public void parse_invalidName_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
    }
}
