package constant;

import IO.exception.UnableToReadException;
import IO.reader.StreamTextFileReader;

import java.util.Arrays;
import java.util.List;

public class Writing {

    public static List<String> swearWords;
    public static List<Character> punctuationMarks =  Arrays.asList('`', '[', ']', '(', ')', '{', '}', '<', '>', '⟨', '⟩',
            ',', ':', '-', '–', '—', '―', '!', '.', '-', '?', '\"', '\'', ';', '/', '&', '@', '*', '/', '\\', '|', '#',
            '№',  '%', ':', '^');

    static {
        try {
            String strSwearWords = new StreamTextFileReader(FileConstants.RUS_SWEAR_WORDS_FILE_PATH).read();
            strSwearWords = replacePM(strSwearWords);
            swearWords = Arrays.asList(strSwearWords.split(" "));
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

}
