package sorting;

public class GnomeSort implements SortingAlgorithm {

    boolean sorted = false;

    public String getName() {
        return "Gnome Sort";
    }

    public void run(DataAccessor data) {
        int i = 1;
        int j = 2;

        while (i < data.size()) {
            if (data.get(i - 1) <= data.get(i)) {
                i = j;
                j++;
            } else {
                int tmp = data.get(i - 1);
                data.set(i - 1,data.get(i));
                data.set(i--,tmp);
                i = (i == 0) ? j++ : i;
            }
        }
        data.done();
    }

}
