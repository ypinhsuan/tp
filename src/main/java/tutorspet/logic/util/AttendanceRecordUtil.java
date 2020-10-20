package tutorspet.logic.util;

import static tutorspet.commons.core.Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE;
import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;
import static tutorspet.logic.commands.AddAttendanceCommand.MESSAGE_DUPLICATE_ATTENDANCE;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.AttendanceRecord;
import tutorspet.model.student.Student;

/**
 * Contains utility methods for modifying {@code Attendance}s in {@code AttendanceRecord}.
 */
public class AttendanceRecordUtil {

    /**
     * Returns an {@code AttendanceRecord} where the {@code attendanceToAdd} has been added to the
     * {@code targetAttendanceRecord}.
     *
     * @throws CommandException if there is an existing {@code Attendance} for the {@code targetStudent}.
     */
    public static AttendanceRecord addAttendance(AttendanceRecord targetAttendanceRecord, Student targetStudent,
                                                 Attendance attendanceToAdd) throws CommandException {
        requireAllNonNull(targetAttendanceRecord, targetStudent, attendanceToAdd);

        if (targetAttendanceRecord.hasAttendance(targetStudent.getUuid())) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTENDANCE);
        }

        Map<UUID, Attendance> attendanceMap = new HashMap<>(targetAttendanceRecord.getAttendanceRecord());
        attendanceMap.put(targetStudent.getUuid(), attendanceToAdd);

        assert attendanceMap.size() - 1 == targetAttendanceRecord.getAttendanceRecord().size();

        return new AttendanceRecord(attendanceMap);
    }

    /**
     * Returns an {@code AttendanceRecord} where the {@code attendanceToSet} has replaced the existing
     * {@code Attendance} for the {@code targetStudent} in the {@code targetAttendanceRecord}.
     *
     * @throws CommandException if there does not exist an existing {@code Attendance} for the {@code targetStudent}.
     */
    public static AttendanceRecord setAttendance(AttendanceRecord targetAttendanceRecord, Student targetStudent,
                                                 Attendance attendanceToSet) throws CommandException {
        requireAllNonNull(targetAttendanceRecord, targetStudent, attendanceToSet);

        if (!targetAttendanceRecord.hasAttendance(targetStudent.getUuid())) {
            throw new CommandException(MESSAGE_MISSING_STUDENT_ATTENDANCE);
        }

        Map<UUID, Attendance> attendanceMap = new HashMap<>(targetAttendanceRecord.getAttendanceRecord());
        attendanceMap.put(targetStudent.getUuid(), attendanceToSet);

        assert attendanceMap.size() == targetAttendanceRecord.getAttendanceRecord().size();

        return new AttendanceRecord(attendanceMap);
    }

    /**
     * Returns an {@code AttendanceRecord} where the {@code Attendance} for the {@code targetStudent} in the
     * {@code targetAttendanceRecord} has been removed.
     *
     * @throws CommandException if there does not exist an existing {@code Attendance} for the {@code targetStudent}.
     */
    public static AttendanceRecord removeAttendance(AttendanceRecord targetAttendanceRecord,
                                                    Student targetStudent) throws CommandException {
        requireAllNonNull(targetAttendanceRecord, targetStudent);

        if (!targetAttendanceRecord.hasAttendance(targetStudent.getUuid())) {
            throw new CommandException(MESSAGE_MISSING_STUDENT_ATTENDANCE);
        }

        Map<UUID, Attendance> attendanceMap = new HashMap<>(targetAttendanceRecord.getAttendanceRecord());
        attendanceMap.remove(targetStudent.getUuid());

        assert attendanceMap.size() + 1 == targetAttendanceRecord.getAttendanceRecord().size();

        return new AttendanceRecord(attendanceMap);
    }
}
