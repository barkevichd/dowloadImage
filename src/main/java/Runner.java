import org.apache.http.HttpResponse;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Runner {
    public static final String url = "https://randomfox.ca/floof/";

    public static void main(String args[]) {
        Properties properties = new Properties();
        properties.init();
        Utils utils = new Utils();
        DownloadImage downloadImage = new DownloadImage();
        System.out.println( "Getting image link...");
        HttpResponse response = downloadImage.executeHttpRequest(url);
        String link = downloadImage.parseImageLink(utils.entityToString(response.getEntity()));
        System.out.println("Image link taken: " + link);
        File file = downloadImage.downloadAndSave(link, System.getProperty(Constants.IMAGE_FOLDER));
        System.out.println("Image downloaded into folder " + System.getProperty(Constants.IMAGE_FOLDER));
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date());

        DatabaseConnection db = new DatabaseConnection();
        Connection conn = db.createConnection(System.getProperty(Constants.DATABASE_FOLDER), System.getProperty(Constants.DATABASE_NAME));
        db.createNewTable(conn);
        db.insertDataIntoDB(conn, link, timeStamp, file.length());
        db.closeConnection(conn);
        System.out.println("Image info saved in DB " + System.getProperty(Constants.DATABASE_NAME));
        System.out.println("Program executed successfully");
    }
}
