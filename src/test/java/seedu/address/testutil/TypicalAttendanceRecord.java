package seedu.address.testutil;

import static seedu.address.testutil.TypicalStudent.ALICE;
import static seedu.address.testutil.TypicalStudent.BENSON;
import static seedu.address.testutil.TypicalStudent.CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_33;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_51;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_80;

import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TypicalAttendanceRecord {

    public static final AttendanceRecord RECORD_EMPTY = new AttendanceRecordBuilder().build();

    public static final AttendanceRecord RECORD_ALICE_80 = new AttendanceRecordBuilder()
            .withEntry(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80)).build();

    public static final AttendanceRecord RECORD_BENSON_51_CARL_33 = new AttendanceRecordBuilder()
            .withEntry(BENSON.getUuid(), new Attendance(VALID_ATTENDANCE_51)).
            withEntry(CARL.getUuid(), new Attendance(VALID_ATTENDANCE_33)).build();

    private TypicalAttendanceRecord() {} // prevents instantiation

    public static List<AttendanceRecord> getTypicalAttendanceRecord() {
        return new ArrayList<>(Arrays.asList(RECORD_EMPTY, RECORD_ALICE_80, RECORD_BENSON_51_CARL_33));
    }
}
