package general.cojo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AgeNPeople {

   static int[] age = new int[150];

    public static void main(String[] args) throws IOException {

        System.out.println("Введите колличество людей! ");
        int countPeople = getAge();

        System.out.println("Введите возраст каждлого человека ");
        for (int i = 0; i < countPeople; i++) {
            age[getAge()] = 1;
        }

        int countPeopleInDec= 0;
        int countDec = 0;

        for (int i = 0; i < age.length; i++) {
            if (i % 10 == 0){
                countDec++;
                if(countPeopleInDec != 0)
                    System.out.println(countPeopleInDec + " человек имеет возраст от " + (i - 10) + " до " + i);
                countPeopleInDec = 0;
            }
            if (age[i] != 0) {
                countPeopleInDec++;
            }
        }
    }

    public static int getAge() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int count = Integer.parseInt(reader.readLine());

        try {
            if (count <= 0)
                throw new IOException();
        }
        catch(IOException e){
            System.out.println("<0");
        }
        return count;
    }


    public static void showAge() {
        for (int i = 0; i < age.length; i++ ){
            if (age[i] != 0)
                System.out.println(age[i] + " i - " + i);
        }
    }
}
