package chat.filter.interfaces;

public interface NickFilter extends Filter{
    boolean validateNick(String nick);
}
