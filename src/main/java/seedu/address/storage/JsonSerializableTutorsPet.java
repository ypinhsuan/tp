package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTutorsPet;
import seedu.address.model.TutorsPet;
import seedu.address.model.person.Person;

/**
 * An Immutable TutorsPet that is serializable to JSON format.
 */
@JsonRootName(value = "tutorspet")
class JsonSerializableTutorsPet {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTutorsPet} with the given persons.
     */
    @JsonCreator
    public JsonSerializableTutorsPet(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyTutorsPet} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTutorsPet}.
     */
    public JsonSerializableTutorsPet(ReadOnlyTutorsPet source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Tutor's Pet into the model's {@code TutorsPet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TutorsPet toModelType() throws IllegalValueException {
        TutorsPet tutorsPet = new TutorsPet();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (tutorsPet.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            tutorsPet.addPerson(person);
        }
        return tutorsPet;
    }

}
