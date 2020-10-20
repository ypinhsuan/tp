package tutorspet.logic.util;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_WEEK;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE;
import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.AttendanceRecord;
import tutorspet.model.attendance.AttendanceRecordList;
import tutorspet.model.attendance.Week;
import tutorspet.model.student.Student;

public class AttendanceRecordListUtil {

    /**
     * Returns an {@code AttendanceRecord} where the {@code attendanceToAdd} has been added to the
     * {@code targetAttendanceRecord}.
     *
     * @throws CommandException if the {@code targetWeek} does not exist in the {@code targetAttendanceRecordList} or
     * if there is an existing {@code Attendance} for the {@code targetStudent}.
     */
    public static AttendanceRecordList addAttendance(AttendanceRecordList targetAttendanceRecordList,
                                                     Student targetStudent, Week targetWeek,
                                                     Attendance attendanceToAdd) throws CommandException {
        requireAllNonNull(targetAttendanceRecordList, targetStudent, targetWeek, attendanceToAdd);

        if (!targetAttendanceRecordList.isWeekContained(targetWeek)) {
            throw new CommandException(MESSAGE_INVALID_WEEK);
        }

        AttendanceRecord targetAttendanceRecord = targetAttendanceRecordList.getAttendanceRecord(targetWeek);

        AttendanceRecord editedWeekAttendanceRecord =
                AttendanceRecordUitl.addAttendance(targetAttendanceRecord, targetStudent, attendanceToAdd);

        List<AttendanceRecord> modifiedAttendanceRecordList =
                new ArrayList<>(targetAttendanceRecordList.getAttendanceRecordList());
        modifiedAttendanceRecordList.set(targetWeek.getZeroBasedWeekIndex(), editedWeekAttendanceRecord);
        return new AttendanceRecordList(modifiedAttendanceRecordList);
    }

    /**
     * Returns an {@code AttendanceRecordList} where the {@code AttendanceToSet} has replaced the existing
     * {@code Attendance} for the {@code targetStudent} in the {@code targetWeek} in the
     * {@code targetAttendanceRecordList}.
     *
     * @throws CommandException if the {@code targetWeek} does not exist in the {@code targetAttendanceRecordList} or
     * if there does not exist an existing {@code Attendance} for the {@code targetStudent}.
     */
    public static AttendanceRecordList editAttendance(AttendanceRecordList targetAttendanceRecordList,
                                                      Student targetStudent, Week targetWeek,
                                                      Attendance attendanceToSet) throws CommandException {
        requireAllNonNull(targetAttendanceRecordList, targetStudent, targetWeek, attendanceToSet);

        if (!targetAttendanceRecordList.isWeekContained(targetWeek)) {
            throw new CommandException(MESSAGE_INVALID_WEEK);
        }

        AttendanceRecord targetAttendanceRecord = targetAttendanceRecordList.getAttendanceRecord(targetWeek);

        AttendanceRecord editedWeekAttendanceRecord =
                AttendanceRecordUitl.setAttendance(targetAttendanceRecord, targetStudent, attendanceToSet);

        List<AttendanceRecord> modifiedAttendanceRecordList =
                new ArrayList<>(targetAttendanceRecordList.getAttendanceRecordList());
        modifiedAttendanceRecordList.set(targetWeek.getZeroBasedWeekIndex(), editedWeekAttendanceRecord);
        return new AttendanceRecordList(modifiedAttendanceRecordList);
    }

