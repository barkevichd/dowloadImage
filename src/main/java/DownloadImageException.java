public class DownloadImageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DownloadImageException(String message) {
        super(message);
    }

    public DownloadImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
