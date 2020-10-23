package tutorspet.logic.parser;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorspet.logic.commands.Command;
import tutorspet.logic.commands.ExitCommand;
import tutorspet.logic.commands.HelpCommand;
import tutorspet.logic.commands.LinkCommand;
import tutorspet.logic.commands.ListCommand;
import tutorspet.logic.commands.RedoCommand;
import tutorspet.logic.commands.ResetCommand;
import tutorspet.logic.commands.UndoCommand;
import tutorspet.logic.commands.UnlinkCommand;
import tutorspet.logic.commands.ViewHistoryCommand;
import tutorspet.logic.commands.attendance.AddAttendanceCommand;
import tutorspet.logic.commands.attendance.DeleteAttendanceCommand;
import tutorspet.logic.commands.attendance.EditAttendanceCommand;
import tutorspet.logic.commands.attendance.FindAttendanceCommand;
import tutorspet.logic.commands.attendance.StatisticsCommand;
import tutorspet.logic.commands.lesson.AddLessonCommand;
import tutorspet.logic.commands.lesson.DeleteLessonCommand;
import tutorspet.logic.commands.lesson.DisplayVenueCommand;
import tutorspet.logic.commands.lesson.EditLessonCommand;
import tutorspet.logic.commands.moduleclass.AddModuleClassCommand;
import tutorspet.logic.commands.moduleclass.ClearModuleClassCommand;
import tutorspet.logic.commands.moduleclass.DeleteModuleClassCommand;
import tutorspet.logic.commands.moduleclass.EditModuleClassCommand;
import tutorspet.logic.commands.moduleclass.FindModuleClassCommand;
import tutorspet.logic.commands.moduleclass.ListModuleClassCommand;
import tutorspet.logic.commands.student.AddStudentCommand;
import tutorspet.logic.commands.student.ClearStudentCommand;
import tutorspet.logic.commands.student.DeleteStudentCommand;
import tutorspet.logic.commands.student.EditStudentCommand;
import tutorspet.logic.commands.student.FindStudentCommand;
import tutorspet.logic.commands.student.ListStudentCommand;
import tutorspet.logic.parser.attendance.AddAttendanceCommandParser;
import tutorspet.logic.parser.attendance.DeleteAttendanceCommandParser;
import tutorspet.logic.parser.attendance.EditAttendanceCommandParser;
import tutorspet.logic.parser.attendance.FindAttendanceCommandParser;
import tutorspet.logic.parser.attendance.StatisticsCommandParser;
import tutorspet.logic.parser.exceptions.ParseException;
import tutorspet.logic.parser.lesson.AddLessonCommandParser;
import tutorspet.logic.parser.lesson.DeleteLessonCommandParser;
import tutorspet.logic.parser.lesson.DisplayVenueCommandParser;
import tutorspet.logic.parser.lesson.EditLessonCommandParser;
import tutorspet.logic.parser.moduleclass.AddModuleClassCommandParser;
import tutorspet.logic.parser.moduleclass.DeleteModuleClassCommandParser;
import tutorspet.logic.parser.moduleclass.EditModuleClassCommandParser;
import tutorspet.logic.parser.moduleclass.FindModuleClassCommandParser;
import tutorspet.logic.parser.student.AddStudentCommandParser;
import tutorspet.logic.parser.student.DeleteStudentCommandParser;
import tutorspet.logic.parser.student.EditStudentCommandParser;
import tutorspet.logic.parser.student.FindStudentCommandParser;
import tutorspet.logic.parser.student.ListStudentCommandParser;

/**
 * Parses user input.
 */
public class TutorsPetParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string.
     * @return the command based on the user input.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommandParser().parse(arguments);

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case ClearStudentCommand.COMMAND_WORD:
            return new ClearStudentCommand();

        case FindStudentCommand.COMMAND_WORD:
            return new FindStudentCommandParser().parse(arguments);

        case AddModuleClassCommand.COMMAND_WORD:
            return new AddModuleClassCommandParser().parse(arguments);

        case ListModuleClassCommand.COMMAND_WORD:
            return new ListModuleClassCommand();

        case EditModuleClassCommand.COMMAND_WORD:
            return new EditModuleClassCommandParser().parse(arguments);

        case DeleteModuleClassCommand.COMMAND_WORD:
            return new DeleteModuleClassCommandParser().parse(arguments);

        case ClearModuleClassCommand.COMMAND_WORD:
            return new ClearModuleClassCommand();

        case FindModuleClassCommand.COMMAND_WORD:
            return new FindModuleClassCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case LinkCommand.COMMAND_WORD:
            return new LinkCommandParser().parse(arguments);

        case UnlinkCommand.COMMAND_WORD:
            return new UnlinkCommandParser().parse(arguments);

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(arguments);

        case EditLessonCommand.COMMAND_WORD:
            return new EditLessonCommandParser().parse(arguments);

        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(arguments);

        case AddAttendanceCommand.COMMAND_WORD:
            return new AddAttendanceCommandParser().parse(arguments);

        case EditAttendanceCommand.COMMAND_WORD:
            return new EditAttendanceCommandParser().parse(arguments);

        case DeleteAttendanceCommand.COMMAND_WORD:
            return new DeleteAttendanceCommandParser().parse(arguments);

        case FindAttendanceCommand.COMMAND_WORD:
            return new FindAttendanceCommandParser().parse(arguments);

        case DisplayVenueCommand.COMMAND_WORD:
            return new DisplayVenueCommandParser().parse(arguments);

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommandParser().parse(arguments);

        case ViewHistoryCommand.COMMAND_WORD:
            return new ViewHistoryCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ResetCommand.COMMAND_WORD:
            return new ResetCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
