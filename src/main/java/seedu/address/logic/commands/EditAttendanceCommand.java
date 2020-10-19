package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPATION_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.logic.util.LessonUtil.editAttendanceInLesson;
import static seedu.address.logic.util.ModuleClassUtil.addModifiedLessonToModuleClass;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULE_CLASS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.attendance.AttendanceRecordList;
import seedu.address.model.attendance.Week;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

/**
 * Edits the details of an existing attendance.
 */
public class EditAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "edit-attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a student's attendance identified by the "
            + "index number used in the displayed class list, "
            + "index number in the lesson list, "
            + "index number used in the displayed student list "
            + "and the week number.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Note: All indexes must be positive integers.\n"
            + "Parameters: "
            + PREFIX_CLASS_INDEX + "CLASS_INDEX "
            + PREFIX_LESSON_INDEX + "LESSON_INDEX "
            + PREFIX_STUDENT_INDEX + "STUDENT_INDEX "
            + PREFIX_WEEK + "WEEK_NUMBER "
            + PREFIX_PARTICIPATION_SCORE + "PARTICIPATION_SCORE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASS_INDEX + "1 " + PREFIX_LESSON_INDEX + "1 "
            + PREFIX_STUDENT_INDEX + "1 "
            + PREFIX_WEEK + "2 "
            + PREFIX_PARTICIPATION_SCORE + "80";

    public static final String MESSAGE_EDIT_ATTENDANCE_SUCCESS = "Edited attendance: %1$s attended week %2$s lesson "
            + "with participation score of %3$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index moduleClassIndex;
    private final Index lessonIndex;
    private final Index studentIndex;
    private final Week week;
    private final EditAttendanceDescriptor editAttendanceDescriptor;

    /**
     * @param moduleClassIndex in the filtered class list.
     * @param lessonIndex in the specified class list to be edited.
     * @param studentIndex in the filtered student list.
     * @param week of the attendance to be edited.
     * @param editAttendanceDescriptor details to edit the attendance with.
     */
    public EditAttendanceCommand(Index moduleClassIndex, Index lessonIndex, Index studentIndex, Week week,
                                 EditAttendanceDescriptor editAttendanceDescriptor) {
        requireAllNonNull(moduleClassIndex, lessonIndex, studentIndex, week, editAttendanceDescriptor);

        this.moduleClassIndex = moduleClassIndex;
        this.lessonIndex = lessonIndex;
        this.studentIndex = studentIndex;
        this.week = week;
        this.editAttendanceDescriptor = editAttendanceDescriptor;
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
        AttendanceRecordList targetAttendanceRecordList = targetLesson.getAttendanceRecordList();

        if (!targetAttendanceRecordList.isWeekContained(week)) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEEK);
        }

        AttendanceRecord targetAttendanceRecord = targetAttendanceRecordList.getAttendanceRecord(week);

        if (!targetAttendanceRecord.hasAttendance(targetStudent.getUuid())) {
            throw new CommandException(Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE);
        }

        Attendance attendanceToEdit = targetAttendanceRecord.getAttendance(targetStudent.getUuid());
        Attendance editedAttendance = createEditedAttendance(attendanceToEdit, editAttendanceDescriptor);

        Lesson modifiedLesson = editAttendanceInLesson(targetLesson, targetStudent, week, editedAttendance);
        ModuleClass modifiedModuleClass =
                addModifiedLessonToModuleClass(targetModuleClass, lessonIndex, modifiedLesson);

        model.setModuleClass(targetModuleClass, modifiedModuleClass);
        model.updateFilteredModuleClassList(PREDICATE_SHOW_ALL_MODULE_CLASS);

        String message =
                String.format(MESSAGE_EDIT_ATTENDANCE_SUCCESS, targetStudent.getName(), week, editedAttendance);
        model.commit(message);
        return new CommandResult(message);
    }

    /**
     * Creates and returns an {@code Attendance} with details of the {@code attendanceToEdit} edited with
     * {@editAttendanceDescriptor}.
     */
    private static Attendance createEditedAttendance(
            Attendance attendanceToEdit, EditAttendanceDescriptor editAttendanceDescriptor) {
        assert attendanceToEdit != null;

        int editedParticipationScore =
                editAttendanceDescriptor.getParticipationScore().orElse(attendanceToEdit.getParticipationScore());
        return new Attendance(editedParticipationScore);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAttendanceCommand)) {
            return false;
        }

        // state check
        EditAttendanceCommand e = (EditAttendanceCommand) other;
        return moduleClassIndex.equals(e.moduleClassIndex)
                && lessonIndex.equals(e.lessonIndex)
                && studentIndex.equals(e.studentIndex)
                && week.equals(e.week)
                && editAttendanceDescriptor.equals(e.editAttendanceDescriptor);
    }

    /**
     * Stores the details to edit the attendance with. Each non-empty field will replace the
     * corresponding field value of the attendance.
     */
    public static class EditAttendanceDescriptor {

        private Integer participationScore;

        public EditAttendanceDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAttendanceDescriptor(EditAttendanceDescriptor toCopy) {
            setParticipationScore(toCopy.participationScore);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(participationScore);
        }

        public void setParticipationScore(int participationScore) {
            this.participationScore = participationScore;
        }

        public Optional<Integer> getParticipationScore() {
            return Optional.ofNullable(participationScore);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAttendanceDescriptor)) {
                return false;
            }

            // state check
            EditAttendanceDescriptor e = (EditAttendanceDescriptor) other;

            return getParticipationScore().equals(e.getParticipationScore());
        }
    }
}
