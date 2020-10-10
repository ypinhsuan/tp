package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

/**
 * Represents a summary of currently recorded {@code Commands}.
 */
public class StateRecords {

    private List<String> stateRecords;
    private int currentIndex;

    /**
     * Creates a {@code StateRecords} with the specified {@code currentIndex} and {@code stateRecords}
     */
    public StateRecords(int currentIndex, List<String> stateRecords) {
        requireAllNonNull(currentIndex, stateRecords);

        this.stateRecords = stateRecords;
        this.currentIndex = currentIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public List<String> getStateRecords() {
        return stateRecords;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof StateRecords
                && ((StateRecords) other).getStateRecords().equals(getStateRecords())
                && ((StateRecords) other).getCurrentIndex() == getCurrentIndex());
    }
}
