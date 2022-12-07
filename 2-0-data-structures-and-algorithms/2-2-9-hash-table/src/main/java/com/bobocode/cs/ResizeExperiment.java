package com.bobocode.cs;

public class ResizeExperiment {
    public static void main(String[] args) {
        HashTable<String, Integer> secondTable = new HashTable<>();
        secondTable.put("NumberOne", 1);
        secondTable.put("NumberTwo", 2);
        secondTable.put("NumberThree", 3);

        System.out.println(secondTable);
        secondTable.resizeTable(15);
        System.out.println(secondTable);
    }
}
