import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cmd {
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder processBuilder =
                new ProcessBuilder("cmd", "/c","dir");
        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream,"cp866"));

        String line;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
        }

        int exitCode = process.waitFor();
    }
}
