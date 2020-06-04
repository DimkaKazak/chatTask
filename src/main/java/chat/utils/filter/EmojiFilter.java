package chat.utils.filter;

import chat.utils.filter.interfaces.Filter;
import com.vdurmont.emoji.EmojiParser;

public class EmojiFilter implements Filter {
    @Override
    public String filter(String message) {
        return EmojiParser.parseToUnicode(message);
    }
}
