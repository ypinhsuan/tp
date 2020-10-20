package tutorspet.logic.util;

import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;
import static tutorspet.logic.commands.AddLessonCommand.MESSAGE_EXISTING_LESSON;
import static tutorspet.logic.commands.EditLessonCommand.MESSAGE_DUPLICATE_LESSON;
import static tutorspet.logic.util.LessonUtil.addAttendanceToLesson;
import static tutorspet.logic.util.LessonUtil.deleteAttendanceFromLesson;
import static tutorspet.logic.util.LessonUtil.editAttendanceInLesson;
import static tutorspet.logic.util.LessonUtil.getAttendanceFromLesson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import tutorspet.commons.core.Messages;
import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.Week;
import tutorspet.model.components.name.Name;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;
import tutorspet.model.student.Student;

/**
 * Contains utility methods for modifying {@code Lesson}s and {@code Attendance}s in {@code ModuleClass}.
 */
public class ModuleClassUtil {

    // lesson-related methods

    /**
     * Adds {@code lessonToAdd} to {@code moduleClassToAddTo}.
     * All existing {@code Lesson}s in {@code moduleClassToAddTo} are copied to the new {@code ModuleClass}.
     *
     * @throws CommandException if the {@code lessonToAdd} already exists.
     */
    public static ModuleClass addLessonToModuleClass(
            ModuleClass targetModuleClass, Lesson lessonToAdd)
            throws CommandException {
        requireAllNonNull(targetModuleClass, lessonToAdd);

        if (targetModuleClass.hasLesson(lessonToAdd)) {
            throw new CommandException(MESSAGE_EXISTING_LESSON);
        }

        Name moduleClassName = targetModuleClass.getName();
        Set<UUID> studentsIds = targetModuleClass.getStudentUuids();
        List<Lesson> lessons = new ArrayList<>(targetModuleClass.getLessons());
        lessons.add(lessonToAdd);
        return new ModuleClass(moduleClassName, studentsIds, lessons);
    }

    /**
     * Replaces the {@code lessonToEdit} with {@code editedLesson} in {@code targetModuleClass}.
     * All other existing {@code Lesson}s in {@code targetModuleClass} are copied to the new {@code ModuleClass}.
     *
     * @throws CommandException if the {@code editedLesson} already exists.
     */
    public static ModuleClass editLessonInModuleClass(
            ModuleClass targetModuleClass, Lesson lessonToEdit, Lesson editedLesson) throws CommandException {
        requireAllNonNull(targetModuleClass, lessonToEdit, editedLesson);

        assert targetModuleClass.hasLesson(lessonToEdit);

        if (!lessonToEdit.isSameLesson(editedLesson) && targetModuleClass.hasLesson(editedLesson)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        Name moduleClassName = targetModuleClass.getName();
        Set<UUID> studentsIds = new HashSet<>(targetModuleClass.getStudentUuids());
        List<Lesson> listOfLessons = targetModuleClass.getLessons();
        List<Lesson> editedListOfLessons = new ArrayList<>();
        for (Lesson lesson : listOfLessons) {
            if (lesson.equals(lessonToEdit)) {
                editedListOfLessons.add(editedLesson);
            } else {
                editedListOfLessons.add(lesson);
            }
        }

        assert listOfLessons.size() == editedListOfLessons.size();

        return new ModuleClass(moduleClassName, studentsIds, editedListOfLessons);
    }

    /**
     * Removes the {@code lessonToDelete} from the {@code targetModuleClass}.
     * All other existing {@code Lesson}s in the {@code targetModuleClass} are copied to the new {@code ModuleClass}.
     */
    public static ModuleClass deleteLessonFromModuleClass(
            ModuleClass targetModuleClass, Lesson lessonToDelete) {
        requireAllNonNull(targetModuleClass, lessonToDelete);

        assert targetModuleClass.hasLesson(lessonToDelete);

        Name moduleClassName = targetModuleClass.getName();
        Set<UUID> studentsIds = new HashSet<>(targetModuleClass.getStudentUuids());
        List<Lesson> listOfLessons = targetModuleClass.getLessons();
        List<Lesson> editedListOfLessons = new ArrayList<>();
        for (Lesson lesson : listOfLessons) {
            if (!lesson.equals(lessonToDelete)) {
                editedListOfLessons.add(lesson);
            }
        }

        assert listOfLessons.size() - 1 == editedListOfLessons.size();

        return new ModuleClass(moduleClassName, studentsIds, editedListOfLessons);
    }

    /**
     * Returns the {@code Lesson} at the {@code lessonIndex} of the {@code targetModuleClass}.
     *
     * @throws CommandException if the {@code lessonIndex} is invalid.
     */
    public static Lesson getLessonFromModuleClass(ModuleClass targetModuleClass, Index lessonIndex)
            throws CommandException {
        requireAllNonNull(targetModuleClass, lessonIndex);

        if (lessonIndex.getZeroBased() >= targetModuleClass.getLessons().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        return targetModuleClass.getLessons().get(lessonIndex.getZeroBased());
    }

    // attendance-related methods

    /**
     * Returns a {@code ModuleClass} where the {@code attendanceToAdd} has been added to the {@code Lesson} at
     * the {@code lessonIndex} in the {@code targetModuleClass}.
     *
     * @throws CommandException if any of the following violations are found:
     * - the {@code targetStudent} is not part of the {@code targetModuleClass}<br/>
     * - the {@code lessonIndex} is invalid<br/>
     * - the {@code targetWeek} does not exist in the {@code Lesson} as given by the {@code lessonIndex}<br/>
     * - there is an existing {@code Attendance} for the {@code targetStudent} in the {@code targetWeek}<br/>
     */
    public static ModuleClass addAttendanceToModuleClass(ModuleClass targetModuleClass, Index lessonIndex,
                                                         Week targetWeek, Student targetStudent,
                                                         Attendance attendanceToAdd)
            throws CommandException {
        requireAllNonNull(targetModuleClass, lessonIndex, targetWeek, targetStudent, attendanceToAdd);

        if (!targetModuleClass.hasStudentUuid(targetStudent.getUuid())) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS);
        }

        if (lessonIndex.getOneBased() > targetModuleClass.getLessons().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson targetLesson = targetModuleClass.getLessons().get(lessonIndex.getZeroBased());

        Lesson modifiedLesson = addAttendanceToLesson(targetLesson, targetStudent, targetWeek, attendanceToAdd);

        return updateLessonInModuleClass(targetModuleClass, lessonIndex, modifiedLesson);
    }

    /**
     * Returns a {@code ModuleClass} where the {@code attendanceToSet} has replaced the existing
     * {@code Attendance} for the {@code targetStudent} in the {@code targetWeek} in the {@code Lesson} at
     * the {@code lessonIndex} in the {@code targetModuleClass}.
     *
     * @throws CommandException if any of the following violations are found:
     * - the {@code targetStudent} is not part of the {@code targetModuleClass}<br/>
     * - the {@code lessonIndex} is invalid<br/>
     * - the {@code targetWeek} does not exist in the {@code Lesson} as given by the {@code lessonIndex}<br/>
     * - the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} does not exist<br/>
     */
    public static ModuleClass editAttendanceInModuleClass(ModuleClass targetModuleClass, Index lessonIndex,
                                                          Week targetWeek, Student targetStudent,
                                                          Attendance attendanceToSet)
        throws CommandException {
        requireAllNonNull(targetModuleClass, lessonIndex, targetWeek, targetStudent, attendanceToSet);

        if (!targetModuleClass.hasStudentUuid(targetStudent.getUuid())) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS);
        }

