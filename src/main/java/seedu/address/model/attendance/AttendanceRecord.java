package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;

/**
 * Represents the attendance of students in a single lesson class.
 * Guarantees immutability.
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
