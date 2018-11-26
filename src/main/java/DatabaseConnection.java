import java.io.File;
import java.sql.*;

public class DatabaseConnection {

    public Connection createConnection(String pathToDB, String fileName) {
        Utils utils = new Utils();
        utils.createFolder(pathToDB);
        String dbUrl = "jdbc:sqlite:" + pathToDB + File.separator + fileName;
        try {
           return DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            throw new DownloadImageException("Could not create connection to DB", e.getCause());
        }
    }

    public void createNewTable(Connection connection) {
        String sql = "CREATE TABLE IF NOT EXISTS downloadedImages (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	address text NOT NULL,\n"
                + "	timeOfDownload text NOT NULL,\n"
                + "	size integer NOT NULL\n"
                + ");";

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DownloadImageException("Could not execute statement", e.getCause());
        }
    }

    public void insertDataIntoDB(Connection connection, String address, String timeOfDownload, long size) {
        String sql = "INSERT INTO downloadedImages(address,timeOfDownload,size) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, address);
            pstmt.setString(2, timeOfDownload);
            pstmt.setLong(3, size);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DownloadImageException("Could not insert data int DB", e.getCause());
        }
    }

    public void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DownloadImageException("Could not close DB connection", e.getCause());
        }
    }
}
