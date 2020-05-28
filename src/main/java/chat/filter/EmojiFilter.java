package chat.filter;

import chat.filter.interfaces.Filter;
import com.vdurmont.emoji.EmojiParser;

public class EmojiFilter implements Filter {
    @Override
    public String filter(String message) {
        return EmojiParser.parseToUnicode(message);
    }
}
