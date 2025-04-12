package com.discgolf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.discgolf.dao.DiscDAO;
import com.discgolf.dao.DiscDAOImpl;
import com.discgolf.model.Disc;
import com.discgolf.model.DiscSearchCriteria;

import java.util.List;

public class DiscServiceImpl implements DiscService {
    private static final Logger logger = LoggerFactory.getLogger(DiscServiceImpl.class);
    private final DiscDAO discDAO;

    public DiscServiceImpl() {
        this.discDAO = new DiscDAOImpl();
    }

    public DiscServiceImpl(DiscDAO discDAO) {
        this.discDAO = discDAO;
    }

    /**
     * Create a new disc
     *
     * @param disc Disc to create
     * @return the created disc with its generated ID
     */
    @Override
    public Disc createDisc(Disc disc) {
        return discDAO.createDisc(disc);
    }

    /**
     * Retrieve all discs
     *
     * @return List of all discs
     */
    @Override
    public List<Disc> getAllDiscs() {
        logger.info("Service Call: Getting all discs");
        return discDAO.getAllDiscs();
    }

    /**
     * Retrieve a disc by ID
     *
     * @param discId The ID of the disc to retrieve
     * @return the disc if found, null otherwise
     */
    @Override
    public Disc getDiscById(int discId) {
        logger.info("Service Call: Getting disc by ID: {}", discId);
        return discDAO.getDiscById(discId);
    }

    /**
     * Search for discs based on a set of criteria
     *
     * @param criteria The search criteria object containing any combination of filters
     * @return List of discs matching the criteria
     */
    @Override
    public List<Disc> searchDiscs(DiscSearchCriteria criteria) {
        return List.of();
    }

    /**
     * Update an existing disc
     *
     * @param disc The disc to update
     * @return True if the disc was updated, false otherwise
     */
    @Override
    public boolean updateDisc(Disc disc) {
        return false;
    }

    /**
     * Delete a disc by its ID
     *
     * @param discId ID of disc to be deleted
     * @return true if disc was deleted, false otherwise
     */
    @Override
    public boolean deleteDisc(int discId) {
        return false;
    }
}