    /**
     * Returns an {@code AttendanceRecordList} where the {@code Attendance} for the {@code targetStudent} in the
     * {@code targetWeek} in the {@code targetAttendanceRecordList} has been removed.
     *
     * @throws CommandException if the {@code targetWeek} does not exist in the {@code targetAttendanceRecordList} or
     * if there does not exist an existing {@code Attendance} for the {@code targetStudent}.
     */
    public static AttendanceRecordList removeAttendance(AttendanceRecordList targetAttendanceRecordList,
                                                        Student targetStudent,
                                                        Week targetWeek) throws CommandException {
        requireAllNonNull(targetAttendanceRecordList, targetStudent, targetWeek);

        if (!targetAttendanceRecordList.isWeekContained(targetWeek)) {
            throw new CommandException(MESSAGE_INVALID_WEEK);
        }

        AttendanceRecord targetAttendanceRecord = targetAttendanceRecordList.getAttendanceRecord(targetWeek);

        AttendanceRecord editedWeekAttendanceRecord =
                AttendanceRecordUitl.removeAttendance(targetAttendanceRecord, targetStudent);

        List<AttendanceRecord> modifiedAttendanceRecordList =
                new ArrayList<>(targetAttendanceRecordList.getAttendanceRecordList());
        modifiedAttendanceRecordList.set(targetWeek.getZeroBasedWeekIndex(), editedWeekAttendanceRecord);
        return new AttendanceRecordList(modifiedAttendanceRecordList);
    }

    /**
     * Returns true if an {@code Attendance} exists for the {@code targetStudent} in the {@code targetWeek} in the
     * {@code targetAttendanceRecordList}.
     *
     * @throws CommandException if the {@code targetWeek} does not exist in the {@code targetAttendanceRecordList} or
     * if there does not exist an existing {@code Attendance} for the {@code targetStudent}.
     */
    public static boolean hasAttendance(AttendanceRecordList targetAttendanceRecordList, Student targetStudent,
                                           Week targetWeek) throws CommandException {
        requireAllNonNull(targetAttendanceRecordList, targetStudent, targetWeek);

        if (!targetAttendanceRecordList.isWeekContained(targetWeek)) {
            throw new CommandException(MESSAGE_INVALID_WEEK);
        }

        AttendanceRecord targetAttendanceRecord = targetAttendanceRecordList.getAttendanceRecord(targetWeek);

        return targetAttendanceRecord.hasAttendance(targetStudent.getUuid());
    }

    /**
     * Returns the {@code Attendance} for the {@code targetStudent} in the {@code targetWeek} in the
     * {@code targetAttendanceRecordList}.
     *
     * @throws CommandException if the {@code targetWeek} does not exist in the {@code targetAttendanceRecordList} or
     * if there does not exist an existing {@code Attendance} for the {@code targetStudent}.
     */
    public static Attendance getAttendance(AttendanceRecordList targetAttendanceRecordList, Student targetStudent,
                                           Week targetWeek) throws CommandException {
        requireAllNonNull(targetAttendanceRecordList, targetStudent, targetWeek);

        if (!targetAttendanceRecordList.isWeekContained(targetWeek)) {
            throw new CommandException(MESSAGE_INVALID_WEEK);
        }

        AttendanceRecord targetAttendanceRecord = targetAttendanceRecordList.getAttendanceRecord(targetWeek);

        if (!targetAttendanceRecord.hasAttendance(targetStudent.getUuid())) {
            throw new CommandException(MESSAGE_MISSING_STUDENT_ATTENDANCE);
        }

        return targetAttendanceRecord.getAttendance(targetStudent.getUuid());
    }

    /**
     * Returns an ordered and unmodifiable {@code List<Optional<Attendance>} summarising the {@code targetStudent}'s
     * attendance in the respective weeks.
     */
    public static List<Optional<Attendance>> getAttendances(AttendanceRecordList targetAttendanceRecordList,
                                                            Student targetStudent) {
        requireAllNonNull(targetAttendanceRecordList, targetStudent);

        List<Optional<Attendance>> studentRecords = new ArrayList<>();

        List<AttendanceRecord> attendanceRecords = targetAttendanceRecordList.getAttendanceRecordList();

        for (AttendanceRecord attendanceRecord : attendanceRecords) {
            if (attendanceRecord.hasAttendance(targetStudent.getUuid())) {
                studentRecords.add(Optional.of(attendanceRecord.getAttendance(targetStudent.getUuid())));
            } else {
                studentRecords.add(Optional.empty());
            }
        }

        return Collections.unmodifiableList(studentRecords);
    }
}
