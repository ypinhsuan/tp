package tutorspet.testutil;

import tutorspet.logic.commands.EditAttendanceCommand;
import tutorspet.model.attendance.Attendance;

public class EditAttendanceDescriptorBuilder {

    private EditAttendanceCommand.EditAttendanceDescriptor descriptor;

    public EditAttendanceDescriptorBuilder() {
        descriptor = new EditAttendanceCommand.EditAttendanceDescriptor();
    }

    public EditAttendanceDescriptorBuilder(EditAttendanceCommand.EditAttendanceDescriptor descriptor) {
        this.descriptor = new EditAttendanceCommand.EditAttendanceDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAttendanceDescriptor} with fields containing {@code attendance} details.
     * @param attendance
     */
    public EditAttendanceDescriptorBuilder(Attendance attendance) {
        descriptor = new EditAttendanceCommand.EditAttendanceDescriptor();
        descriptor.setParticipationScore(attendance.getParticipationScore());
    }

    /**
     * Sets the participation score of the {@code EditAttendanceDescriptor} that we are building.
     */
    public EditAttendanceDescriptorBuilder withParticipationScore(int participationScore) {
        descriptor.setParticipationScore(participationScore);
        return this;
    }

    public EditAttendanceCommand.EditAttendanceDescriptor build() {
        return descriptor;
    }
}
