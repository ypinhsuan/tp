package seedu.address.model.util;

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
 * Contains utility methods for modifying {@code Lessons} in {@code ModuleClass}.
 */
public class ModuleClassModificationUtil {

    /**
     * Adds all lessons in the target module class to the new module class.
     * The {@code lessonToAdd} is added to the new module class.
     *
     * @throws CommandException if the {@code lessonToAdd} already exists.
     */
    public static ModuleClass createModifiedModuleClassWithAddedLesson(
            ModuleClass moduleClassToAddTo, Lesson lessonToAdd)
            throws CommandException {
        assert moduleClassToAddTo != null;
        assert lessonToAdd != null;

        if (moduleClassToAddTo.hasLesson(lessonToAdd)) {
            throw new CommandException(MESSAGE_EXISTING_LESSON);
        }

        Name moduleClassName = moduleClassToAddTo.getName();
        Set<UUID> studentsIds = moduleClassToAddTo.getStudentUuids();
        List<Lesson> lessons = new ArrayList<>(moduleClassToAddTo.getLessons());
        lessons.add(lessonToAdd);
        return new ModuleClass(moduleClassName, studentsIds, lessons);
    }

    /**
     * Adds all lessons in the target module class to the new module class.
     * The {@code editedLesson} is added in place of the {@code lessonToEdit}.
     *
     * @throws CommandException if the {@code editedLesson} already exists.
     */
    public static ModuleClass createModifiedModuleClassWithEditedLesson(
            ModuleClass targetModuleClass, Lesson lessonToEdit, Lesson editedLesson) throws CommandException {
        assert targetModuleClass != null;
        assert lessonToEdit != null;
        assert editedLesson != null;
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
     * Adds all lessons in the target module class to the new module class.
     * The {@code lessonToDelete} is deleted from the new module class.
     *
     * @throws CommandException if the {@code editedLesson} already exists.
     */
    public static ModuleClass createModifiedModuleClassWithDeletedLesson(
            ModuleClass targetModuleClass, Lesson lessonToDelete) {
        assert targetModuleClass != null;
        assert lessonToDelete != null;
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
     * Updates {@code lessons} in {@code moduleClass} and creates a modified {@code moduleClass} object.
     */
    public static ModuleClass createModifiedModuleClassWithModifiedLesson(
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
