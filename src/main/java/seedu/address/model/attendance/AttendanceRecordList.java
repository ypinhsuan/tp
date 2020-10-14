package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.deepCopyList;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.DuplicateAttendanceException;
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
     */
    private boolean isWeekContained(Week week) {
        return week.getWeekNumber() <= recordList.size();
    }

    public List<AttendanceRecord> getAttendanceRecordList() {
        return Collections.unmodifiableList(recordList);
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

        UUID studentUuid = student.getUuid();
        int weekIndex = week.getZeroBasedWeekNumber();
        return recordList.get(weekIndex).getAttendance(studentUuid);
    }

    /**
     * Adds a {@code Student}'s {@code Attendance} to the specified {@code Week}.
     */
    public AttendanceRecordList addAttendance(Student student, Week week, Attendance attendance)
            throws InvalidWeekException, DuplicateAttendanceException {
        requireAllNonNull(student, week, attendance);

        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        UUID studentUuid = student.getUuid();
        int weekIndex = week.getZeroBasedWeekNumber();
        List<AttendanceRecord> updatedRecordList = deepCopyList(recordList, x -> x.deepCopy());
        AttendanceRecord toAdd = updatedRecordList.get(weekIndex).addAttendance(studentUuid, attendance);
        updatedRecordList.set(weekIndex, toAdd);
        return new AttendanceRecordList(updatedRecordList);
    }

    /**
     * Edits a {@code Student}'s {@code Attendance} in the specified {@code Week}.
     */
    public AttendanceRecordList editAttendance(Student student, Week week, Attendance attendance)
            throws InvalidWeekException, AttendanceNotFoundException {
        requireAllNonNull(student, week, attendance);

        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        UUID studentUuid = student.getUuid();
        int weekIndex = week.getZeroBasedWeekNumber();
        List<AttendanceRecord> updatedRecordList = deepCopyList(recordList, x -> x.deepCopy());
        AttendanceRecord toEdit = updatedRecordList.get(weekIndex).editAttendance(studentUuid, attendance);
        updatedRecordList.set(weekIndex, toEdit);
        return new AttendanceRecordList(updatedRecordList);
    }

    /**
     * Deletes a {@code Student}'s {@code Attendance} in the specified {@code Week}.
     */
    public AttendanceRecordList deleteAttendance(Student student, Week week)
            throws InvalidWeekException, AttendanceNotFoundException {
        requireAllNonNull(student, week);

        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        UUID studentUuid = student.getUuid();
        int weekIndex = week.getZeroBasedWeekNumber();
        List<AttendanceRecord> updatedRecordList = deepCopyList(recordList, x -> x.deepCopy());
        AttendanceRecord toDelete = updatedRecordList.get(weekIndex).deleteAttendance(studentUuid);
        updatedRecordList.set(weekIndex, toDelete);
        return new AttendanceRecordList(updatedRecordList);
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
