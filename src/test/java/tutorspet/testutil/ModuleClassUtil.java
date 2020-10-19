package tutorspet.testutil;

import static tutorspet.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;

import tutorspet.logic.commands.AddModuleClassCommand;
import tutorspet.logic.commands.EditModuleClassCommand.EditModuleClassDescriptor;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;

/**
 * A utility class for ModuleClass.
 */
public class ModuleClassUtil {

    /**
     * Returns an add class command string for adding the {@code moduleClass}.
     */
    public static String getAddModuleClassCommand(ModuleClass moduleClass) {
        return AddModuleClassCommand.COMMAND_WORD + " " + getModuleDetails(moduleClass);
    }

    /**
     * Returns the part of the command string for the given {@code moduleClass}'s details.
     */
    public static String getModuleDetails(ModuleClass moduleClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + moduleClass.getName().toString());
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditModuleClassDescriptor}'s details.
     */
    public static String getEditModuleClassDescriptorDetails(EditModuleClassDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName));
        return sb.toString();
    }

    /**
     * Returns a new {@code ModuleClass} based on the given {@code moduleClass} but with the specified
     * {@code targetLesson} replaced.
     */
    public static ModuleClass manualReplaceLessonToModuleClass(ModuleClass moduleClass,
                                                                Lesson targetLesson, Lesson modifiedLesson) {
        List<Lesson> lessons = new ArrayList<>(moduleClass.getLessons());

        for (Lesson lesson : lessons) {
            if (lesson.isSameLesson(targetLesson)) {
                lessons.set(lessons.indexOf(lesson), modifiedLesson);
            }
        }

        return new ModuleClass(moduleClass.getName(), moduleClass.getStudentUuids(), lessons);
    }
}
