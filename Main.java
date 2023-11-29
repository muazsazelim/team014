
// Main.java
import java.awt.BorderLayout;

import javax.swing.SwingUtilities;

import com.sheffield.model.DatabaseConnectionHandler;
import com.sheffield.model.DatabaseOperations;

import com.sheffield.views.LoginView;
import com.sheffield.views.OrderManagementStaffView;
import com.sheffield.views.TrainsOfSheffield;
import com.sheffield.views.UserMainView;
public class Main {
    public static void main(String[] args) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        
        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            TrainsOfSheffield window = null;
            LoginView loginView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // Create and initialize the LoanTableDisplay view using the database connection
                window = new TrainsOfSheffield();
                window.setVisible(true);

                loginView = new LoginView(databaseConnectionHandler.getConnection());

                TrainsOfSheffield.getPanel().removeAll();
                TrainsOfSheffield.getPanel().add(loginView, BorderLayout.CENTER);
                TrainsOfSheffield.getPanel().revalidate();

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }
}
