package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyTutorsPet;
import seedu.address.model.TutorsPet;
import seedu.address.model.components.name.Name;
import seedu.address.model.components.tag.Tag;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Email;
import seedu.address.model.student.Student;
import seedu.address.model.student.Telegram;

/**
 * Contains utility methods for populating {@code TutorsPet} with sample data.
 */
public class SampleDataUtil {

    private static final List<Student> listOfStudents = getSampleStudents();
    private static final List<ModuleClass> listOfModuleClass = getSampleModuleClasses();

    private static List<Student> getSampleStudents() {
        Student[] students = new Student[] {
            new Student(new Name("Alex Yeoh"), new Telegram("41ex_Yo"), new Email("alexyeoh@example.com"),
                getTagSet("Average")),
            new Student(new Name("Bernice Yu"), new Telegram("b3rnice"), new Email("berniceyu@example.com"),
                getTagSet("Good", "Experienced")),
            new Student(new Name("Charlotte Oliveiro"), new Telegram("C_Ol1ve"), new Email("charlotte@example.com"),
                getTagSet("Struggling")),
            new Student(new Name("David Li"), new Telegram("li_DAvid"), new Email("lidavid@example.com"),
                getTagSet("Weak")),
            new Student(new Name("Irfan Ibrahim"), new Telegram("IIbr4hmm"), new Email("irfan@example.com"),
                getTagSet("Struggling")),
            new Student(new Name("Roy Balakrishnan"), new Telegram("B_Roy"), new Email("royb@example.com"),
                getTagSet("Average"))
        };
        return Arrays.asList(students);
    }

    private static List<ModuleClass> getSampleModuleClasses() {
        Set<UUID> studentsInCs2103TTutorial = new HashSet<>(Arrays.asList(listOfStudents.get(0).getUuid(),
                listOfStudents.get(1).getUuid(), listOfStudents.get(2).getUuid()));
        Set<UUID> studentsInCs2100Lab = new HashSet<>(Arrays.asList(listOfStudents.get(0).getUuid(),
                listOfStudents.get(3).getUuid()));

        ModuleClass[] moduleClasses = new ModuleClass[] {
            new ModuleClass(new Name("CS2103T Tutorial"), studentsInCs2103TTutorial),
            new ModuleClass(new Name("CS2100 Lab"), studentsInCs2100Lab),
            new ModuleClass(new Name("CS2101 Tutorial")),
        };
        return Arrays.asList(moduleClasses);
    }

    public static ReadOnlyTutorsPet getSampleTutorsPet() {
        TutorsPet sampleTp = new TutorsPet();

        for (Student sampleStudent : listOfStudents) {
            sampleTp.addStudent(sampleStudent);
        }

        for (ModuleClass moduleClass : listOfModuleClass) {
            sampleTp.addModuleClass(moduleClass);
        }

        return sampleTp;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
