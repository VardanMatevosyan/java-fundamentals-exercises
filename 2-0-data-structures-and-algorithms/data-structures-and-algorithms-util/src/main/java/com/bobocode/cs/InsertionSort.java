package com.bobocode.cs;

import java.util.Arrays;
import java.util.Random;

public class InsertionSort {

    public static void main(String[] args) {
        int[] array = new int[]{6, 4, 3, 6, 3, 6, 7};
        System.out.println("before " + Arrays.toString(array));
        insertingSort(array);
        System.out.println("after " + Arrays.toString(array));
    }

    private static void insertingSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int currentElement = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > currentElement) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = currentElement;
        }
    }



    private static int[] getArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }


    // todo later
//
//    public static void main(String[] args) {
//        String[] input = new String[]{"1", "2", "3", "x", "5", "6", "a", "porosiatko", "c", "10", "11", "12", "13", "14", "15", "16"};
//        int index = 1;
//        for (int i = 0; i < input.length; i++) {
//
//            System.out.print(input[i] + "    ");
//            index++;
//            if (index > 5 || i == input.length - 1) {
//                System.out.println();
//                index = 1;
//            }
//        }
//    }
}
