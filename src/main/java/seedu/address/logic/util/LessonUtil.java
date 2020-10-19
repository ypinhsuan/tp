package seedu.address.logic.util;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.AddAttendanceCommand.MESSAGE_DUPLICATE_ATTENDANCE;
import static seedu.address.logic.commands.DeleteAttendanceCommand.MESSAGE_MISSING_ATTENDANCE;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.attendance.AttendanceRecordList;
import seedu.address.model.attendance.Week;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.NumberOfOccurrences;
import seedu.address.model.lesson.Venue;
import seedu.address.model.student.Student;

/**
 * Contains utility methods for modifying {@code Attendance}s in {@code Lesson}s.
 */
public class LessonUtil {

    /**
     * Adds {@code attendanceToAdd} to {@code targetLesson}.
     * All existing {@code Attendance}s in {@code targetLesson} are copied to the new {@code Lesson}.
     *
     * @throws CommandException if an {@code Attendance} already exists for the {@code targetStudent}
     * in the {@code targetWeek}.
     */
    public static Lesson addAttendanceToLesson(
            Lesson targetLesson, Student targetStudent, Week targetWeek, Attendance attendanceToAdd)
            throws CommandException {
        requireAllNonNull(targetLesson, targetStudent, targetWeek, attendanceToAdd);

        if (targetLesson.getAttendanceRecordList().hasAttendance(targetStudent, targetWeek)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTENDANCE);
        }

        List<AttendanceRecord> attendanceRecords = targetLesson.getAttendanceRecordList().getAttendanceRecordList();
        Map<UUID, Attendance> record = targetLesson.getAttendanceRecordList()
                .getAttendanceRecord(targetWeek).getAttendanceRecord();
        Map<UUID, Attendance> updatedRecord = new HashMap<>(record);
        updatedRecord.put(targetStudent.getUuid(), attendanceToAdd);

        assert updatedRecord.size() == record.size() + 1;

        AttendanceRecord updatedAttendanceRecord = new AttendanceRecord(updatedRecord);
        List<AttendanceRecord> updatedAttendanceRecords = new ArrayList<>(attendanceRecords);
        updatedAttendanceRecords.set(targetWeek.getZeroBasedWeekIndex(), updatedAttendanceRecord);
        AttendanceRecordList updatedAttendanceRecordList = new AttendanceRecordList(updatedAttendanceRecords);

        // unchanged lesson fields
        LocalTime startTime = targetLesson.getStartTime();
        LocalTime endTime = targetLesson.getEndTime();
        Day day = targetLesson.getDay();
        NumberOfOccurrences numberOfOccurrences = targetLesson.getNumberOfOccurrences();
        Venue venue = targetLesson.getVenue();

        return new Lesson(startTime, endTime, day, numberOfOccurrences, venue, updatedAttendanceRecordList);
    }

    /**
     * Edits the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} from the
     * {@code targetLesson}. All other existing {@code Attendance}s in {@code targetLesson} are copied
     * to the new {@code Lesson}.
     *
     * @throws CommandException if the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek}
     * does not exist.
     */
    public static Lesson editAttendanceInLesson(
            Lesson targetLesson, Student targetStudent, Week targetWeek, Attendance attendanceToEdit)
            throws CommandException {
        requireAllNonNull(targetLesson, targetStudent, targetWeek, attendanceToEdit);

        List<AttendanceRecord> attendanceRecords = targetLesson.getAttendanceRecordList().getAttendanceRecordList();
        Map<UUID, Attendance> record = targetLesson.getAttendanceRecordList()
                .getAttendanceRecord(targetWeek).getAttendanceRecord();

        if (!record.containsKey(targetStudent.getUuid())) {
            throw new CommandException(MESSAGE_MISSING_ATTENDANCE);
        }

        Map<UUID, Attendance> updatedRecord = new HashMap<>(record);
        updatedRecord.put(targetStudent.getUuid(), attendanceToEdit);

        assert updatedRecord.size() == record.size();

        AttendanceRecord updatedAttendanceRecord = new AttendanceRecord(updatedRecord);
        List<AttendanceRecord> updatedAttendanceRecords = new ArrayList<>(attendanceRecords);
        updatedAttendanceRecords.set(targetWeek.getZeroBasedWeekIndex(), updatedAttendanceRecord);
        AttendanceRecordList updatedAttendanceRecordList = new AttendanceRecordList(updatedAttendanceRecords);

        // unchanged lesson fields
        LocalTime startTime = targetLesson.getStartTime();
        LocalTime endTime = targetLesson.getEndTime();
        Day day = targetLesson.getDay();
        NumberOfOccurrences numberOfOccurrences = targetLesson.getNumberOfOccurrences();
        Venue venue = targetLesson.getVenue();

        return new Lesson(startTime, endTime, day, numberOfOccurrences, venue, updatedAttendanceRecordList);
    }

    /**
     * Removes the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} from the
     * {@code targetLesson}. All other existing {@code Attendance}s in {@code targetLesson} are copied
     * to the new {@code Lesson}.
     *
     * @throws CommandException if the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek}
     * does not exist.
     */
    public static Lesson deleteAttendanceFromLesson(
            Lesson targetLesson, Student targetStudent, Week targetWeek) throws CommandException {
        requireAllNonNull(targetLesson, targetStudent, targetWeek);

        List<AttendanceRecord> attendanceRecords = targetLesson.getAttendanceRecordList().getAttendanceRecordList();
        Map<UUID, Attendance> record = targetLesson.getAttendanceRecordList()
                .getAttendanceRecord(targetWeek).getAttendanceRecord();
        Map<UUID, Attendance> updatedRecord = new HashMap<>(record);

        if (!updatedRecord.containsKey(targetStudent.getUuid())) {
            throw new CommandException(MESSAGE_MISSING_ATTENDANCE);
        }

        // delete attendance record
        updatedRecord.remove(targetStudent.getUuid());

        assert updatedRecord.size() == record.size() - 1;

        AttendanceRecord updatedAttendanceRecord = new AttendanceRecord(updatedRecord);
        List<AttendanceRecord> updatedAttendanceRecords = new ArrayList<>(attendanceRecords);
        updatedAttendanceRecords.set(targetWeek.getZeroBasedWeekIndex(), updatedAttendanceRecord);
        AttendanceRecordList updatedAttendanceRecordList = new AttendanceRecordList(updatedAttendanceRecords);

        // unchanged lesson fields
        LocalTime startTime = targetLesson.getStartTime();
        LocalTime endTime = targetLesson.getEndTime();
        Day day = targetLesson.getDay();
        NumberOfOccurrences numberOfOccurrences = targetLesson.getNumberOfOccurrences();
        Venue venue = targetLesson.getVenue();

        return new Lesson(startTime, endTime, day, numberOfOccurrences, venue, updatedAttendanceRecordList);
    }
}
