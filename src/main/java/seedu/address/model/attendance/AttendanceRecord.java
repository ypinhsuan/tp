package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.DuplicateAttendanceException;

/**
 * Represents the attendance of students in a single lesson class.
 * Guarantees Immutability.
 */
public class AttendanceRecord {

    private final HashMap<UUID, Attendance> record;

    public AttendanceRecord() {
        this.record = new HashMap<>();
    }

    /**
     * Overloaded constructor method.
     * Requires record to be non null.
     */
    public AttendanceRecord(HashMap<UUID, Attendance> record) {
        requireNonNull(record);

        this.record = record;
    }

    /**
     * Gets the {@code Attendance} of the given {@code UUID}.
     */
    public Attendance getAttendance(UUID uuid) throws AttendanceNotFoundException {
        if (record.containsKey(uuid)) {
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

        record.put(uuid, attendance);
        return new AttendanceRecord(new HashMap<>(record));
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

        record.put(uuid, editedAttendance);
        return new AttendanceRecord(new HashMap<>(record));
    }

    /**
     * Deletes the given {@code UUID} entry from the map.
     */
    public AttendanceRecord deleteAttendance(UUID uuid) throws AttendanceNotFoundException {
        requireNonNull(uuid);

        if (!record.containsKey(uuid)) {
            throw new AttendanceNotFoundException();
        }

        record.remove(uuid);
        return new AttendanceRecord(new HashMap<>(record));
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
