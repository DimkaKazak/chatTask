package chat.utils.filter;

import constant.Writing;

public class NickFilter implements chat.utils.filter.interfaces.NickValidator {
    @Override
    public boolean validateNick(String nick) {
        for (String swearWord : Writing.swearWords){
            if (swearWord.substring(0, swearWord.length() - 1).equalsIgnoreCase(nick)){
                return false;
            }
        }
        return true;
    }
}
