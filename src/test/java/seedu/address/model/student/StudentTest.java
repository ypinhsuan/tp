package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
<<<<<<< HEAD:src/test/java/seedu/address/model/student/StudentTest.java
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AVERAGE;
=======
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
>>>>>>> Change occurences of Phone to Telegram:src/test/java/seedu/address/model/person/PersonTest.java
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudent.ALICE;
import static seedu.address.testutil.TypicalStudent.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudentBuilder;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

<<<<<<< HEAD:src/test/java/seedu/address/model/student/StudentTest.java
        // different phone and email -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));
=======
        // different telegram and email -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
>>>>>>> Change occurences of Phone to Telegram:src/test/java/seedu/address/model/person/PersonTest.java

        // different name -> returns false
        editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

<<<<<<< HEAD:src/test/java/seedu/address/model/student/StudentTest.java
        // same name, same phone, different attributes -> returns true
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_AVERAGE).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_AVERAGE).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_AVERAGE).build();
        assertTrue(ALICE.isSameStudent(editedAlice));
=======
        // same name, same telegram, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same telegram, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
>>>>>>> Change occurences of Phone to Telegram:src/test/java/seedu/address/model/person/PersonTest.java
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different student -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

<<<<<<< HEAD:src/test/java/seedu/address/model/student/StudentTest.java
        // different phone -> returns false
        editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
=======
        // different telegram -> returns false
        editedAlice = new PersonBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).build();
>>>>>>> Change occurences of Phone to Telegram:src/test/java/seedu/address/model/person/PersonTest.java
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_AVERAGE).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
