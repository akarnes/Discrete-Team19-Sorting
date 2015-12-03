package sorting;

public class CocktailSort implements SortingAlgorithm {

    public String getName() {
        return "Cocktail Sort";
    }

    public void run(DataAccessor data) {
        //shaker is the array
        //if the array is sorted
        boolean cocktail = true;
        
        int loc = 0;
        //continue till sorted
        while (cocktail) {
            //set to false, until true
            cocktail = false;
            //goes through array one way to sort
            //drunk is 'i'
            for (int drunk = loc; drunk <= data.size() - loc - 2; drunk++) {
                //if it has found where it is more than the next one, it switches it
                if (data.get(drunk) > data.get(drunk + 1)) {
                    //switches and returns true
                    //wasted is the temp
                    int wasted = data.get(drunk);
                    data.set(drunk, data.get(drunk + 1));
                    data.set(drunk + 1, wasted);
                    cocktail = true;
                }
            }
            //breaks if it is never true
            if (!cocktail) {
                break;
            }
            //set to false (again), until true
            cocktail = false;
            //goes through other way to sort
            //twerk is 'i'
            for (int twerk = data.size() - loc - 2; twerk >= loc; twerk--) {
                //if it has found where it is more than the next one, it switches it
                if (data.get(twerk) > data.get(twerk + 1)) {
                    //switches and returns true
                    //club is temp
                    int club = data.get(twerk);
                    data.set(twerk, data.get(twerk + 1));
                    data.set(twerk + 1, club);
                    cocktail = true;
                }
            }
            loc++;
        }

        data.done();
    }

}
