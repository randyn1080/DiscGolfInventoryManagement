package com.discgolf.service;

import com.discgolf.model.Disc;
import com.discgolf.model.DiscSearchCriteria;

import java.util.List;

public interface DiscService {
    /**
     * Create a new disc
     * @param disc Disc to create
     * @return the created disc with its generated ID
     */
    Disc createDisc(Disc disc);

    /**
     * Retrieve all discs
     * @return List of all discs
     */
    List<Disc> getAllDiscs();

    /**
     * Retrieve a disc by ID
     * @param discId The ID of the disc to retrieve
     * @return the disc if found, null otherwise
     */
    Disc getDiscById(int discId);

    /**
     * Search for discs based on a set of criteria
     * @param criteria The search criteria object containing any combination of filters
     * @return List of discs matching the criteria
     */
    List<Disc> searchDiscs(DiscSearchCriteria criteria);

    /**
     * Update an existing disc
     * @param disc The disc to update
     * @return True if the disc was updated, false otherwise
     */
    boolean updateDisc(Disc disc);

    /**
     * Delete a disc by its ID
     * @param discId ID of disc to be deleted
     * @return true if disc was deleted, false otherwise
     */
    boolean deleteDisc(int discId);
}
