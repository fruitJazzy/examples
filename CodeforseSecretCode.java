package general;


import general.general.file.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class Codeforces {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int pow = Integer.parseInt(reader.readLine());
        int inputNumber = Integer.parseInt(reader.readLine());

        int[] arrayNumber = makeArray(inputNumber, pow);

        int minimum = getNumber(arrayNumber);

        for (int i = 0; i < 10; i++) {
            swapFirstLast(arrayNumber);
            minimum = equalMin(getNumber(arrayNumber),minimum);
            addOne(arrayNumber);
            minimum = equalMin(getNumber(arrayNumber),minimum);
        }

        System.out.println(minimum);
    }

    public static int[] makeArray(int number,int pow){
        int[] box = new int[pow];
        for(int i = 10, j = pow - 1; i < Math.pow(number,pow); j--){
            box[j] =  number % i;
            number /= 10;
        }
        return box;
    }

    public static int getNumber(int[] array){
        int fullnumber = 0;
        for (int i = 0; i < array.length; i++) {
            fullnumber += array[i] * Math.pow(10,array.length - i - 1);
        }
        return fullnumber;
    }

    public static void addOne(int[] array){
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 9)
                array[i] = 0;
            else
                array[i]++;
        }
    }

    public static void swapFirstLast(int[] array){
        array[0] = array[0] + array[array.length - 1];
        array[array.length - 1] = array[0] - array[array.length - 1];
        array[0] = array[0] - array[array.length - 1];
    }

    public static int equalMin(int fistNum, int secondNum){
        if (fistNum < secondNum)
            return fistNum;
        else
            return secondNum;
    }
}
