package seedu.address.testutil;

import seedu.address.logic.commands.EditAttendanceCommand.EditAttendanceDescriptor;

public class EditAttendanceDescriptorBuilder {

    private EditAttendanceDescriptor descriptor;

    public EditAttendanceDescriptorBuilder() {
        descriptor = new EditAttendanceDescriptor();
    }

    public EditAttendanceDescriptorBuilder(EditAttendanceDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Returns an {@code EditAttendanceDescriptor} with fields containing {@code attendance} details.
     * @param participationScore
     */
    public EditAttendanceDescriptorBuilder(int participationScore) {
        descriptor = new EditAttendanceDescriptor();
        descriptor.setParticipationScore(participationScore);
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
