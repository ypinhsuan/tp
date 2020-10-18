package seedu.address.logic.util;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.AddLessonCommand.MESSAGE_EXISTING_LESSON;
import static seedu.address.logic.commands.EditLessonCommand.MESSAGE_DUPLICATE_LESSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.components.name.Name;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;

/**
 * Contains utility methods for modifying {@code Lesson}s in {@code ModuleClass}.
 */
public class ModuleClassModificationUtil {

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
    public static ModuleClass addEditedLessonToModuleClass(
            ModuleClass targetModuleClass, Lesson lessonToEdit, Lesson editedLesson) throws CommandException {
        requireAllNonNull(targetModuleClass, lessonToEdit, editedLesson, targetModuleClass.hasLesson(lessonToEdit));

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
        requireAllNonNull(targetModuleClass, lessonToDelete, targetModuleClass.hasLesson(lessonToDelete));

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
     * Updates {@code lessons} in {@code moduleClass} and creates a modified {@code moduleClass} object.
     */
    public static ModuleClass addModifiedLessonToModuleClass(
            ModuleClass targetModuleClass, Index lessonToEditIndex, Lesson lessonToUpdate) {
        requireAllNonNull(targetModuleClass, lessonToEditIndex, lessonToUpdate);

        Name moduleClassName = targetModuleClass.getName();
        Set<UUID> studentsIds = targetModuleClass.getStudentUuids();
        List<Lesson> lessons = new ArrayList<>(targetModuleClass.getLessons());
        lessons.set(lessonToEditIndex.getZeroBased(), lessonToUpdate);

        return new ModuleClass(moduleClassName, studentsIds, lessons);
    }
}
