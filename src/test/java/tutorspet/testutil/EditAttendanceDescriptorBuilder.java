package tutorspet.testutil;

import tutorspet.logic.commands.attendance.EditAttendanceCommand.EditAttendanceDescriptor;
import tutorspet.model.attendance.Attendance;

public class EditAttendanceDescriptorBuilder {

    private EditAttendanceDescriptor descriptor;

    public EditAttendanceDescriptorBuilder() {
        descriptor = new EditAttendanceDescriptor();
    }

    public EditAttendanceDescriptorBuilder(EditAttendanceDescriptor descriptor) {
        this.descriptor = new EditAttendanceDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAttendanceDescriptor} with fields containing {@code attendance} details.
     * @param attendance
     */
    public EditAttendanceDescriptorBuilder(Attendance attendance) {
        descriptor = new EditAttendanceDescriptor();
        descriptor.setParticipationScore(attendance.getParticipationScore());
    }

    /**
     * Sets the participation score of the {@code EditAttendanceDescriptor} that we are building.
     */
    public EditAttendanceDescriptorBuilder withParticipationScore(int participationScore) {
        descriptor.setParticipationScore(participationScore);
        return this;
    }

    public EditAttendanceDescriptor build() {
        return descriptor;
    }
}
