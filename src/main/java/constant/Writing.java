package constant;

import IO.exception.UnableToReadException;
import IO.reader.StreamTextFileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Writing {

    public static List<String> names;
    public static List<String> swearWords;
    public static List<Character> punctuationMarks =  Arrays.asList('`', '[', ']', '(', ')', '{', '}', '<', '>', '⟨', '⟩',
            ',', ':', '-', '–', '—', '―', '!', '.', '-', '?', '\"', '\'', ';', '/', '&', '@', '*', '/', '\\', '|', '#',
            '№',  '%', ':', '^');

    static {
        try {
            swearWords = initList(FileConstants.RUS_SWEAR_WORDS_FILE_PATH);
            names = initList(FileConstants.NAMES_FILE_PATH);
        } catch (UnableToReadException e) {
            e.printStackTrace();
        }
    }

    public static String replacePM(String str){
        String result = str;
        for (Character pm : punctuationMarks){
            result = result.replace(pm, Character.MIN_VALUE).toLowerCase();
        }
        return result;
    }

    private static List<String> initList(String filePath) throws UnableToReadException {
        String words = new StreamTextFileReader(filePath).read();
        words = replacePM(words);
        return Arrays.asList(words.split(" "));
    }

}
