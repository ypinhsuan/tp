package seedu.address.model.util;

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
 * Contains utility methods for modifying {@code Attendances} in {@code Lessons}.
 */
public class LessonModificationUtil {

    /**
     * Adds all attendances in the target lesson to the new lesson.
     * The {@code attendanceToAdd} is added to the lesson.
     *
     * @throws CommandException if the {@code attendanceToAdd} already exists.
     */
    public static Lesson createModifiedLessonWithAddedAttendance(
            Lesson targetLesson, Student targetStudent, Week targetWeek, Attendance attendanceToAdd)
            throws CommandException {
        assert targetLesson != null;
        assert targetStudent != null;
        assert targetWeek != null;
        assert attendanceToAdd != null;

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
     * Adds all attendances in the target lesson to the new lesson.
     * The attendance of the {@code targetStudent} in the {@code targetWeek} is deleted from the lesson.
     *
     * @throws CommandException if the attendance of the {@code targetStudent} in the {@code targetWeek} does not exist.
     */
    public static Lesson createModifiedLessonWithDeletedAttendance(
            Lesson targetLesson, Student targetStudent, Week targetWeek) throws CommandException {
        assert targetLesson != null;
        assert targetStudent != null;
        assert targetWeek != null;

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
