package chat.filter;

import chat.filter.interfaces.Filter;
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

        builder.replace(builder.length() - 1, builder.length(), "");
        return builder.toString();
    }
}
