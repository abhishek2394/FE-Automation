package functions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SeleniumPropertyReader {
    protected static Properties singleSharedProperties;

    public Properties loadProperties(){
        singleSharedProperties = new Properties(System.getProperties());

        InputStream seleniumPropertyFile = null;

        try {
            seleniumPropertyFile = new FileInputStream("src/test/resources/selenium.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        if (seleniumPropertyFile != null) {

            try {
                singleSharedProperties.load(seleniumPropertyFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return singleSharedProperties;
    }


}
