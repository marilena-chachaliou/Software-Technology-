package inncontrol;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import inncontrol.auth.LoginScreen;
import inncontrol.booking.BookingScreen;
import inncontrol.rooms.RoomManagementScreen;
import inncontrol.events.EventsScreen;
import inncontrol.promotions.PromotionsScreen;
import inncontrol.reviews.GuestReviewsScreen;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        TabPane tabs = new TabPane();

        tabs.getTabs().add(new Tab("Login", new LoginScreen()));
        tabs.getTabs().add(new Tab("Booking", new BookingScreen()));
        tabs.getTabs().add(new Tab("Rooms", new RoomManagementScreen()));
        tabs.getTabs().add(new Tab("Events", new EventsScreen()));
        tabs.getTabs().add(new Tab("Promotions", new PromotionsScreen()));
        tabs.getTabs().add(new Tab("Reviews", new GuestReviewsScreen()));

        primaryStage.setScene(new Scene(tabs, 800, 600));
        primaryStage.setTitle("InnControl - Full Demo");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
