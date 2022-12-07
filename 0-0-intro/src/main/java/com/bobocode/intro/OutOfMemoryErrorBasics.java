package com.bobocode.intro;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryErrorBasics {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        while (true) {
            list.add("string");
        }
    }

}
