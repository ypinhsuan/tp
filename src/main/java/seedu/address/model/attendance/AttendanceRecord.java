package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.deepCopyMap;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.UnaryOperator;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.DuplicateAttendanceException;

/**
 * Represents the attendance of students in a single lesson class.
 * Guarantees Immutability.
 */
public class AttendanceRecord {

    private final Map<UUID, Attendance> record;

    public AttendanceRecord() {
        this.record = new HashMap<>();
    }

    /**
     * Overloaded constructor method.
     * Requires record to be non null.
     */
    public AttendanceRecord(Map<UUID, Attendance> record) {
        requireNonNull(record);

        this.record = record;
    }

    public Map<UUID, Attendance> getAttendanceRecord() {
        return Collections.unmodifiableMap(record);
    }

    public AttendanceRecord deepCopy() {
        return new AttendanceRecord(
                deepCopyMap(record, uuidKey -> uuidKey, value -> value.deepCopy()));
    }

    /**
     * Gets the {@code Attendance} of the given {@code UUID}.
     */
    public Attendance getAttendance(UUID uuid) throws AttendanceNotFoundException {
        requireNonNull(uuid);

        if (!record.containsKey(uuid)) {
            throw new AttendanceNotFoundException();
        }

        return record.get(uuid);
    }

    /**
     * Adds a {@code UUID} and {@code Attendance} to the map.
     */
    public AttendanceRecord addAttendance(UUID uuid, Attendance attendance)
            throws DuplicateAttendanceException {
        requireAllNonNull(uuid, attendance);

        if (record.containsKey(uuid)) {
            throw new DuplicateAttendanceException();
        }

        Map<UUID, Attendance> copiedRecord =
                deepCopyMap(record, uuidKey -> uuidKey, value -> value.deepCopy());
        copiedRecord.put(uuid, attendance);
        return new AttendanceRecord(copiedRecord);
    }

    /**
     * Edits the {@code Attendance} of a given {@code UUID} in the map.
     */
    public AttendanceRecord editAttendance(UUID uuid, Attendance editedAttendance)
            throws AttendanceNotFoundException {
        requireAllNonNull(uuid, editedAttendance);

        if (!record.containsKey(uuid)) {
            throw new AttendanceNotFoundException();
        }

        Map<UUID, Attendance> copiedRecord =
                deepCopyMap(record, uuidKey -> uuidKey, value -> value.deepCopy());
        copiedRecord.put(uuid, editedAttendance);
        return new AttendanceRecord(copiedRecord);
    }

    /**
     * Deletes the given {@code UUID} entry from the map.
     */
    public AttendanceRecord deleteAttendance(UUID uuid) throws AttendanceNotFoundException {
        requireNonNull(uuid);

        if (!record.containsKey(uuid)) {
            throw new AttendanceNotFoundException();
        }

        Map<UUID, Attendance> copiedRecord =
                deepCopyMap(record, uuidKey -> uuidKey, value -> value.deepCopy());
        copiedRecord.remove(uuid);
        return new AttendanceRecord(copiedRecord);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttendanceRecord // instanceof handles nulls
                && ((AttendanceRecord) other).record.equals(record));
    }

    @Override
    public int hashCode() {
        return Objects.hash(record);
    }
}
