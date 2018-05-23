package ProcessSchedule;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class NumericHandlers {
    
    public static double[] randomArray(int size, double min, double max) {
        double[] array = new double[size];
        
        for (int i = 0; i < array.length; i++) {
            array[i] = round(ThreadLocalRandom.current().nextDouble(min, max), 2);
        }
        return array;
    }
    
    public static int[] randomUniqueArray(int min, int max) {
        int[] oneToN = IntStream.range(min, max + 1).toArray();
        
        shuffleArray(oneToN);
        return oneToN;
    }                    
    
    public static String removeExcessDecimalPoint(String str) {
        if (!str.contains(".")) {
            return str;
        }
        int beginIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                beginIndex = i;
                break;
            }
        }
        int endIndex = beginIndex;
        boolean decimalPointFound = false;
        for (int i = beginIndex + 1; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                if (!decimalPointFound) {
                    decimalPointFound = true;
                } else {
                    endIndex = i + 1;
                    break;
                }
            } else {
                endIndex = i + 1;
            }
        }
        return str.substring(beginIndex, endIndex);
    }                

    public static double round(double d, int decimalPlaces) {
        return Math.round(d * Math.pow(10, decimalPlaces)) / 100.0;
    }
    
    // Fisher-Yates
    public static void shuffleArray(int[] array) {        
        Random rand = new Random();
        int index, temp;
        
        for (int i = array.length - 1; i > 0; i--) {
            index = rand.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
