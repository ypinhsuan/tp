package seedu.address.model.lesson;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Lesson {

    private final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Recurrence recurrence;
    private final Location location;
    private final Day day;

    public Lesson(LocalTime startTime, LocalTime endTime, Recurrence recurrence, Location location, Day day) {
        requireAllNonNull(startTime, endTime, recurrence, location, day);

        this.startTime = startTime;
        this.endTime = endTime;
        this.recurrence = recurrence;
        this.location = location;
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public Location getLocation() {
        return location;
    }

    public Day getDay() {
        return day;
    }

    public boolean isSameLesson(Lesson otherLesson) {
        if (otherLesson == this) {
            return true;
        }

        return otherLesson != null
                && otherLesson.getStartTime().equals(getStartTime())
                && otherLesson.getEndTime().equals(getStartTime())
                && otherLesson.getLocation().equals(getLocation())
                && otherLesson.getDay().equals(getDay());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;

        return otherLesson.getStartTime().equals(getStartTime())
                && otherLesson.getEndTime().equals(getStartTime())
                && otherLesson.getRecurrence().equals(getRecurrence())
                && otherLesson.getLocation().equals(getLocation())
                && otherLesson.getDay().equals(getDay());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(startTime, endTime, recurrence, location, day);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(day)
                .append(" Time: ")
                .append(TIME_FORMATTER.format(getStartTime()))
                .append(" to ")
                .append(TIME_FORMATTER.format(getEndTime()))
                .append(" Location: ")
                .append(getLocation());
        return builder.toString();
    }
}
