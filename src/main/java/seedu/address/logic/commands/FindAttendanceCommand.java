package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.attendance.Week;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

/**
 * Finds a student's attendance for a specified lesson in a specified lesson on
 * a specified week in the student manager.
 */
public class FindAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "find-attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Finds the attendance of a student in a specified "
            + "lesson on a specified week.\n"
            + "Note: All indexes must be positive integers.\n"
            + "Parameters: "
            + PREFIX_CLASS_INDEX + "CLASS_INDEX"
            + PREFIX_LESSON_INDEX + "LESSON_INDEX "
            + PREFIX_STUDENT_INDEX + "STUDENT_INDEX "
            + PREFIX_WEEK + "WEEK_NUMBER (must be a positive integer) ";

    public static final String MESSAGE_SUCCESS = "%1$S attended week %2$s lesson with a participation score of %3$s";

    private final Index moduleCLassIndex;
    private final Index lessonIndex;
    private final Index studentIndex;
    private final Week week;

    /**
     * Creates a FindAttendanceCommand to find a student's attendance for a specified lesson
     * on a specified week in the student manager.
     */
    public FindAttendanceCommand(Index moduleCLassIndex, Index lessonIndex, Index studentIndex, Week week) {
        requireAllNonNull(moduleCLassIndex, lessonIndex, studentIndex, week);

        this.moduleCLassIndex = moduleCLassIndex;
        this.lessonIndex = lessonIndex;
        this.studentIndex = studentIndex;
        this.week = week;
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

        AttendanceRecord attendanceRecord = targetLesson.getAttendanceRecordList().getAttendanceRecord(week);

        if (!attendanceRecord.hasAttendance(targetStudent.getUuid())) {
            throw new CommandException(Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE);
        }

        Attendance attendance = targetLesson.getAttendanceRecordList().getAttendance(targetStudent, week);
        String message = String.format(MESSAGE_SUCCESS, targetStudent.getName(), week, attendance);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAttendanceCommand // instanceof handles nulls
                && moduleCLassIndex.equals(((FindAttendanceCommand) other).moduleCLassIndex)
                && lessonIndex.equals(((FindAttendanceCommand) other).lessonIndex)
                && studentIndex.equals(((FindAttendanceCommand) other).studentIndex)
                && week.equals(((FindAttendanceCommand) other).week));
    }
}
