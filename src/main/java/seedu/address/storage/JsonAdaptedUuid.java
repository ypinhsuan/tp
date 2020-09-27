package seedu.address.storage;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of {@link java.util.UUID}.
 */
class JsonAdaptedUuid {

    public static final String MESSAGE_NULL_UUID = "UUID is null";

    private final String uuid;

    /**
     * Constructs a {@code JsonAdaptedUuid} with the given {@code uuid}.
     */
    @JsonCreator
    public JsonAdaptedUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Converts a given {@code Uuid} into this class for Jackson use.
     */
    public JsonAdaptedUuid(UUID source) {
        uuid = source.toString();
    }

    @JsonValue
    public String getUuid() {
        return uuid;
    }

    /**
     * Converts this Jackson-friendly adapted uuid object into the model's {@code Uuid} object.
     *
     * @throws IllegalValueException if adapted uuid is null.
     */
    public UUID toModelType() throws IllegalValueException {
        if (uuid == null) {
            throw new IllegalValueException(MESSAGE_NULL_UUID);
        }
        return UUID.fromString(uuid);
    }
}
