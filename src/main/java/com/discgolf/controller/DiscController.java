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
        app.get("/discs/{id}", this::getDiscById);
        app.get("/discs/search", this::getSearchDisc);
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
            // params are
            //   Integer speed;
            //   Integer glide;
            //   Integer turn;
            //   Integer fade;
            //   String manufacturer;
            //   String mold;
            //   String color;
            //   Integer weight;
            DiscSearchCriteria criteria = new DiscSearchCriteria();

            if (!ctx.pathParam("speed").isEmpty()) {
                try {
                    criteria.setSpeed(Integer.parseInt(ctx.pathParam("speed")));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid speed value: {}", ctx.pathParam("speed"));
                }
            }
            if (!ctx.pathParam("glide").isEmpty()) {
                try {
                    criteria.setGlide(Integer.parseInt(ctx.pathParam("glide")));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid glide value: {}", ctx.pathParam("glide"));
                }
            }
            if (!ctx.pathParam("turn").isEmpty()) {
                try {
                    criteria.setTurn(Integer.parseInt(ctx.pathParam("turn")));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid turn value: {}", ctx.pathParam("turn"));
                }
            }
            if (!ctx.pathParam("fade").isEmpty()) {
                try {
                    criteria.setFade(Integer.parseInt(ctx.pathParam("fade")));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid fade value: {}", ctx.pathParam("fade"));
                }
            }

            if (!ctx.pathParam("manufacturer").isEmpty()) {
                criteria.setManufacturer(ctx.pathParam("glide"));
            }

            if (!ctx.pathParam("mold").isEmpty()) {
                criteria.setMold(ctx.pathParam("mold"));
            }

            if (!ctx.pathParam("color").isEmpty()) {
                criteria.setColor(ctx.pathParam("color"));
            }

            if (!ctx.pathParam("weight").isEmpty()) {
                try {
                    criteria.setWeight(Integer.parseInt(ctx.pathParam("weight")));
                } catch (NumberFormatException e) {
                    logger.warn("Controller: Invalid weight value: {}", ctx.pathParam("weight"));
                }
            }
            logger.info("Controller: GET /discs/search with criteria {}", criteria);
            ctx.json(discService.searchDiscs(criteria));

        } catch (Exception e) {
            logger.error("Controller: Error searching for discs", e);
            ctx.status(500).json("Error searching for discs" + e.getMessage());
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
        }catch(NumberFormatException e) {
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
