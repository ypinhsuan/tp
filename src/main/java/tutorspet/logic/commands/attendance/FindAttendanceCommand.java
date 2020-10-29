package tutorspet.logic.commands.attendance;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_WEEK;
import static tutorspet.logic.util.ModuleClassUtil.getAttendanceFromModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.getLessonFromModuleClass;

import java.util.List;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.Command;
import tutorspet.logic.commands.CommandResult;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.Model;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.Week;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;
import tutorspet.model.student.Student;

/**
 * Finds a student's attendance for a specified lesson in a specified class on
 * a specified week in the student manager.
 */
public class FindAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "find-attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the attendance of a student in a specified "
            + "lesson on a specified week.\n"
            + "Note: All indexes and numbers must be positive integers.\n"
            + "Parameters: "
            + PREFIX_CLASS_INDEX + "CLASS_INDEX "
            + PREFIX_LESSON_INDEX + "LESSON_INDEX "
            + PREFIX_STUDENT_INDEX + "STUDENT_INDEX "
            + PREFIX_WEEK + "WEEK_NUMBER";

    public static final String MESSAGE_SUCCESS = "Found attendance:\n"
            + "%1$s attended %2$s %3$s in week %4$s\n"
            + "with a participation score of %5$s.";

    private final Index moduleClassIndex;
    private final Index lessonIndex;
    private final Index studentIndex;
    private final Week week;

    /**
     * Creates a FindAttendanceCommand to find a student's attendance for a specified lesson
     * on a specified week in the student manager.
     */
    public FindAttendanceCommand(Index moduleClassIndex, Index lessonIndex, Index studentIndex, Week week) {
        requireAllNonNull(moduleClassIndex, lessonIndex, studentIndex, week);

        this.moduleClassIndex = moduleClassIndex;
        this.lessonIndex = lessonIndex;
        this.studentIndex = studentIndex;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownStudentList = model.getFilteredStudentList();
        List<ModuleClass> lastShownModuleClassList = model.getFilteredModuleClassList();

        if (studentIndex.getOneBased() > lastShownStudentList.size()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        if (moduleClassIndex.getOneBased() > lastShownModuleClassList.size()) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
        }

        Student targetStudent = lastShownStudentList.get(studentIndex.getZeroBased());
        ModuleClass targetModuleClass = lastShownModuleClassList.get(moduleClassIndex.getZeroBased());
        Lesson targetLesson = getLessonFromModuleClass(targetModuleClass, lessonIndex);

        Attendance attendance = getAttendanceFromModuleClass(targetModuleClass, lessonIndex, week, targetStudent);

        String message = String.format(MESSAGE_SUCCESS,
                targetStudent.getName(), targetModuleClass.getName(), targetLesson.printLesson(), week, attendance);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAttendanceCommand // instanceof handles nulls
                && moduleClassIndex.equals(((FindAttendanceCommand) other).moduleClassIndex)
                && lessonIndex.equals(((FindAttendanceCommand) other).lessonIndex)
                && studentIndex.equals(((FindAttendanceCommand) other).studentIndex)
                && week.equals(((FindAttendanceCommand) other).week));
    }
}
