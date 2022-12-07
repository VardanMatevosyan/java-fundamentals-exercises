package com.bobocode.cs;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {
    public static void main(String[] args) {
//        int[] array = new int[]{8, 5, 7, 3, 7};
//        System.out.println("Array size " + array.length);
//        System.out.println("Before sorting" + Arrays.toString(array));
//        System.out.println();

        for (int i = 1000; i < 1000000; i = i + 1000) {
            int[] array = getArray(i);
//            System.out.println("before " + Arrays.toString(array));
            long startMillis = System.currentTimeMillis();
            merge(array);
            System.out.println(array.length + " " + (System.currentTimeMillis() - startMillis));
//            System.out.println("after " + Arrays.toString(array));
        }
//        System.out.println("After sorting" + Arrays.toString(array));
    }

    private static int[] getArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for(int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    private static void merge(int[] array) {
        if (array.length <= 1) {
            return;
        }
        int halfIndex = array.length / 2;
        int[] leftHalf = new int[halfIndex];
        int[] rightHalf = new int[array.length - halfIndex];
        fillLeftHalfArray(array, halfIndex, leftHalf);
        fillRightHalfArray(array, halfIndex, rightHalf);
        merge(leftHalf);
        merge(rightHalf);
        mergeArrays(array, leftHalf, rightHalf);
    }

    private static void fillLeftHalfArray(int[] array, int halfIndex, int[] leftHalf) {
        System.arraycopy(array, 0, leftHalf, 0, halfIndex);
    }

    private static void fillRightHalfArray(int[] array, int halfIndex, int[] rightHalf) {
        System.arraycopy(array, halfIndex, rightHalf, 0, array.length - halfIndex);
    }
    private static void mergeArrays(int[] array, int[] leftHalf, int[] rightHalf) {
        int leftHalfArraySize = leftHalf.length, rightHalfArraySize = rightHalf.length;
        int leftIndex = 0, rightIndex = 0, resultIndex = 0;

//        System.out.println("Before merging leftHalf - " + Arrays.toString(leftHalf));
//        System.out.println("Before merging rightHalf - " + Arrays.toString(rightHalf));

        while (leftIndex < leftHalfArraySize && rightIndex < rightHalfArraySize) {
            if (leftHalf[leftIndex] <= rightHalf[rightIndex]) {
                array[resultIndex] = leftHalf[leftIndex];
                leftIndex++;
            } else {
                array[resultIndex] = rightHalf[rightIndex];
                rightIndex++;
            }
            resultIndex++;
        }
        while (leftIndex < leftHalfArraySize) {
            array[resultIndex] = leftHalf[leftIndex];
            leftIndex++;
            resultIndex++;
        }
        while (rightIndex < rightHalfArraySize) {
            array[resultIndex] = rightHalf[rightIndex];
            rightIndex++;
            resultIndex++;
        }
//        System.out.println("After each step " + Arrays.toString(array));
//        System.out.println();
    }
}
