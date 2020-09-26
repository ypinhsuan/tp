package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.components.Name;
import seedu.address.model.student.Email;
import seedu.address.model.student.Student;
import seedu.address.model.student.Telegram;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_UUID = "5d07082f-960a-4a0c-8661-8f1a84cf3f93";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";

    private UUID uuid;
    private Name name;
    private Telegram telegram;
    private Email email;
    private Set<Tag> tags;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        uuid = UUID.fromString(DEFAULT_UUID);
        name = new Name(DEFAULT_NAME);
        telegram = new Telegram(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        uuid = studentToCopy.getUuid();
        name = studentToCopy.getName();
        telegram = studentToCopy.getTelegram();
        email = studentToCopy.getEmail();
        tags = new HashSet<>(studentToCopy.getTags());
    }

    /**
     * Sets the {@code UUID} of the {@code Student} that we are building.
     */
    public StudentBuilder withUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code Student} that we are building.
     */
    public StudentBuilder withTelegram(String telegram) {
        this.telegram = new Telegram(telegram);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Student build() {
        return new Student(uuid, name, telegram, email, tags);
    }

}
