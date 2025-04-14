package com.discgolf.controller;

import com.discgolf.model.Disc;
import com.discgolf.model.DiscSearchCriteria;
import com.discgolf.service.DiscService;
import com.discgolf.service.DiscServiceImpl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DiscController {
    private static Logger logger = LoggerFactory.getLogger(DiscController.class);
    private final DiscService discService;

    /**
     * Default Constructor
     */
    public DiscController() {
        discService = new DiscServiceImpl();
    }

    /**
     * For testing with mockito
     * @param discService mock discService
     */
    public DiscController(DiscService discService) {
        this.discService = discService;
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> cors.add(CorsPluginConfig::anyHost));
        });

        app.get("/discs",this::getAllDiscs);
        app.get("/discs/search", this::getSearchDisc);
        app.get("/discs/{id}", this::getDiscById);
        app.post("/discs/search", this::postSearchDisc);
        app.post("/discs", this::createDisc);
        app.put("/discs/{id}", this::updateDisc);
        app.delete("discs/{id}", this::deleteDisc);
        return app;
    }

    private void getAllDiscs(Context ctx) {
        logger.info("Controller: GET /discs");
        ctx.json(discService.getAllDiscs());
    }

    private void getDiscById(Context ctx) {
        try {
            int discId = Integer.parseInt(ctx.pathParam("id"));
            logger.info("Controller: GET /discs/{}", discId);

            Disc disc = discService.getDiscById(discId);
            if (disc != null) {
                ctx.json(disc);
            } else {
                ctx.status(404).json("Disc not found");
            }
        } catch (Exception e) {
            logger.error("Controller: Invalid Disc ID format", e);
            ctx.status(400).json("Invalid Disc ID format");
        }
    }

    private void getSearchDisc(Context ctx) {
        try {
            DiscSearchCriteria.Builder builder = DiscSearchCriteria.builder();

            String speedParam = ctx.pathParam("speed");
            if (!speedParam.isEmpty()) {
                try {
                    builder.speed(Integer.parseInt(speedParam));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid speed value: {}", speedParam);
                }
            }

            String glideParam = ctx.pathParam("glide");
            if (!glideParam.isEmpty()) {
                try {
                    builder.glide(Integer.parseInt(glideParam));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid glide value: {}", glideParam);
                }
            }

            String turnParam = ctx.pathParam("turn");
            if (!turnParam.isEmpty()) {
                try {
                    builder.turn(Integer.parseInt(turnParam));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid turn value: {}", turnParam);
                }
            }

            String fadeParam = ctx.pathParam("fader");
            if (!fadeParam.isEmpty()) {
                try {
                    builder.fade(Integer.parseInt(fadeParam));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid fade value: {}", fadeParam);
                }
            }

            String manufacturerParam = ctx.pathParam("manufacturer");
            if (!manufacturerParam.isEmpty()) {
                builder.manufacturer(manufacturerParam);
            }

            String modelParam = ctx.pathParam("model");
            if (!modelParam.isEmpty()) {
                builder.mold(modelParam);
            }

            String colorParam = ctx.pathParam("color");
            if (!colorParam.isEmpty()) {
                builder.color(colorParam);
            }

            String weightParam = ctx.pathParam("weight");
            if (!weightParam.isEmpty()) {
                try {
                    builder.weight(Integer.parseInt(weightParam));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid weight value: {}", weightParam);
                }
            }

            DiscSearchCriteria criteria = builder.build();
            logger.info("Controller: GET /discs/search with criteria: {}", criteria);
            ctx.json(discService.searchDiscs(criteria));
        } catch (Exception e) {
            logger.error("Controller: Error searching for discs", e);
            ctx.status(500).json("Error searching for discs " + e.getMessage());
        }
    }

    private void postSearchDisc(Context ctx) {
        try {
            DiscSearchCriteria criteria = ctx.bodyAsClass(DiscSearchCriteria.class);
            logger.info("Controller: POST /discs/search");
            ctx.json(discService.searchDiscs(criteria));
        } catch (Exception e) {
            logger.error("Controller: Error searching discs", e);
            ctx.status(400).json("Error searching discs: " + e.getMessage());
        }
    }

    private void createDisc(Context ctx) {
        try {
            Disc disc = ctx.bodyAsClass(Disc.class);
            logger.info("Controller: POST /discs - {} {}", disc.getManufacturer(), disc.getMold());

            Disc createdDisc = discService.createDisc(disc);
            if (createdDisc != null) {
                ctx.status(201).json("Disc created");
            } else {
                ctx.status(400).json("Failed to create disc");
            }
        } catch (Exception e) {
            logger.error("Controller: Error creating disc", e);
            ctx.status(400).json("Error creating disc: " + e.getMessage());
        }
    }

    private void updateDisc(Context ctx) {
        try {
            Disc disc = ctx.bodyAsClass(Disc.class);
            logger.info("Controller: PUT /discs - {} {} {}",
                    disc.getDiscID(), disc.getManufacturer(), disc.getMold());

            boolean updated = discService.updateDisc(disc);
            if (updated) {
                ctx.json(disc);
            } else {
                ctx.status(404).json("Disc not found or update failed");
            }
        } catch(NumberFormatException e) {
            logger.error("Controller: Invalid Disc ID format", e);
            ctx.status(400).json("Invalid Disc ID format");
        } catch (Exception e) {
            logger.error("Controller: Error updating disc", e);
            ctx.status(400).json("Error updating disc: " + e.getMessage());
        }
    }

    private void deleteDisc(Context ctx) {
        try {
            int discId = Integer.parseInt(ctx.pathParam("id"));
            logger.info("Controller: DELETE /discs/{}", discId);

            boolean deleted = discService.deleteDisc(discId);
            if (deleted) {
                ctx.json("Disc deleted succesfully");
            } else {
                ctx.status(404).json("Disc not found or deletion failed");
            }
        } catch (NumberFormatException e) {
            logger.error("Controller: Invalid Disc ID format", e);
            ctx.status(400).json("Invalid Disc ID format");
        }
    }
}
