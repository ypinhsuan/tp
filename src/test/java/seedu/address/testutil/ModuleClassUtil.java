package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.EditModuleClassCommand.EditModuleClassDescriptor;

/**
 * A utility class for Student.
 */
public class ModuleClassUtil {

    /**
     * Returns the part of command string for the given {@code EditModuleClassDescriptor}'s details.
     */
    public static String getEditModuleClassDescriptorDetails(EditModuleClassDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName));
        return sb.toString();
    }
}
