import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File formulario = new File("C:\\Users\\bruno\\IdeaProjects\\registration-system\\src\\files\\forms\\formulario.txt");
        User user = new User();
        Scanner sc = new Scanner(System.in);
        FileManager.toManage(formulario, user, sc);
    }
}
