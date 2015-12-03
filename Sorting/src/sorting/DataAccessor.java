package sorting;

import java.util.ArrayList;

/**
 * Wraps around an array of data points. Algorithms should use this object to
 * access the sortable data so that metrics can be stored on how it does its
 * job.
 */
public class DataAccessor {

    public int setTotal = 0;
    public int accessTotal = 0;

    /**
     * Enumerated constants representing event types.
     */
    public enum ActionType {
        GET, SET, DONE
    }

    /**
     * Represents a single interaction between an algorithm and the data. This
     * is used to keep track of an algorithm's behavior.
     */
    public class Action {

        public ActionType type;
        public int index;
        public int index2;
        public int data;

        public Action(ActionType type, int index, int index2, int data) {
            this.type = type;
            this.index = index;
            this.index2 = index2;
            this.data = data;
        }
    }

    private int[] data;
    private ArrayList<Action> actions;
    private int eventCursor;

    /**
     * Creates a new DataAccessor using the given data. The data is copied, not
     * used directly.
     */
    public DataAccessor(int[] data) {
        int[] copy = new int[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        this.data = copy;
        this.actions = new ArrayList<>();
    }

    /**
     * Returns the data at the given index.
     */
    public int get(int i) {
        actions.add(new Action(ActionType.GET, i, 0, 0));
        accessTotal++;
        return data[i];
    }

    /**
     * Sets the data at the given index to the given value.
     */
    public void set(int i, int val) {
        actions.add(new Action(ActionType.SET, i, 0, val));
        setTotal++;
        data[i] = val;
    }

    /**
     * Swaps the data contained in the given indexes with each other. This is
     * just a convenience function. It is equivalent to a get and two sets.
     */
    public void swap(int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
        actions.add(new Action(ActionType.GET, i, 0, 0));
        actions.add(new Action(ActionType.SET, i, 0, data[i]));
        actions.add(new Action(ActionType.SET, j, 0, data[j]));
    }

    /**
     * Returns the amount of data elements.
     */
    public int size() {
        return data.length;
    }

    /**
     * Claim that the sorting is finished.
     */
    public void done() {
        actions.add(new Action(ActionType.DONE, 0, 0, 0));
    }

    /**
     * Returns the next step the algorithm took. This method is not to be used
     * by algorithms themselves.
     */
    public Action nextAction() {
        if (eventCursor >= actions.size()) {
            return null;
        }
        Action action = actions.get(eventCursor);
        eventCursor++;
        return action;
    }

    /**
     * Resets the algorithm action read head.
     */
    public void reset() {
        eventCursor = 0;
    }

    public int getAccess() {
        return accessTotal;
    }
    
    public int getSet() {
        return setTotal;
    }
}
