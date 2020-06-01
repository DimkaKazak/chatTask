package IO.base;

import java.io.File;

public class BaseReader {
    protected File file;
    protected String path;

    public BaseReader(File file) {
        this.file = file;
        this.path = file.getAbsolutePath();
    }

    public BaseReader(String path) {
        file = new File(path);
        this.path = file.getAbsolutePath();
    }

    public String getPath() {
        return path;
    }
}
