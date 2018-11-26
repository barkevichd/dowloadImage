import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class Utils {

    public void createFolder(String path) {
        try {
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
        } catch (SecurityException e) {
            throw new DownloadImageException("Folder cannot be created: permission denied or provided path is not correct", e.getCause());
        }
    }


    public String entityToString(HttpEntity entity) {
        try {
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            throw new DownloadImageException("Could not convert Entity to String", e.getCause());
        }
    }
}
