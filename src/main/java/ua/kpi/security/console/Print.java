package ua.kpi.security.console;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Print {
    private static Print print;

    private final Scanner in = new Scanner(System.in);

    public static Print getInstance() {
        if (print == null)
            print = new Print();
        return print;
    }

    public void error(Exception e) {
        System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
    }

    public int menu() {
        separator();
        System.out.println(Colors.VERTICAL_BAR + "    1. Create new file     2. Open a file     " + Colors.VERTICAL_BAR);
        System.out.println(Colors.VERTICAL_BAR + "                    " + Colors.RED + "0. Exit" + Colors.RESET + "                   " + Colors.VERTICAL_BAR);

        separator();
        System.out.println("Selection: ");
        return getPositiveInteger(2);
    }

    public int menu(String filename, boolean isActivated) {
        separator();
        int wsNum = 46 - filename.length();
        if (!isActivated)
            wsNum -= 7;
        System.out.print(Colors.VERTICAL_BAR);
        for (int i = 0; i < wsNum / 2; i++)
            System.out.print(" ");
        System.out.print(Colors.PURPLE + filename + Colors.RESET);
        if (!isActivated)
            System.out.print(Colors.RED + " (DEMO)" + Colors.RESET);
        for (int i = 0; i < wsNum / 2; i++)
            System.out.print(" ");
        if (wsNum % 2 != 0)
            System.out.print(" ");
        System.out.println(Colors.VERTICAL_BAR);
        separator();
        System.out.println(Colors.VERTICAL_BAR + "   1. Print            8. Copy                "
                + Colors.VERTICAL_BAR);
        System.out.println(Colors.VERTICAL_BAR + "   2. Add              9. File parameters     "
                + Colors.VERTICAL_BAR);
        System.out.println(Colors.VERTICAL_BAR + "   3. Rewrite          10. Encode / Decode    " + Colors.VERTICAL_BAR);
        System.out.println(Colors.VERTICAL_BAR + "   4. Clear            11. Delete             " + Colors.VERTICAL_BAR);
        System.out.println(Colors.VERTICAL_BAR + "   5. Find all         12. Close (Back)       " + Colors.VERTICAL_BAR);
        System.out.print  (Colors.VERTICAL_BAR + "   6. Replace all      " + Colors.PURPLE + "13. ");
        if (isActivated)
            System.out.print("Log out            ");
        else
            System.out.print("Enter key          ");
        System.out.println(Colors.RESET + Colors.VERTICAL_BAR);
        System.out.println(Colors.VERTICAL_BAR + "   7. Rename           " +
                Colors.RED + "0. Exit" + Colors.RESET + "                " + Colors.VERTICAL_BAR);
        separator();
        System.out.println("Selection: ");
        return getPositiveInteger(14);
    }

    public void separator() {
        System.out.println(Colors.SEPARATOR);
    }

    public int getPositiveInteger(int quantity) {
        while (true) {
            try {
                int value = in.nextInt();
                if (0 > value || value > quantity) {
                    System.out.println("The number must be in range from 0 to " + quantity + ". Enter correct value: ");
                    continue;
                }
                in.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Enter positive number: ");
                in.nextLine();
            }
        }
    }

    public String getFileName() {
        System.out.println("Enter file name: ");
        return in.nextLine();
    }

    public boolean create(boolean created, String fileName) {
        if (created)
            System.out.println("File created: " + Colors.PURPLE + fileName + Colors.RESET);
        else
            System.out.println("File already exists.");
        return created;
    }

    public void printText(String text) {
        if (text.isEmpty())
            System.out.println(Colors.PURPLE + "File is empty." + Colors.RESET);
        else
            System.out.println(text);
    }

    public String getUserText() {
        System.out.println("If you want to save the file enter :s");
        System.out.println("If you want to leave without saving enter :q");
        String inputLine;
        StringBuilder sb = new StringBuilder();
        do {
            inputLine = in.nextLine();
            sb.append(inputLine).append(System.lineSeparator());
        } while (!inputLine.equals(":s") && !inputLine.equals(":q"));
        if (inputLine.equals(":s")) {
            if (sb.length() == 0)
                return "";
            return (System.lineSeparator() + sb.substring(0, sb.lastIndexOf(System.lineSeparator() + ":")));
        }
        return null;
    }

    public void fileDoesNotExist() {
        System.out.println("File does not exist.");
    }

    public void clearFile() {
        System.out.println(Colors.PURPLE + "File was cleared." + Colors.RESET);
    }

    public void findUsages(String[] lines) {
        System.out.println("Enter characters you want to find:");
        String searchWord = in.nextLine();
        for (String line : lines) {
            if (line.contains(searchWord)) {
                String[] words = line.split(" ");
                for (String word : words) {
                    if (word.equals(searchWord))
                        System.out.print(Colors.CYAN_BACKGROUND + word + Colors.RESET);
                    else
                        System.out.print(word);
                    System.out.print(" ");
                }
                System.out.println();
            } else {
                System.out.println(line);
            }
        }
    }

    public String[] getReplace() {
        System.out.println("Enter characters you want to replace:");
        String text = in.nextLine();
        System.out.println("Enter characters to which you want to replace:");
        String replacement = in.nextLine();
        return new String[]{text, replacement};
    }

    public String getNewName() {
        System.out.println("Enter new name: ");
        return in.nextLine();
    }

    public String getEncodedDecodedName(boolean isEncoded) {
        System.out.print("Enter name of file where you want ");
        if (isEncoded)
            System.out.print("decoded");
        else
            System.out.print("encoded");
        System.out.println(" text to be placed: ");
        return in.nextLine();
    }

    public void parameters(BasicFileAttributes attr) {
        System.out.println("Creation Time: " + attr.creationTime());
        System.out.println("Last Access Time: " + attr.lastAccessTime());
        System.out.println("Last Modified Time: " + attr.lastModifiedTime());
        System.out.println("Is Directory: " + attr.isDirectory());
        System.out.println("Is Other: " + attr.isOther());
        System.out.println("Is Regular File: " + attr.isRegularFile());
        System.out.println("Is Symbolic Link: " + attr.isSymbolicLink());
        System.out.println("Size: " + attr.size() + " bytes");
    }

    public void delete(boolean deleted) {
        if (deleted)
            System.out.println("File was deleted.");
        else
            System.out.println("Failed to delete the file.");
    }

    public String getPassword() {
        System.out.println("You use demo version of the program.");
        System.out.println("Enter the key to unlock all functionality: ");
        return in.nextLine();
    }

    public void incorrectKey() {
        System.out.println(Colors.RED + "This key is not correct." + Colors.RESET);
    }

    public void correctKey() {
        System.out.println(Colors.PURPLE + "Key was registered successfully." + Colors.RESET);
    }

    public void demoAlert() {
        System.out.println(Colors.RED + "Can't save file, because you use demo version of the program");
        System.out.println("To register the program choose option 13 and enter the key." + Colors.RESET);
    }

    public void captcha(String link) {
        System.out.println("Please visit " + Colors.PURPLE + link + Colors.RESET + " and pass the CAPTCHA.");
    }
}
