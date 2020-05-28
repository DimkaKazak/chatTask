package chat.filter;

import chat.filter.interfaces.Filter;
import constant.Writing;

public class NameFilter implements Filter {

    @Override
    public String filter(String message) {
        String[] splittedMsg = message.split(" ");
        StringBuilder builder = new StringBuilder("");

        for (String word : splittedMsg){
            for (String name : Writing.names){
                if (name.toLowerCase().contains(word.toLowerCase())){
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
