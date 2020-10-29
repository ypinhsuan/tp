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
import static tutorspet.logic.util.ModuleClassUtil.editAttendanceInModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.getAttendanceFromModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.getLessonFromModuleClass;
import static tutorspet.model.Model.PREDICATE_SHOW_ALL_MODULE_CLASS;

import java.util.List;
import java.util.Optional;

import tutorspet.commons.core.index.Index;
import tutorspet.commons.util.CollectionUtil;
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

    public static final String MESSAGE_SUCCESS = "Edited attendance:\n"
            + "%1$s attended %2$s %3$s in week %4$s\nwith participation score of %5$s.";
    public static final String MESSAGE_COMMIT = "Edited attendance: %1$s (%2$s %3$s in week %4$s).";

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

        if (studentIndex.getOneBased() > lastShownStudentList.size()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        if (moduleClassIndex.getOneBased() > lastShownModuleClassList.size()) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
        }

        Student targetStudent = lastShownStudentList.get(studentIndex.getZeroBased());
        ModuleClass targetModuleClass = lastShownModuleClassList.get(moduleClassIndex.getZeroBased());
        Lesson targetLesson = getLessonFromModuleClass(targetModuleClass, lessonIndex);

        Attendance attendanceToEdit = getAttendanceFromModuleClass(targetModuleClass, lessonIndex, week, targetStudent);
        Attendance editedAttendance = createEditedAttendance(attendanceToEdit, editAttendanceDescriptor);
        ModuleClass modifiedModuleClass =
                editAttendanceInModuleClass(targetModuleClass, lessonIndex, week, targetStudent, editedAttendance);

        model.setModuleClass(targetModuleClass, modifiedModuleClass);
        model.updateFilteredModuleClassList(PREDICATE_SHOW_ALL_MODULE_CLASS);

        model.commit(String.format(MESSAGE_COMMIT, targetStudent.getName(), modifiedModuleClass.getName(),
                targetLesson.printLesson(), week));
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetStudent.getName(), modifiedModuleClass.getName(),
                targetLesson.printLesson(), week, editedAttendance));
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
