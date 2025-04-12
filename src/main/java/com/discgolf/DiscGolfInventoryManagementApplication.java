package com.discgolf;

import com.discgolf.controller.DiscController;
import io.javalin.Javalin;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class DiscGolfInventoryManagementApplication {
    private static final Logger logger = LoggerFactory.getLogger(DiscGolfInventoryManagementApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Disc Golf Inventory Management Application");

        // create and start the controller
        DiscController controller = new DiscController();
        Javalin app = controller.startAPI();

        // start the app on port 8080
        app.start(8080);

        logger.info("Disc Golf Inventory Management Application started on port 8080");
    }
}
