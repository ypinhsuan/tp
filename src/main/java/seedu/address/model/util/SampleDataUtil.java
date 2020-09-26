package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyTutorsPet;
import seedu.address.model.TutorsPet;
import seedu.address.model.components.Name;
import seedu.address.model.student.Email;
import seedu.address.model.student.Student;
import seedu.address.model.student.Telegram;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code TutorsPet} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
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
    }

    public static ReadOnlyTutorsPet getSampleTutorsPet() {
        TutorsPet sampleTp = new TutorsPet();
        for (Student sampleStudent : getSampleStudents()) {
            sampleTp.addStudent(sampleStudent);
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
