package sorting;

public class placeHolder
{

}
//package sorting;
//
//public class placeHolder {
//
//    public static void main(String[] args) {
//        int[] a = {10, 10, 16, 125, 61, 68, 18, 69, 71, 12};
//        boolean[][] b = toBinaryArray(a);
//        b = sort(0, b.length - 1, 0, b, 0);
//        printIntArray(toIntArray(b));
//    }
//
//    public static boolean[][] sort(int start, int end, int digit, boolean[][] list, int recursion) {
//        int first = start;
//        int last = end;
//        if (digit == list[0].length) {
//            return list;
//        }
//        while (start < end) {
//            while (start <= last && !list[start][digit]) {
//                start++;
//            }
//            while (end >= first && list[end][digit]) {
//                end--;
//            }
//            if (start < end && list[start][digit] && !list[end][digit]) {
//                System.out.println(digit + " " + start + " " + end);
//                boolean[] swap = list[start];
//                list[start] = list[end];
//                list[end] = swap;
//                start++;
//                end--;
//            }
//        }
//        digit++;
//        recursion++;
//        list = sort(first, end, digit, list, recursion);
//        list = sort(start, last, digit, list, recursion);
//        recursion--;
//        return list;
//    }
//
//    public static void printBoolArray(boolean[][] a) {
//        for (int j = 0; j < a[0].length; j++) {
//            for (int i = 0; i < a.length; i++) {
//                System.out.print((a[i][j] ? "1 " : "0 "));
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//
//    public static void printIntArray(int[] a) {
//        for (int i : a) {
//            System.out.print(i + " ");
//        }
//        System.out.println();
//    }
//
//    public static boolean[][] toBinaryArray(int[] a) {
//        boolean[][] b = new boolean[a.length][8];
//        String pass;
//        char[] move;
//        for (int i = 0; i < a.length; i++) {
//            pass = Integer.toBinaryString(a[i]);
//            while (pass.length() < 8) {
//                pass = "0" + pass;
//            }
//            move = pass.toCharArray();
//            for (int j = 0; j < move.length; j++) {
//                b[i][j] = true;
//                if (move[j] == '0') {
//                    b[i][j] = false;
//                }
//            }
//        }
//        return b;
//    }
//
//    public static int[] toIntArray(boolean[][] a) {
//        int[] b = new int[a.length];
//        String transfer;
//        for (int i = 0; i < a.length; i++) {
//            transfer = "";
//            for (int j = 0; j < 8; j++) {
//                if (a[i][j]) {
//                    transfer = transfer + "1";
//                } else {
//                    transfer = transfer + "0";
//                }
//            }
//            b[i] = Integer.parseInt(transfer, 2);
//        }
//        return b;
//    }
//}