        if (lessonIndex.getOneBased() > targetModuleClass.getLessons().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson targetLesson = targetModuleClass.getLessons().get(lessonIndex.getZeroBased());

        Lesson modifiedLesson = editAttendanceInLesson(targetLesson, targetStudent, targetWeek, attendanceToSet);

        return updateLessonInModuleClass(targetModuleClass, lessonIndex, modifiedLesson);
    }

    /**
     * Returns a {@code ModuleClass} where the {@code Attendance} for the {@code targetStudent} in the
     * {@code targetWeek} in the {@code Lesson} at the {@code lessonIndex} in {@code targetModuleClass}
     * has been removed.
     *
     * @throws CommandException if any of the following violations are found:
     * - the {@code targetStudent} is not part of the {@code targetModuleClass}<br/>
     * - the {@code lessonIndex} is invalid<br/>
     * - the {@code targetWeek} does not exist in the {@code Lesson} as given by the {@code lessonIndex}<br/>
     * - the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} does not exist<br/>
     */
    public static ModuleClass deleteAttendanceFromModuleClass(ModuleClass targetModuleClass, Index lessonIndex,
                                                              Week week, Student targetStudent)
        throws CommandException {
        requireAllNonNull(targetModuleClass, lessonIndex, week, targetStudent);

        if (!targetModuleClass.hasStudentUuid(targetStudent.getUuid())) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS);
        }

        if (lessonIndex.getOneBased() > targetModuleClass.getLessons().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson targetLesson = targetModuleClass.getLessons().get(lessonIndex.getZeroBased());

        Lesson modifiedLesson = deleteAttendanceFromLesson(targetLesson, targetStudent, week);

        return updateLessonInModuleClass(targetModuleClass, lessonIndex, modifiedLesson);
    }

    /**
     * Returns the {@code Attendance} for the {@code targetStudent} in the {@code targetWeek} in the
     * {@code Lesson} at the {@code lessonIndex}.
     *
     * @throws CommandException if any of the following violations are found:
     * - the {@code targetStudent} is not part of the {@code targetModuleClass}<br/>
     * - the {@code lessonIndex} is invalid<br/>
     * - the {@code targetWeek} does not exist in the {@code Lesson} as given by the {@code lessonIndex}<br/>
     * - the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} does not exist<br/>
     */
    public static Attendance getAttendanceFromModuleClass(ModuleClass targetModuleClass, Index lessonIndex,
                                                          Week targetWeek, Student targetStudent)
            throws CommandException {
        requireAllNonNull(targetModuleClass, lessonIndex, targetWeek, targetStudent);

        if (!targetModuleClass.hasStudentUuid(targetStudent.getUuid())) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS);
        }

        if (lessonIndex.getOneBased() > targetModuleClass.getLessons().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson targetLesson = targetModuleClass.getLessons().get(lessonIndex.getZeroBased());

        return getAttendanceFromLesson(targetLesson, targetStudent, targetWeek);
    }

    // private methods

    private static ModuleClass updateLessonInModuleClass(
            ModuleClass targetModuleClass, Index lessonToEditIndex, Lesson lessonToUpdate) {
        assert targetModuleClass != null;
        assert lessonToEditIndex != null;
        assert lessonToUpdate != null;

        Name moduleClassName = targetModuleClass.getName();
        Set<UUID> studentsIds = targetModuleClass.getStudentUuids();
        List<Lesson> lessons = new ArrayList<>(targetModuleClass.getLessons());
        lessons.set(lessonToEditIndex.getZeroBased(), lessonToUpdate);

        return new ModuleClass(moduleClassName, studentsIds, lessons);
    }
}
