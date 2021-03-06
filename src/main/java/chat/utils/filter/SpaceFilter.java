package chat.utils.filter;

import chat.utils.filter.interfaces.Filter;
import constant.Writing;

import java.util.StringTokenizer;

public class SpaceFilter implements Filter {
    @Override
    public String filter(String message) {
        StringTokenizer st = new StringTokenizer(message, " ");
        StringBuilder builder = new StringBuilder("");

        while (st.hasMoreTokens()){
            String val = st.nextToken();
            if (Writing.punctuationMarks.contains(val.charAt(0))){
                builder.replace(builder.length() - 1, builder.length(), "");
            }

            builder.append(val).append(" ");
        }

        if (!builder.toString().isEmpty()) builder.replace(builder.length() - 1, builder.length(), "");
        return builder.toString();
    }
}
