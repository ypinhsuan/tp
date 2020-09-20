package seedu.address.testutil;

import seedu.address.model.TutorsPet;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TutorsPet ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TutorsPet tutorsPet;

    public AddressBookBuilder() {
        tutorsPet = new TutorsPet();
    }

    public AddressBookBuilder(TutorsPet tutorsPet) {
        this.tutorsPet = tutorsPet;
    }

    /**
     * Adds a new {@code Person} to the {@code TutorsPet} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        tutorsPet.addPerson(person);
        return this;
    }

    public TutorsPet build() {
        return tutorsPet;
    }
}
