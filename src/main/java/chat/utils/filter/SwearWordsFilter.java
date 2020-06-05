package chat.utils.filter;

import chat.utils.filter.interfaces.Filter;
import constant.Writing;

public class SwearWordsFilter implements Filter {

    @Override
    public String filter(String message) {
        String[] splittedMsg = message.split(" ");
        StringBuilder builder = new StringBuilder("");

        for (String word : splittedMsg){
            for (String swearWord : Writing.swearWords){
                if (swearWord.substring(0, swearWord.length() - 1).equalsIgnoreCase(word) && word.length() > 1){

                    word = word.replace(word.substring(1, word.length() - 1), "*");

                }
            }

            if (Writing.punctuationMarks.contains(word.toLowerCase())) builder.append(word);
            else builder.append(" ").append(word);
        }

        builder.replace(0, 1, "");
        return builder.toString();
    }
}
