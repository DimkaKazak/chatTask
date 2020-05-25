package IO.interfaces;

import IO.exception.UnableToCloseException;
import IO.exception.UnableToReadException;

public interface Reader {
    String read() throws UnableToReadException, UnableToCloseException;
}
