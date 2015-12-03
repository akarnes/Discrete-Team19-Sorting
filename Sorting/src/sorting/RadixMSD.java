package sorting;

import java.util.LinkedList;
import java.util.Queue;

public class RadixMSD implements SortingAlgorithm {

    public String getName() {
        return "Radix (MSD)";
    }

    public void run(DataAccessor arr) {
        int[] array = new int[arr.size()];
        boolean[][] b = toBinaryArray(arr);
        b = sort(0, b.length - 1, 0, b, 0);
        array = toIntArray(b);
        
        for(int i = 0; i < arr.size(); i++)
        {
            arr.set(i,array[i]);
        }
        arr.done();
    }
    
    public static boolean[][] sort(int start, int end, int digit, boolean[][] list, int recursion) {
        int first = start;
        int last = end;
        if (digit == list[0].length) {
            return list;
        }
        while (start < end) {
            while (start <= last && !list[start][digit]) {
                start++;
            }
            while (end >= first && list[end][digit]) {
                end--;
            }
            if (start < end && list[start][digit] && !list[end][digit]) {
                System.out.println(digit + " " + start + " " + end);
                boolean[] swap = list[start];
                list[start] = list[end];
                list[end] = swap;
                start++;
                end--;
            }
        }
        digit++;
        recursion++;
        list = sort(first, end, digit, list, recursion);
        list = sort(start, last, digit, list, recursion);
        recursion--;
        return list;
    }

    public static boolean[][] toBinaryArray(DataAccessor data) {
        boolean[][] b = new boolean[data.size()][8];
        String pass;
        char[] move;
        for (int i = 0; i < data.size(); i++) {
            pass = Integer.toBinaryString(data.get(i));
            while (pass.length() < 8) {
                pass = "0" + pass;
            }
            move = pass.toCharArray();
            for (int j = 0; j < move.length; j++) {
                b[i][j] = true;
                if (move[j] == '0') {
                    b[i][j] = false;
                }
            }
        }
        return b;
    }

    public static int[] toIntArray(boolean[][] a) {
        int[] b = new int[a.length];
        String transfer;
        for (int i = 0; i < a.length; i++) {
            transfer = "";
            for (int j = 0; j < 8; j++) {
                if (a[i][j]) {
                    transfer = transfer + "1";
                } else {
                    transfer = transfer + "0";
                }
            }
            b[i] = Integer.parseInt(transfer, 2);
        }
        return b;
    }
}
