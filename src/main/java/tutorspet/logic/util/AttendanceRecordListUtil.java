package tutorspet.logic.util;

import static tutorspet.commons.core.Messages.MESSAGE_INVALID_WEEK;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE;
import static tutorspet.commons.core.Messages.MESSAGE_NO_LESSON_ATTENDED;
import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;
import static tutorspet.logic.util.AttendanceRecordUtil.addAttendance;
import static tutorspet.logic.util.AttendanceRecordUtil.removeAttendance;
import static tutorspet.logic.util.AttendanceRecordUtil.setAttendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.AttendanceRecord;
import tutorspet.model.attendance.AttendanceRecordList;
import tutorspet.model.attendance.Week;
import tutorspet.model.student.Student;

/**
 * Contains utility methods for modifying {@code Attendance}s in {@code AttendanceRecordList}.
 */
public class AttendanceRecordListUtil {

    /**
     * Returns an {@code AttendanceRecordList} where the {@code Attendance}s of the {@code studentToRemove}
     * have been removed.
     */
    public static AttendanceRecordList removeStudentFromAttendanceRecordList(
            AttendanceRecordList targetAttendanceRecordList, Student studentToRemove) {
        requireAllNonNull(targetAttendanceRecordList, studentToRemove);

        List<AttendanceRecord> attendanceRecords = targetAttendanceRecordList.getAttendanceRecordList();

        List<AttendanceRecord> updatedAttendanceRecords =
                attendanceRecords.stream().map(attendanceRecord ->
                        removeStudentFromAttendanceRecord(attendanceRecord, studentToRemove))
                        .collect(Collectors.toUnmodifiableList());

        return new AttendanceRecordList(updatedAttendanceRecords);
    }

    /**
     * Returns an {@code AttendanceRecordList} where the {@code Attendance}s of all {@code Student}s have been removed.
     */
    public static AttendanceRecordList removeAllStudentsFromAttendanceRecordList(
            AttendanceRecordList targetAttendanceRecordList) {
        requireAllNonNull(targetAttendanceRecordList);

        List<AttendanceRecord> attendanceRecords = targetAttendanceRecordList.getAttendanceRecordList();

        List<AttendanceRecord> updatedAttendanceRecords =
                attendanceRecords.stream().map(attendanceRecord -> new AttendanceRecord())
                        .collect(Collectors.toUnmodifiableList());

        return new AttendanceRecordList(updatedAttendanceRecords);
    }

    /**
     * Returns an {@code AttendanceRecord} where the {@code attendanceToAdd} has been added to the
     * {@code targetAttendanceRecord}.
     *
     * @throws CommandException if the {@code targetWeek} does not exist in the {@code targetAttendanceRecordList} or
     * if there is an existing {@code Attendance} for the {@code targetStudent}.
     */
    public static AttendanceRecordList addAttendanceToAttendanceRecordList(
            AttendanceRecordList targetAttendanceRecordList,
            Student targetStudent, Week targetWeek,
            Attendance attendanceToAdd) throws CommandException {
        requireAllNonNull(targetAttendanceRecordList, targetStudent, targetWeek, attendanceToAdd);

        if (!targetAttendanceRecordList.isWeekContained(targetWeek)) {
            throw new CommandException(MESSAGE_INVALID_WEEK);
        }

        AttendanceRecord targetAttendanceRecord = targetAttendanceRecordList.getAttendanceRecord(targetWeek);

        AttendanceRecord editedWeekAttendanceRecord =
                addAttendance(targetAttendanceRecord, targetStudent, attendanceToAdd);

        return replaceAttendanceRecordInList(targetAttendanceRecordList, targetWeek, editedWeekAttendanceRecord);
    }

    /**
     * Returns an {@code AttendanceRecordList} where the {@code AttendanceToSet} has replaced the existing
     * {@code Attendance} for the {@code targetStudent} in the {@code targetWeek} in the
     * {@code targetAttendanceRecordList}.
     *
     * @throws CommandException if the {@code targetWeek} does not exist in the {@code targetAttendanceRecordList} or
     * if the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} does not exist.
     */
    public static AttendanceRecordList editAttendanceInAttendanceRecordList(
            AttendanceRecordList targetAttendanceRecordList,
            Student targetStudent, Week targetWeek,
            Attendance attendanceToSet) throws CommandException {
        requireAllNonNull(targetAttendanceRecordList, targetStudent, targetWeek, attendanceToSet);

        if (!targetAttendanceRecordList.isWeekContained(targetWeek)) {
            throw new CommandException(MESSAGE_INVALID_WEEK);
        }

        AttendanceRecord targetAttendanceRecord = targetAttendanceRecordList.getAttendanceRecord(targetWeek);

        AttendanceRecord editedWeekAttendanceRecord =
                setAttendance(targetAttendanceRecord, targetStudent, attendanceToSet);

        return replaceAttendanceRecordInList(targetAttendanceRecordList, targetWeek, editedWeekAttendanceRecord);
    }

