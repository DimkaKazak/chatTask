package chat.filter;

import chat.filter.interfaces.Filter;
import constant.Writing;

import java.util.List;

public class FirstLetterFilter implements Filter {

    private List<String> writtings;

    public FirstLetterFilter(List<String> writtings){
        this.writtings = writtings;
    }

    @Override
    public String filter(String message) {
        if (message.isEmpty()) return "";

        String[] splittedMsg = message.split(" ");
        StringBuilder builder = new StringBuilder("");

        for (String word : splittedMsg){
            for (String name : writtings){
                if (name.equalsIgnoreCase(word)){
                    word = String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1).toLowerCase();
                }
            }

            if (Writing.punctuationMarks.contains(word.toLowerCase())) builder.append(word);
            else builder.append(" ").append(word);
        }

        builder.replace(0, 1, "");
        return builder.toString();
    }
}
