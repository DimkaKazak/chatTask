package constant;

import java.io.File;

public class FileConstants {

    private static final String sep = File.separator;

    public static final String CONFIG_FILE_PATH = "src" + sep + "main" + sep + "resources" + sep + "config.properties";
    public static final String RUS_SWEAR_WORDS_FILE_PATH = "src" + sep + "main" + sep + "resources" + sep + "filters/swearWords.txt";
    public static final String NAMES_FILE_PATH = "src" + sep + "main" + sep + "resources" + sep + "filters/namesRUS.txt";
    public static final String CAPITALS_FILE_PATH = "src" + sep + "main" + sep + "resources" + sep + "filters/capitalsENG.txt";
    public static final String COUNTRY_FILE_PATH = "src" + sep + "main" + sep + "resources" + sep + "filters/countryENG.txt";
    public static final String SQL_CONFIG_FILE_PATH = "src" + sep + "main" + sep + "resources" + sep + "sql_config.properties";
}
