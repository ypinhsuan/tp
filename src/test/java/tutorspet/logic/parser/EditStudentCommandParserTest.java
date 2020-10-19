package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.parser.CliSyntax.PREFIX_TAG;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.CommandTestUtil;
import tutorspet.logic.commands.EditStudentCommand;
import tutorspet.logic.commands.EditStudentCommand.EditStudentDescriptor;
import tutorspet.model.components.name.Name;
import tutorspet.model.components.tag.Tag;
import tutorspet.model.student.Email;
import tutorspet.model.student.Telegram;
import tutorspet.testutil.EditStudentDescriptorBuilder;
import tutorspet.testutil.TypicalIndexes;

public class EditStudentCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE);

    private EditStudentCommandParser parser = new EditStudentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditStudentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string",
                MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1"
                + CommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser, "1"
                + CommandTestUtil.INVALID_TELEGRAM_DESC, Telegram.MESSAGE_CONSTRAINTS); // invalid telegram
        CommandParserTestUtil.assertParseFailure(parser, "1"
                + CommandTestUtil.INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        CommandParserTestUtil.assertParseFailure(parser, "1"
                + CommandTestUtil.INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid telegram followed by valid email
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_TELEGRAM_DESC
                + CommandTestUtil.EMAIL_DESC_AMY, Telegram.MESSAGE_CONSTRAINTS);

        // valid telegram followed by invalid telegram. The test case for invalid telegram followed by valid telegram
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.TELEGRAM_DESC_BOB
                + CommandTestUtil.INVALID_TELEGRAM_DESC, Telegram.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Student} being edited,
        // parsing it together with a valid tag results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.TAG_DESC_EXPERIENCED
                + CommandTestUtil.TAG_DESC_AVERAGE + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.TAG_DESC_EXPERIENCED
                + TAG_EMPTY + CommandTestUtil.TAG_DESC_AVERAGE, Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_EMPTY
                + CommandTestUtil.TAG_DESC_EXPERIENCED + CommandTestUtil.TAG_DESC_AVERAGE, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC
                + CommandTestUtil.INVALID_EMAIL_DESC + CommandTestUtil.VALID_TELEGRAM_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_ITEM;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.TELEGRAM_DESC_BOB
                + CommandTestUtil.TAG_DESC_AVERAGE
                + CommandTestUtil.EMAIL_DESC_AMY
                + CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.TAG_DESC_EXPERIENCED;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_AMY)
                .withTelegram(CommandTestUtil.VALID_TELEGRAM_BOB).withEmail(CommandTestUtil.VALID_EMAIL_AMY)
                .withTags(CommandTestUtil.VALID_TAG_AVERAGE, CommandTestUtil.VALID_TAG_EXPERIENCED).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.TELEGRAM_DESC_BOB
                + CommandTestUtil.EMAIL_DESC_AMY;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withTelegram(CommandTestUtil.VALID_TELEGRAM_BOB)
                .withEmail(CommandTestUtil.VALID_EMAIL_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_AMY;
        EditStudentCommand.EditStudentDescriptor descriptor =
                new EditStudentDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // telegram
        userInput = targetIndex.getOneBased() + CommandTestUtil.TELEGRAM_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withTelegram(CommandTestUtil.VALID_TELEGRAM_AMY).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + CommandTestUtil.EMAIL_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withEmail(CommandTestUtil.VALID_EMAIL_AMY).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + CommandTestUtil.TAG_DESC_EXPERIENCED;
        descriptor = new EditStudentDescriptorBuilder().withTags(CommandTestUtil.VALID_TAG_EXPERIENCED).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased()
                + CommandTestUtil.TELEGRAM_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY
                + CommandTestUtil.TAG_DESC_EXPERIENCED + CommandTestUtil.TELEGRAM_DESC_AMY
                + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.TAG_DESC_EXPERIENCED
                + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.TAG_DESC_AVERAGE;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withTelegram(CommandTestUtil.VALID_TELEGRAM_BOB)
                .withEmail(CommandTestUtil.VALID_EMAIL_BOB)
                .withTags(CommandTestUtil.VALID_TAG_EXPERIENCED, CommandTestUtil.VALID_TAG_AVERAGE)
                .build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = TypicalIndexes.INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.INVALID_TELEGRAM_DESC
                + CommandTestUtil.TELEGRAM_DESC_BOB;
        EditStudentCommand.EditStudentDescriptor descriptor =
                new EditStudentDescriptorBuilder().withTelegram(CommandTestUtil.VALID_TELEGRAM_BOB).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_TELEGRAM_DESC
                + CommandTestUtil.TELEGRAM_DESC_BOB;
        descriptor = new EditStudentDescriptorBuilder().withTelegram(CommandTestUtil.VALID_TELEGRAM_BOB)
                .withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withTags().build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
