package tutorspet.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;
import static tutorspet.logic.util.LessonUtil.addAttendanceToLesson;
import static tutorspet.logic.util.ModuleClassUtil.addModifiedLessonToModuleClass;

import java.util.List;

import tutorspet.commons.core.Messages;
import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.logic.parser.CliSyntax;
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
            + CliSyntax.PREFIX_CLASS_INDEX + "CLASS_INDEX "
            + CliSyntax.PREFIX_LESSON_INDEX + "LESSON_INDEX "
            + CliSyntax.PREFIX_STUDENT_INDEX + "STUDENT_INDEX "
            + CliSyntax.PREFIX_WEEK + "WEEK_NUMBER"
            + CliSyntax.PREFIX_PARTICIPATION_SCORE + "PARTICIPATION_SCORE (must be an integer between 0 and 100)";

    public static final String MESSAGE_SUCCESS = "New attendance added: %1$s attended week %2$s lesson with "
            + "participation score of %3$s";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "Attendance have been recorded previously.";

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

        if (studentIndex.getZeroBased() >= lastShownStudentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        if (moduleClassIndex.getZeroBased() >= lastShownModuleClassList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
        }

        Student targetStudent = lastShownStudentList.get(studentIndex.getZeroBased());
        ModuleClass targetModuleClass = lastShownModuleClassList.get(moduleClassIndex.getZeroBased());

        if (!targetModuleClass.hasStudentUuid(targetStudent.getUuid())) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS);
        }

        if (lessonIndex.getZeroBased() >= targetModuleClass.getLessons().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson targetLesson = targetModuleClass.getLessons().get(lessonIndex.getZeroBased());

        if (!targetLesson.getAttendanceRecordList().isWeekContained(week)) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEEK);
        }

        Lesson modifiedLesson = addAttendanceToLesson(targetLesson, targetStudent, week, toAdd);
        ModuleClass modifiedModuleClass =
                addModifiedLessonToModuleClass(targetModuleClass, lessonIndex, modifiedLesson);
        model.setModuleClass(targetModuleClass, modifiedModuleClass);

        String message = String.format(MESSAGE_SUCCESS, targetStudent.getName(), week, toAdd);
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
