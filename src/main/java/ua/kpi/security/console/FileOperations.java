package ua.kpi.security.console;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class FileOperations {
    private static FileOperations fileOperations;
    private String fileName;

    private FileOperations() {
    }

    public static FileOperations getInstance() {
        if (fileOperations == null)
            fileOperations = new FileOperations();
        return fileOperations;
    }

    public void setFileName(String name) {
        fileName = name;
    }

    public String getFileName() {
        return fileName;
    }

    public static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && !file.isDirectory();
    }

    public boolean createFile() throws IOException {
        return new File(fileName).createNewFile();
    }

    public String getText() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine()).append(System.lineSeparator());
        }
        scanner.close();
        return sb.toString().trim();
    }

    public void editFile(String text, boolean append) throws IOException {
        if (text != null) {
            FileWriter fileWriter = new FileWriter(fileName, append);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(text);
            printWriter.close();
        }
    }

    public boolean editFile(String text, boolean append, boolean isActivated) throws IOException {
        if (!isActivated && text != null) {
            return false;
        }
        if (text != null) {
            FileWriter fileWriter = new FileWriter(fileName, append);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(text);
            printWriter.close();
        }
        return true;
    }

    public void clearFile() throws IOException {
        Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.TRUNCATE_EXISTING);
    }

    public String[] getLines() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine())
            lines.add(scanner.nextLine());
        scanner.close();
        return lines.toArray(new String[0]);
    }

    public void renameFile(String newName) throws IOException {
        Path source = Paths.get(fileName);
        Files.move(source, source.resolveSibling(newName));
        fileName = newName;
    }

    public void copyFile(String copyName) throws IOException {
        Files.copy(Paths.get(fileName), Paths.get(copyName), StandardCopyOption.REPLACE_EXISTING);
    }

    public BasicFileAttributes getFileParameters() throws IOException {
        return Files.readAttributes(Paths.get(fileName), BasicFileAttributes.class);
    }

    public boolean deleteFile() {
        boolean deleted = new File(fileName).delete();
        fileName = null;
        return deleted;
    }

    public boolean isBase64Encoded() throws FileNotFoundException {
        try {
            decode();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void decodeFile(String encodedFileName) throws IOException {
        String decodedText = decode();
        fileName = encodedFileName;
        createFile();
        editFile(decodedText, false);
    }

    public void encodeFile(String decodedFileName) throws IOException {
        String encodedText = encode();
        String backToFile = fileName;
        fileName = decodedFileName;
        createFile();
        editFile(encodedText, false);
        fileName = backToFile;
    }

    private String decode() throws FileNotFoundException {
        return new String(Base64.getDecoder().decode(getText()));
    }

    private String encode() throws FileNotFoundException {
        return Base64.getEncoder().encodeToString(getText().getBytes());
    }
}
