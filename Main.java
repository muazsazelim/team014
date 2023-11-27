
// Main.java
import javax.swing.SwingUtilities;

import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.DatabaseOperations;

import com.sheffield.views.LoginView;
import com.sheffield.views.OrderManagementStaffView;
public class Main {
    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        
        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // Create and initialize the LoanTableDisplay view using the database connection
                loginView = new LoginView(databaseConnectionHandler.getConnection());
                loginView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
