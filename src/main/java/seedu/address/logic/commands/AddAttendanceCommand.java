package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPATION_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.logic.util.LessonModificationUtil.createModifiedLessonWithAddedAttendance;
import static seedu.address.logic.util.ModuleClassModificationUtil.createModifiedModuleClassWithModifiedLesson;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Week;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

/**
 * Adds a student's attendance to the specified {@code Lesson} in the student manager.
 */
public class AddAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "add-attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student's attendance to the specified "
            + "lesson in the student manager. "
            + "Note: All indexes must be positive integers.\n"
            + "Parameters: "
            + PREFIX_CLASS_INDEX + "CLASS_INDEX "
            + PREFIX_LESSON_INDEX + "LESSON_INDEX "
            + PREFIX_STUDENT_INDEX + "STUDENT_INDEX "
            + PREFIX_WEEK + "WEEK_NUMBER (must be a positive integer) "
            + PREFIX_PARTICIPATION_SCORE + "PARTICIPATION_SCORE (must be an integer between 0 and 100)";

    public static final String MESSAGE_SUCCESS = "New attendance added: %1$s attended week %2$s lesson with "
            + "participation score of %3$s";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "Attendance have been recorded previously.";

    private final Index moduleCLassIndex;
    private final Index lessonIndex;
    private final Index studentIndex;
    private final Week week;
    private final Attendance toAdd;

    /**
     * Creates an AddAttendanceCommand to add the specified {@code Attendance}.
     */
    public AddAttendanceCommand(Index moduleCLassIndex, Index lessonIndex, Index studentIndex,
                                Week week, Attendance toAdd) {
        requireAllNonNull(moduleCLassIndex, lessonIndex, studentIndex, week, toAdd);

        this.moduleCLassIndex = moduleCLassIndex;
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

        if (moduleCLassIndex.getZeroBased() >= lastShownModuleClassList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
        }

        Student targetStudent = lastShownStudentList.get(studentIndex.getZeroBased());
        ModuleClass targetModuleClass = lastShownModuleClassList.get(moduleCLassIndex.getZeroBased());

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

        Lesson modifiedLesson = createModifiedLessonWithAddedAttendance(targetLesson, targetStudent, week, toAdd);
        ModuleClass modifiedModuleClass =
                createModifiedModuleClassWithModifiedLesson(targetModuleClass, lessonIndex, modifiedLesson);
        model.setModuleClass(targetModuleClass, modifiedModuleClass);

        String message = String.format(MESSAGE_SUCCESS, targetStudent.getName(), week, toAdd);
        model.commit(message);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAttendanceCommand // instanceof handles nulls
                && moduleCLassIndex.equals(((AddAttendanceCommand) other).moduleCLassIndex)
                && lessonIndex.equals(((AddAttendanceCommand) other).lessonIndex)
                && studentIndex.equals(((AddAttendanceCommand) other).studentIndex)
                && week.equals(((AddAttendanceCommand) other).week)
                && toAdd.equals(((AddAttendanceCommand) other).toAdd));
    }
}
