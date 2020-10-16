package seedu.address.storage.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.attendance.Week;

class JsonAdaptedAttendanceRecord {

    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "Attendance record contains duplicate students.";
    public static final String MESSAGE_INVALID_ATTENDANCE = "Attendance record contains invalid value(s).";

    private final int week;
    private final List<JsonAdaptedStudentAttendance> attendances = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedAttendanceRecord(@JsonProperty("week") int week,
                                       @JsonProperty("records") List<JsonAdaptedStudentAttendance> attendances) {
        this.week = week;
        if (attendances != null) {
            this.attendances.addAll(attendances);
        }
    }

    public JsonAdaptedAttendanceRecord(Week week, AttendanceRecord source) {
        this.week = week.getOneBasedWeekIndex();
        attendances.addAll(source.getAttendanceRecord().entrySet().stream().map(entry ->
                new JsonAdaptedStudentAttendance(entry.getKey(), entry.getValue()))
                .collect(Collectors.toUnmodifiableList()));
    }

    public Pair<Week, AttendanceRecord> toKeyValuePair() throws IllegalValueException {
        final Map<UUID, Attendance> attendancesMap = new HashMap<>();

        if (!Week.isValidWeek(Index.fromOneBased(week))) {
            throw new IllegalValueException(Week.MESSAGE_CONSTRAINTS);
        }

        if (attendances.stream().anyMatch(Objects::isNull)) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTENDANCE);
        }

        // Using a enhanced for-loop instead of streams since map cannot handle checked exceptions nicely.
        for (JsonAdaptedStudentAttendance attendance : attendances) {
            Pair<UUID, Attendance> pair = attendance.toKeyValuePair();
            if (attendancesMap.containsKey(pair.getKey())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ATTENDANCE);
            }
            attendancesMap.put(pair.getKey(), pair.getValue());
        }

        return new Pair<>(new Week(Index.fromOneBased(week)), new AttendanceRecord(attendancesMap));
    }
}
