package seedu.address.model.moduleclass;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ModuleClass}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordPredicate implements Predicate<ModuleClass> {
    private final List<String> keywords;

    public NameContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ModuleClass moduleClass) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(moduleClass.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
            || (other instanceof NameContainsKeywordPredicate //instanceof handles null)
            && keywords.equals(((NameContainsKeywordPredicate) other).keywords)); //state check
    }

}
