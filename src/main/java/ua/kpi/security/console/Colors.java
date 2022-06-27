package ua.kpi.security.console;

public interface Colors {
    String RESET = "\033[0m";
    String PURPLE = "\u001B[35m";
    String RED = "\033[0;31m";
    String BLUE = "\u001B[34m";
    String CYAN_BACKGROUND = "\033[46m";

    String SEPARATOR = BLUE +
            "+----------------------------------------------+" + RESET;
    String VERTICAL_BAR = BLUE + "|" + RESET;
}
