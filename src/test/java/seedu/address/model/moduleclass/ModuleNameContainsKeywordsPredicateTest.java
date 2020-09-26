package seedu.address.model.moduleclass;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2103T_TUTORIAL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ModuleClassBuilder;

public class ModuleNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ModuleNameContainsKeywordsPredicate firstPredicate =
                new ModuleNameContainsKeywordsPredicate(firstPredicateKeywordList);
        ModuleNameContainsKeywordsPredicate secondPredicate =
                new ModuleNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ModuleNameContainsKeywordsPredicate firstPredicateCopy =
                new ModuleNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different module class -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ModuleNameContainsKeywordsPredicate predicate =
                new ModuleNameContainsKeywordsPredicate(Collections.singletonList(
                "CS2103T"));
        assertTrue(predicate.test(new ModuleClassBuilder().withName(VALID_NAME_CS2103T_TUTORIAL).build()));

        // Multiple keywords
        predicate = new ModuleNameContainsKeywordsPredicate(Arrays.asList("CS2103T", "Tutorial"));
        assertTrue(predicate.test(new ModuleClassBuilder().withName(VALID_NAME_CS2103T_TUTORIAL).build()));

        // Only one matching keyword
        predicate = new ModuleNameContainsKeywordsPredicate(Arrays.asList("CS2100", "Tutorial"));
        assertTrue(predicate.test(new ModuleClassBuilder().withName(VALID_NAME_CS2103T_TUTORIAL).build()));

        // Mixed-case keywords
        predicate = new ModuleNameContainsKeywordsPredicate(Arrays.asList("Cs2103t", "TutORial"));
        assertTrue(predicate.test(new ModuleClassBuilder().withName(VALID_NAME_CS2103T_TUTORIAL).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ModuleNameContainsKeywordsPredicate predicate =
                new ModuleNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ModuleClassBuilder().withName(VALID_NAME_CS2103T_TUTORIAL).build()));

        // Non-matching keyword
        predicate = new ModuleNameContainsKeywordsPredicate(Arrays.asList("CS2100"));
        assertFalse(predicate.test(new ModuleClassBuilder().withName(VALID_NAME_CS2103T_TUTORIAL).build()));
    }
}
