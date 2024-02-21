import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {

    private static final Path formulario = Paths.get("C:\\Users\\bruno\\IdeaProjects\\registration-system\\src\\files\\forms\\formulario.txt");
    private static final Path usersDirectory = Paths.get("C:\\Users\\bruno\\IdeaProjects\\registration-system\\src\\files\\users");

    private static void createUser() {
        User user = new User();
        Scanner sc = new Scanner(System.in);
        List<String> listaRespostas = new ArrayList<String>();
        try (BufferedReader br = Files.newBufferedReader(formulario)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
                String resposta = sc.nextLine();
                listaRespostas.add(resposta);
            }
            FileManager.configureUser(user, listaRespostas);
            FileManager.createFile(formulario, user);
            System.out.println(user.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listUsers() {
        int counter = 1;

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(usersDirectory)) {
            for (Path filePath : directoryStream) {
                if (Files.isRegularFile(filePath)) {
                    String firstLine = readFirstLine(filePath);
                    System.out.println(counter + " - " + firstLine);
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findByName(String name){
        try {
            Files.walk(Paths.get(usersDirectory.toString()))
                    .filter(Files::isRegularFile)
                    .forEach(arquivo -> {
                        try (BufferedReader br = Files.newBufferedReader(arquivo)) {
                            String linha;
                            while ((linha = br.readLine()) != null) {
                                if (linha.contains(name)) {
                                    System.out.println(linha);
                                    System.out.println();
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFirstLine(Path filePath) {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void registerQuestion(String novaLinha) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(formulario.toString(), true)); BufferedReader br = Files.newBufferedReader(formulario)) {
            String linha;
            bw.write(novaLinha);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteQuestion(int index) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(formulario)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (index <= 4 || index > linhas.size()) {
            System.out.println("Pergunta Inválida");
            return;
        }
        linhas.remove(index - 1);

        try (BufferedWriter bw = Files.newBufferedWriter(formulario)) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Linha deletada com sucesso");
    }

    public static void selectOption() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecione a opção desejada:");
        System.out.println("1 - Cadastrar o usuário");
        System.out.println("2 - Listar todos os usuários cadastrados");
        System.out.println("3 - Cadastrar nova pergunta no formulário");
        System.out.println("4 - Deletar pergunta no formulário");
        System.out.println("5 - Pesquisar usuário por nome");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                createUser();
                break;
            case 2:
                listUsers();
                break;
            case 3:
                sc.nextLine();
                System.out.println("Digite a pergunta:");
                String question = sc.nextLine();
                registerQuestion(question);
                break;
            case 4:
                System.out.println("Digite o número da questão:");
                int index = sc.nextInt();
                deleteQuestion(index);
                break;
            case 5:
                sc.nextLine();
                System.out.println("Digite o nome que deseja buscar:");
                String name = sc.nextLine();
                findByName(name);
                break;
        }
    }
}
