package tutorspet.logic.commands.attendance;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_PARTICIPATION_SCORE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_WEEK;
import static tutorspet.logic.util.ModuleClassUtil.addAttendanceToModuleClass;
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
 * Adds a student's attendance to the specified {@code Lesson} in the student manager.
 */
public class AddAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "add-attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student's attendance to the specified "
            + "lesson in the student manager. "
            + "Note: All indexes and numbers must be positive integers.\n"
            + "Parameters: "
            + PREFIX_CLASS_INDEX + "CLASS_INDEX "
            + PREFIX_LESSON_INDEX + "LESSON_INDEX "
            + PREFIX_STUDENT_INDEX + "STUDENT_INDEX "
            + PREFIX_WEEK + "WEEK_NUMBER"
            + PREFIX_PARTICIPATION_SCORE + "PARTICIPATION_SCORE (must be an integer between 0 and 100)";

    public static final String MESSAGE_SUCCESS = "New attendance added:\n"
            + "%1$s attended %2$s %3$s in week $4$s\n with participation score of %5$s.";

    private final Index moduleClassIndex;
    private final Index lessonIndex;
    private final Index studentIndex;
    private final Week week;
    private final Attendance toAdd;

    /**
     * Creates an AddAttendanceCommand to add the specified {@code Attendance}.
     */
    public AddAttendanceCommand(Index moduleClassIndex, Index lessonIndex, Index studentIndex,
                                Week week, Attendance toAdd) {
        requireAllNonNull(moduleClassIndex, lessonIndex, studentIndex, week, toAdd);

        this.moduleClassIndex = moduleClassIndex;
        this.lessonIndex = lessonIndex;
        this.studentIndex = studentIndex;
        this.week = week;
        this.toAdd = toAdd;
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

        ModuleClass modifiedModuleClass =
                addAttendanceToModuleClass(targetModuleClass, lessonIndex, week, targetStudent, toAdd);
        model.setModuleClass(targetModuleClass, modifiedModuleClass);

        String message = String.format(MESSAGE_SUCCESS,
                targetStudent.getName(), modifiedModuleClass.getName(), targetLesson, week, toAdd);
        model.commit(message);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAttendanceCommand // instanceof handles nulls
                && moduleClassIndex.equals(((AddAttendanceCommand) other).moduleClassIndex)
                && lessonIndex.equals(((AddAttendanceCommand) other).lessonIndex)
                && studentIndex.equals(((AddAttendanceCommand) other).studentIndex)
                && week.equals(((AddAttendanceCommand) other).week)
                && toAdd.equals(((AddAttendanceCommand) other).toAdd));
    }
}
