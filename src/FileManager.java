import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    private static final String CONTADOR_ARQUIVOS_PATH = "C:\\Users\\bruno\\IdeaProjects\\registration-system\\src\\files\\forms\\contador.txt";

    public static void toManage(File formulario, User user, Scanner sc) {
        List<String> listaRespostas = new ArrayList<String>();
        try (FileReader fr = new FileReader(formulario); BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
                String resposta = sc.nextLine();
                listaRespostas.add(resposta);
            }
            configureUser(user, listaRespostas);
            createFile(formulario, user);
            System.out.println(user.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(File formulario, User user) {
        int fileCounter = lerContadorArquivos();
        String userName = user.getName();
        String fileName = (fileCounter + 1) + "-" + userName.replaceAll("\\s", "").toUpperCase();
        File newFile = new File("C:\\Users\\bruno\\IdeaProjects\\registration-system\\src\\files\\users\\" + fileName + ".txt");
        try (FileWriter fw = new FileWriter(newFile)) {
            fw.write("1 - " + user.getName());
            fw.write("\n2 - " + user.getEmail());
            fw.write("\n3 - " + user.getAge());
            fw.write("\n4 - " + user.getHeight());

            fileCounter++;
            escreverContadorArquivos(fileCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void configureUser(User user, List<String> respostas) {
        if (respostas.size() >= 4) {
            user.setName(respostas.get(0));
            user.setEmail(respostas.get(1));
            user.setAge(respostas.get(2));
            user.setHeight(respostas.get(3));
        } else {
            System.out.println("Não há respostas suficientes para configurar o usuário.");
        }
    }

    private static int lerContadorArquivos() {
        try (BufferedReader br = new BufferedReader(new FileReader(CONTADOR_ARQUIVOS_PATH))) {
            String linha = br.readLine();
            if (linha != null && !linha.isEmpty()) {
                return Integer.parseInt(linha);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0; // Valor padrão se não conseguir ler o contador
    }

    private static void escreverContadorArquivos(int contador) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONTADOR_ARQUIVOS_PATH))) {
            bw.write(Integer.toString(contador));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