    /**
     * Returns an {@code AttendanceRecordList} where the {@code Attendance} for the {@code targetStudent} in the
     * {@code targetWeek} in the {@code targetAttendanceRecordList} has been removed.
     *
     * @throws CommandException if the {@code targetWeek} does not exist in the {@code targetAttendanceRecordList} or
     * if the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} does not exist.
     */
    public static AttendanceRecordList removeAttendanceFromAttendanceRecordList(
            AttendanceRecordList targetAttendanceRecordList,
            Student targetStudent,
            Week targetWeek) throws CommandException {
        requireAllNonNull(targetAttendanceRecordList, targetStudent, targetWeek);

        if (!targetAttendanceRecordList.isWeekContained(targetWeek)) {
            throw new CommandException(MESSAGE_INVALID_WEEK);
        }

        AttendanceRecord targetAttendanceRecord = targetAttendanceRecordList.getAttendanceRecord(targetWeek);

        AttendanceRecord editedWeekAttendanceRecord =
                removeAttendance(targetAttendanceRecord, targetStudent);

        return replaceAttendanceRecordInList(targetAttendanceRecordList, targetWeek, editedWeekAttendanceRecord);
    }

    /**
     * Returns the {@code Attendance} for the {@code targetStudent} in the {@code targetWeek} in the
     * {@code targetAttendanceRecordList}.
     *
     * @throws CommandException if the {@code targetWeek} does not exist in the {@code targetAttendanceRecordList} or
     * if the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} does not exist.
     */
    public static Attendance getAttendanceFromAttendanceRecordList(
            AttendanceRecordList targetAttendanceRecordList, Student targetStudent,
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
     * Returns {@code targetStudent}'s average participation score.
     */
    public static double getScoreFromAttendance(AttendanceRecordList targetAttendanceRecordList, Student targetStudent)
            throws CommandException {
        requireAllNonNull(targetAttendanceRecordList, targetStudent);

        List<Optional<Attendance>> listOfAttendance = getAttendances(targetAttendanceRecordList, targetStudent);

        int totalScore = 0;
        int numOfWeeksParticipated = 0;

        for (Optional<Attendance> attendance : listOfAttendance) {
            if (attendance.isPresent()) {
                totalScore = totalScore + attendance.get().getParticipationScore();
                numOfWeeksParticipated++;
            }
        }

        if (numOfWeeksParticipated == 0) {
            throw new CommandException(MESSAGE_NO_LESSON_ATTENDED);
        }

        return (double) totalScore / numOfWeeksParticipated;
    }

    /**
     * Returns a {@code List<Integer>} containing weeks in which {@code targetStudent} did not attend.
     */
    public static List<Integer> getAbsentWeekFromAttendance(AttendanceRecordList targetAttendanceRecordList,
                                                Student targetStudent) {
        requireAllNonNull(targetAttendanceRecordList, targetStudent);

        List<Optional<Attendance>> listOfAttendance = getAttendances(targetAttendanceRecordList, targetStudent);
        int weekNo = 1;
        List<Integer> weeksNotPresent = new ArrayList<>();

        for (Optional<Attendance> attendance : listOfAttendance) {
            if (attendance.isEmpty()) {
                weeksNotPresent.add(weekNo);
            }
            weekNo++;
        }

        return weeksNotPresent;
    }

    private static AttendanceRecordList replaceAttendanceRecordInList(AttendanceRecordList attendanceRecordList,
                                                                      Week week,
                                                                      AttendanceRecord attendanceRecordToSet) {
        List<AttendanceRecord> modifiedAttendanceRecordList =
                new ArrayList<>(attendanceRecordList.getAttendanceRecordList());
        modifiedAttendanceRecordList.set(week.getZeroBasedWeekIndex(), attendanceRecordToSet);
        return new AttendanceRecordList(modifiedAttendanceRecordList);
    }

    /**
     * Returns an {@code AttendanceRecord} where the {@code Attendance} of the {@code studentToRemove} has been removed.
     * If the {@code studentToRemove} does not exist in the {@code attendanceRecord}, the {@code attendanceRecord} is
     * returned unchanged.
     */
    private static AttendanceRecord removeStudentFromAttendanceRecord(
            AttendanceRecord attendanceRecord, Student studentToRemove) {
        if (!attendanceRecord.hasAttendance(studentToRemove.getUuid())) {
            return attendanceRecord;
        }

        try {
            return removeAttendance(attendanceRecord, studentToRemove);
        } catch (CommandException e) {
            return attendanceRecord;
        }
    }

    private static List<Optional<Attendance>> getAttendances(AttendanceRecordList targetAttendanceRecordList,
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
