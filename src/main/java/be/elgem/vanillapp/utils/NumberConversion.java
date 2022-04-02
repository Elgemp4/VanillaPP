package be.elgem.vanillapp.utils;

import java.util.TreeMap;

public class NumberConversion {
    private static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(10000, "ↂ");
        map.put(9000, "Mↂ");
        map.put(5000, "ↁ");
        map.put(4000, "Mↁ");
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public static String convertIntegerToRoman(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        int floorValue;

        if(number!=0) {
            floorValue = map.floorKey(number);
        }
        else{
            floorValue = 0;
        }


        if(number==0){
            return stringBuilder.toString();
        }
        else{
            return stringBuilder.append(map.get(floorValue) + convertIntegerToRoman(number - floorValue)).toString();
        }
    }
}
