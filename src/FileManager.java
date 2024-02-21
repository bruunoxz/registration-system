import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {
    private static final String CONTADOR_ARQUIVOS_PATH = "C:\\Users\\bruno\\IdeaProjects\\registration-system\\src\\files\\forms\\contador.txt";

    public static void createFile(Path formulario, User user) {
        int fileCounter = readFileCounter() ;
        String userName = user.getName();
        String fileName = (fileCounter + 1) + "-" + userName.replaceAll("\\s", "").toUpperCase();
        Path newPath = Paths.get("C:\\Users\\bruno\\IdeaProjects\\registration-system\\src\\files\\users\\" + fileName + ".txt");
        try (BufferedWriter bw = Files.newBufferedWriter(newPath)) {
            bw.write(user.getName());
            bw.write("\n"+user.getEmail());
            bw.write("\n"+user.getAge());
            bw.write("\n"+user.getHeight());

            fileCounter++;
            writeFileCounter(fileCounter);
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

    private static int readFileCounter() {
        try (BufferedReader br = new BufferedReader(new FileReader(CONTADOR_ARQUIVOS_PATH))) {
            String linha = br.readLine();
            if (linha != null && !linha.isEmpty()) {
                return Integer.parseInt(linha);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static void writeFileCounter(int contador) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONTADOR_ARQUIVOS_PATH))) {
            bw.write(Integer.toString(contador));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
