package IO.reader;

import IO.base.BaseReader;
import IO.exception.UnableToReadException;
import IO.interfaces.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader extends BaseReader implements Reader {
    private Properties props;

    public PropertyFileReader(File file) {
        super(file);
    }

    public PropertyFileReader(String path) {
        super(path);
    }

    @Override
    public String read() throws UnableToReadException {
        this.props = new Properties();

        if (!this.path.contains(".properties")) {
            throw new RuntimeException("Provide the file with .properties format");
        }

        try(InputStream inputStream = new FileInputStream(new File(this.path).getAbsolutePath())) {
            this.props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnableToReadException("");
        }

        if (props.contains("player") && props.contains("enemy")){
            return props.getProperty("player") + ";" + props.getProperty("enemy");
        }

        return "";
    }

    public String getPropertyValue(String key) {
        return String.valueOf(this.props.getOrDefault(key, ""));
    }

    public Properties getProps() {
        return props;
    }
}
