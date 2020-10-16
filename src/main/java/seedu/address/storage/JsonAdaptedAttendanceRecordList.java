package seedu.address.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.attendance.AttendanceRecordList;
import seedu.address.model.attendance.Week;

/**
 * Jackson-friendly version of {@link AttendanceRecordList}.
 */
public class JsonAdaptedAttendanceRecordList {

    public static final String MESSAGE_DUPLICATE_ATTENDANCE_RECORD = "Duplicate attendance records have been found";
    public static final String MESSAGE_WEEK_INDEX_MISMATCH = "An invalid week index has been found";
    public static final String MESSAGE_MISSING_ATTENDANCE_RECORD_LIST = "Attendance data is missing";

    private final List<JsonAdaptedAttendanceRecord> recordList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAttendanceRecordList} with the given attendance records.
     */
    @JsonCreator
    public JsonAdaptedAttendanceRecordList(@JsonProperty("recordList") List<JsonAdaptedAttendanceRecord> recordList) {
        if (recordList != null) {
            this.recordList.addAll(recordList);
        }
    }

    /**
     * Converts a given {@code AttendanceRecordList} into this class for Jackson use.
     */
    public JsonAdaptedAttendanceRecordList(AttendanceRecordList source) {
        List<AttendanceRecord> attendanceRecordList = source.getAttendanceRecordList();
        for (int i = 0; i < attendanceRecordList.size(); i++) {
            JsonAdaptedAttendanceRecord record =
                    new JsonAdaptedAttendanceRecord(new Week(Index.fromZeroBased(i)), attendanceRecordList.get(i));
            recordList.add(record);
        }
    }

    /**
     * Converts this Jackson-friendly adapted attendance record list object into the model's
     * {@code AttendanceRecordList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance records.
     */
    public AttendanceRecordList toModelType() throws IllegalValueException {
        if (recordList.size() == 0) {
            throw new IllegalValueException(MESSAGE_MISSING_ATTENDANCE_RECORD_LIST);
        }

        List<AttendanceRecord> attendanceRecords = new ArrayList<>(Collections.nCopies(recordList.size(), null));
        for (JsonAdaptedAttendanceRecord record : recordList) {
            Pair<Week, AttendanceRecord> pair = record.toKeyValuePair();

            if (pair.getKey().getOneBasedWeekIndex() > attendanceRecords.size()) {
                throw new IllegalValueException(MESSAGE_WEEK_INDEX_MISMATCH);
            }
            if (attendanceRecords.get(pair.getKey().getZeroBasedWeekIndex()) != null) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ATTENDANCE_RECORD);
            }

            attendanceRecords.set(pair.getKey().getZeroBasedWeekIndex(), pair.getValue());
        }

        assert attendanceRecords.stream().noneMatch(Objects::isNull);

        return new AttendanceRecordList(attendanceRecords);
    }
}
