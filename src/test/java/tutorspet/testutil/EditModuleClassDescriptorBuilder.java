package tutorspet.testutil;

import tutorspet.logic.commands.EditModuleClassCommand;
import tutorspet.model.components.name.Name;
import tutorspet.model.moduleclass.ModuleClass;

/**
 * A utility class to help with building EditModuleClassDescriptor objects.
 */
public class EditModuleClassDescriptorBuilder {

    private EditModuleClassCommand.EditModuleClassDescriptor descriptor;

    public EditModuleClassDescriptorBuilder() {
        descriptor = new EditModuleClassCommand.EditModuleClassDescriptor();
    }

    public EditModuleClassDescriptorBuilder(EditModuleClassCommand.EditModuleClassDescriptor descriptor) {
        this.descriptor = new EditModuleClassCommand.EditModuleClassDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleClassDescriptor} with fields containing {@code moduleClass}'s details.
     */
    public EditModuleClassDescriptorBuilder(ModuleClass moduleClass) {
        descriptor = new EditModuleClassCommand.EditModuleClassDescriptor();
        descriptor.setName(moduleClass.getName());
    }

    /**
     * Sets the {@code Name} of the {@code EditModuleClassDescriptor} that we are building.
     */
    public EditModuleClassDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditModuleClassCommand.EditModuleClassDescriptor build() {
        return descriptor;
    }
}
