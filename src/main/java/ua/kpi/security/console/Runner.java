package ua.kpi.security.console;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Runner {

    private final static String KEY_FILE_NAME = "key.txt";
    private static Runner instance;

    private final FileOperations fileOperations;
    private final Print print;

    private static boolean isActivated;
    private volatile boolean isCaptchaPassed;
    private volatile String fileName;
    private volatile String fileText;

    private Runner() {
        fileOperations = FileOperations.getInstance();
        print = Print.getInstance();
    }

    public static Runner getInstance() {
        if (instance == null)
            instance = new Runner();
        return instance;
    }


    public void getMenu() {
        isActivated = false;
        do {
            switch (print.menu()) {
                case 1:
                    String fileName = print.getFileName();
                    try {
                        fileOperations.setFileName(fileName);
                        if (!print.create(fileOperations.createFile(), fileName))
                            continue;
                    } catch (IOException e) {
                        print.error(e);
                        fileOperations.setFileName(null);
                        break;
                    }
                    getFileMenu();
                    break;
                case 2:
                    String name = print.getFileName();
                    if (FileOperations.fileExists(name)) {
                        fileOperations.setFileName(name);
                    } else {
                        print.fileDoesNotExist();
                        continue;
                    }
                    getFileMenu();
                    break;
                case 0:
                    print.separator();
                    return;
            }
        } while (true);
    }

    private void getFileMenu() {
        boolean read = true;
        do {

            this.fileName = fileOperations.getFileName();
            try {
                this.fileText = fileOperations.getText();
            } catch (FileNotFoundException e) {
                this.fileText = Colors.RED + "file not found" + Colors.RESET;
            }
            int selection = print.menu(fileOperations.getFileName(), isActivated);
            switch (selection) {
                case 1:
                    try {
                        if (!isCaptchaPassed) {
                            print.captcha("http://localhost:8080/");
                            break;
                        }
                        print.printText(fileOperations.getText());
                    } catch (FileNotFoundException e) {
                        print.error(e);
                    }
                    break;
                case 2:
                    try {
                        if (!fileOperations.editFile(print.getUserText(), true, isActivated)) {
                            print.demoAlert();
                            break;
                        }
                    } catch (IOException e) {
                        print.error(e);
                    }
                    break;
                case 3:
                    try {
                        if (!fileOperations.editFile(print.getUserText(), false, isActivated)) {
                            print.demoAlert();
                            break;
                        }
                    } catch (IOException e) {
                        print.error(e);
                    }
                    break;
                case 4:
                    try {
                        fileOperations.clearFile();
                        print.clearFile();
                    } catch (IOException e) {
                        print.error(e);
                    }
                    break;
                case 5:
                    if (!isActivated) {
                        print.demoAlert();
                        break;
                    }
                    try {
                        print.findUsages(fileOperations.getLines());
                    } catch (FileNotFoundException e) {
                        print.error(e);
                    }
                    break;
                case 6:
                    try {
                        String[] getReplace = print.getReplace();
                        String replaced = fileOperations.getText().replaceAll(getReplace[0], getReplace[1]);
                        if (!fileOperations.editFile(replaced, false, isActivated)) {
                            print.demoAlert();
                            break;
                        }
                    } catch (IOException e) {
                        print.error(e);
                    }
                    break;
                case 7:
                    try {
                        fileOperations.renameFile(print.getNewName());
                    } catch (IOException e) {
                        print.error(e);
                    }
                    break;
                case 8:
                    try {
                        fileOperations.copyFile(print.getFileName());
                    } catch (IOException e) {
                        print.error(e);
                    }
                    break;
                case 9:
                    try {
                        print.parameters(fileOperations.getFileParameters());
                    } catch (IOException e) {
                        print.error(e);
                    }
                    break;
                case 10:
                    try {
                        boolean isEncoded = fileOperations.isBase64Encoded();
                        String outputFileName = print.getEncodedDecodedName(isEncoded);
                        if (isEncoded)
                            fileOperations.decodeFile(outputFileName);
                        else
                            fileOperations.encodeFile(outputFileName);
                    } catch (IOException e) {
                        print.error(e);
                    }
                    break;
                case 11:
                    print.delete(fileOperations.deleteFile());
                    return;
                case 12:
                    return;
                case 13:
                    if (isActivated) {
                        isActivated = false;
                    } else {
                        try {
                            activate();
                        } catch (FileNotFoundException e) {
                            print.error(e);
                        }
                    }
                    break;
                case 0:
                    print.separator();
//                    System.exit(0);
                    read = false;
                    break;
            }
        } while (read);
    }

    private void activate() throws FileNotFoundException {
        if (!isActivated) {
            if (Vigenere.crypt(print.getPassword(), "nastya").equals(getActivationCode())) {
                isActivated = true;
                print.correctKey();
            } else {
                print.incorrectKey();
            }
        }
    }

    private String getActivationCode() throws FileNotFoundException {
        String prevFile = fileOperations.getFileName();
        fileOperations.setFileName(KEY_FILE_NAME);
        String key = fileOperations.getText();
        fileOperations.setFileName(prevFile);
        return key;
    }

    public void setCaptchaPassed(boolean captchaPassed) {
        isCaptchaPassed = captchaPassed;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileText() {
        return fileText;
    }
}
