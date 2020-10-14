package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.InvalidWeekException;
import seedu.address.model.student.Student;

/**
 * Represents the attendance records of a {@code Lesson}.
 * Contains a list of all attendance records.
 * Guarantees immutability.
 */
public class AttendanceRecordList {

    private final List<AttendanceRecord> recordList;

    /**
     * Constructor method.
     * Each element in the list is initialized to an empty AttendanceRecord.
     */
    public AttendanceRecordList(int numberOfOccurrences) {
        this.recordList = Collections.nCopies(numberOfOccurrences, new AttendanceRecord());
    }

    /**
     * Overloaded constructor method.
     * Requires recordList to be non null. Also converts recordList to fixed size.
     */
    public AttendanceRecordList(List<AttendanceRecord> recordList) {
        requireNonNull(recordList);

        int size = recordList.size();
        this.recordList = Arrays.asList(recordList.toArray(new AttendanceRecord[size]));
    }

    /**
     * Returns true if week number is less than the total number of occurrences.
     * Week number should have been zero based.
     */
    private boolean isWeekContained(int week) {
        return week < recordList.size();
    }

    public List<AttendanceRecord> getAttendanceRecordList() {
        return Collections.unmodifiableList(recordList);
    }

    /**
     * Gets the {@code Attendance} of a {@code Student} in a particular {@code week}.
     */
    public Attendance getAttendance(Student student, int week)
            throws InvalidWeekException, AttendanceNotFoundException {
        requireNonNull(student);

        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        UUID studentUuid = student.getUuid();
        return recordList.get(week).getAttendance(studentUuid);
    }

    /**
     * Gets the {@code AttendanceRecord} of a particular {@code week}.
     */
    public AttendanceRecord getAttendanceRecord(int week) throws InvalidWeekException {
        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        return recordList.get(week);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttendanceRecordList // instanceof handles nulls
                && ((AttendanceRecordList) other).recordList.equals(recordList));
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordList);
    }
}
