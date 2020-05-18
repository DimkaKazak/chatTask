package context;

import IO.exception.UnableToReadException;
import IO.reader.PropertyFileReader;
import constant.FileConstants;

import java.io.IOException;
import java.util.Properties;

public class ContextManager {
    private static volatile ContextManager instance;

    private Properties prop = new Properties();

    public static ContextManager getInstance() {
        ContextManager localInstance = instance;
        if (localInstance == null) {
            synchronized (ContextManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance = localInstance = new ContextManager();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return localInstance;
    }

    private ContextManager() throws IOException {
        PropertyFileReader propertyFileReader = new PropertyFileReader(FileConstants.CONFIG_FILE_PATH);

        try {
            propertyFileReader.read();
            this.prop = propertyFileReader.getProps();
        } catch (UnableToReadException e) {
            e.printStackTrace();
            this.prop = new Properties();
        }
    }

    public String getProperty(String key) {
        return String.valueOf(this.prop.getOrDefault(key, ""));
    }
}
