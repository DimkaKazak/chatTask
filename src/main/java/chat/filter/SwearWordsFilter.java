package chat.filter;

import chat.filter.interfaces.Filter;
import constant.Writing;

public class SwearWordsFilter implements Filter {

    @Override
    public String filter(String message) {
        String[] splittedMsg = message.split(" ");
        StringBuilder builder = new StringBuilder("");

        for (String word : splittedMsg){
            for (String swearWord : Writing.swearWords){
                if (swearWord.toLowerCase().contains(word.toLowerCase()) && word.length() > 1){

                    for (int i = 1; i < word.length() - 1; i++){
                        word = word.replace(word.substring(i, i + 1), "*");
                    }

                }
            }

            if (Writing.punctuationMarks.contains(word.toLowerCase())) builder.append(word);
            else builder.append(" ").append(word);
        }

        builder.replace(0, 1, "");
        return builder.toString();
    }
}