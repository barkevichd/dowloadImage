import org.apache.http.HttpResponse;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Runner {
    public static final Logger LOG = Logger.getLogger(Runner.class.getName());
    public static final String url = "https://randomfox.ca/floof/";

    public static void main(String args[]) {
        Properties properties = new Properties();
        properties.init();
        Utils utils = new Utils();
        DownloadImage downloadImage = new DownloadImage();
        LOG.log(Level.INFO, "Getting image link...");
        HttpResponse response = downloadImage.executeHttpRequest(url);
        String link = downloadImage.parseImageLink(utils.entityToString(response.getEntity()));
        LOG.log(Level.INFO, "Image link taken: " + link);
        File file = downloadImage.downloadAndSave(link, System.getProperty(Constants.IMAGE_FOLDER));
        LOG.log(Level.INFO, "Image downloaded into folder " + System.getProperty(Constants.IMAGE_FOLDER));
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date());

        DatabaseConnection db = new DatabaseConnection();
        Connection conn = db.createConnection(System.getProperty(Constants.DATABASE_FOLDER), System.getProperty(Constants.DATABASE_NAME));
        db.createNewTable(conn);
        db.insertDataIntoDB(conn, link, timeStamp, file.length());
        db.closeConnection(conn);
        LOG.log(Level.INFO, "Image info saved in DB " + System.getProperty(Constants.DATABASE_NAME));
        LOG.log(Level.INFO, "Program executed successfully");
    }
}
