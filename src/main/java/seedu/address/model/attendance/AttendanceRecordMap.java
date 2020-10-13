package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.DuplicateAttendanceException;
import seedu.address.model.attendance.exceptions.InvalidWeekException;
import seedu.address.model.student.Student;

/**
 * Represents the attendance record of a {@code Lesson}.
 * Maps the {@code Week} to an {@code AttendanceRecord}.
 * Guarantees immutability.
 */
public class AttendanceRecordMap {

    private final int recurrences;
    private final HashMap<Week, AttendanceRecord> recordMap;

    /**
     * Constructor method.
     */
    public AttendanceRecordMap(int recurrences) {
        this.recurrences = recurrences;
        this.recordMap = new HashMap<>();
    }

    /**
     * Overloaded constructor method.
     * Requires recordMap to be non null.
     */
    public AttendanceRecordMap(int recurrences, HashMap<Week, AttendanceRecord> recordMap) {
        requireNonNull(recordMap);

        this.recurrences = recurrences;
        this.recordMap = recordMap;
    }

    /**
     * Returns true if week number is less than the total number of recurrences.
     */
    private boolean isWeekContained(Week week) {
        return week.getWeekNumber() <= recurrences;
    }

    /**
     * Gets the {@code Attendance} of a {@code Student} in a particular {@code Week}.
     */
    public Attendance getAttendance(Student student, Week week)
            throws InvalidWeekException, AttendanceNotFoundException {
        requireAllNonNull(student, week);

        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        if (!recordMap.containsKey(week)) {
            throw new AttendanceNotFoundException();
        }

        UUID studentUuid = student.getUuid();
        return recordMap.get(week).getAttendance(studentUuid);
    }

    /**
     * Adds a {@code Student}'s {@code Attendance} to the specified {@code Week}.
     */
    public AttendanceRecordMap addAttendance(Student student, Week week, Attendance attendance)
            throws InvalidWeekException, DuplicateAttendanceException {
        requireAllNonNull(student, attendance);

        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        UUID studentUuid = student.getUuid();
        AttendanceRecord toAdd = recordMap.containsKey(week)
                ? recordMap.get(week).addAttendance(studentUuid, attendance)
                : new AttendanceRecord().addAttendance(studentUuid, attendance);
        recordMap.put(week, toAdd);
        return new AttendanceRecordMap(recurrences, new HashMap<>(recordMap));
    }

    /**
     * Edits a {@code Student}'s {@code Attendance} in the specified {@code Week}.
     */
    public AttendanceRecordMap editAttendance(Student student, Week week, Attendance attendance)
            throws InvalidWeekException, AttendanceNotFoundException {
        requireAllNonNull(student, attendance);

        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        if (!recordMap.containsKey(week)) {
            throw new AttendanceNotFoundException();
        }

        UUID studentUuid = student.getUuid();
        AttendanceRecord toEdit = recordMap.get(week).editAttendance(studentUuid, attendance);
        recordMap.put(week, toEdit);
        return new AttendanceRecordMap(recurrences, new HashMap<>(recordMap));
    }

    /**
     * Deletes a {@code Student}'s {@code Attendance} in the specified {@code Week}.
     */
    public AttendanceRecordMap deleteAttendance(Student student, Week week)
            throws InvalidWeekException, AttendanceNotFoundException {
        requireNonNull(student);

        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        if (!recordMap.containsKey(week)) {
            throw new AttendanceNotFoundException();
        }

        UUID studentUuid = student.getUuid();
        AttendanceRecord toDelete = recordMap.get(week).deleteAttendance(studentUuid);
        recordMap.put(week, toDelete);
        return new AttendanceRecordMap(recurrences, new HashMap<>(recordMap));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttendanceRecordMap // instanceof handles nulls
                && ((AttendanceRecordMap) other).recordMap.equals(recordMap));
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordMap);
    }
}
