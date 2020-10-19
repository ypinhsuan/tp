package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import tutorspet.logic.commands.AddStudentCommand;
import tutorspet.logic.commands.CommandTestUtil;
import tutorspet.model.components.name.Name;
import tutorspet.model.components.tag.Tag;
import tutorspet.model.student.Email;
import tutorspet.model.student.Student;
import tutorspet.model.student.Telegram;
import tutorspet.testutil.StudentBuilder;
import tutorspet.testutil.TypicalStudent;

public class AddStudentCommandParserTest {

    private AddStudentCommandParser parser = new AddStudentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(TypicalStudent.BOB)
                .withTags(CommandTestUtil.VALID_TAG_EXPERIENCED).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.TAG_DESC_EXPERIENCED, new AddStudentCommand(expectedStudent));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY
                + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.TAG_DESC_EXPERIENCED, new AddStudentCommand(expectedStudent));

        // multiple telegram - last telegram accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.TELEGRAM_DESC_AMY + CommandTestUtil.TELEGRAM_DESC_BOB
                + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.TAG_DESC_EXPERIENCED,
                new AddStudentCommand(expectedStudent));

        // multiple emails - last email accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.TAG_DESC_EXPERIENCED, new AddStudentCommand(expectedStudent));

        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(TypicalStudent.BOB)
                .withTags(CommandTestUtil.VALID_TAG_EXPERIENCED, CommandTestUtil.VALID_TAG_AVERAGE).build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.TAG_DESC_AVERAGE + CommandTestUtil.TAG_DESC_EXPERIENCED,
                new AddStudentCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(TypicalStudent.AMY).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY
                        + CommandTestUtil.TELEGRAM_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY,
                new AddStudentCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB
                        + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB,
                expectedMessage);

        // missing telegram prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                        + CommandTestUtil.VALID_TELEGRAM_BOB + CommandTestUtil.EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                        + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB
                        + CommandTestUtil.VALID_TELEGRAM_BOB + CommandTestUtil.VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC
                + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.TAG_DESC_AVERAGE + CommandTestUtil.TAG_DESC_EXPERIENCED, Name.MESSAGE_CONSTRAINTS);

        // invalid telegram
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.INVALID_TELEGRAM_DESC + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.TAG_DESC_AVERAGE
                + CommandTestUtil.TAG_DESC_EXPERIENCED, Telegram.MESSAGE_CONSTRAINTS);

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.INVALID_EMAIL_DESC
                + CommandTestUtil.TAG_DESC_AVERAGE + CommandTestUtil.TAG_DESC_EXPERIENCED, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_TAG_EXPERIENCED, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                        + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.TELEGRAM_DESC_BOB
                        + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.TAG_DESC_AVERAGE + CommandTestUtil.TAG_DESC_EXPERIENCED,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC
                        + CommandTestUtil.TELEGRAM_DESC_BOB + CommandTestUtil.INVALID_EMAIL_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }
}
