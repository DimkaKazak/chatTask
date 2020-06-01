package chat.filter;

import constant.Writing;

public class NickFilter implements chat.filter.interfaces.NickValidator {
    @Override
    public boolean validateNick(String nick) {
        for (String swearWord : Writing.swearWords){
            if (swearWord.toLowerCase().contains(nick.toLowerCase())){
                return false;
            }
        }
        return true;
    }
}
