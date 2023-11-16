
// Main.java
import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.DatabaseOperations;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        DatabaseOperations databaseOperations = new DatabaseOperations();
        try {
            databaseConnectionHandler.openConnection();

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            databaseConnectionHandler.closeConnection();
        }
    }
}
