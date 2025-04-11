package com.discgolf.dao;

import com.discgolf.model.Disc;
import com.discgolf.model.DiscSearchCriteria;

import java.util.List;

public interface DiscDAO {
    // Create
    /**
     * Creates a new disc in the database
     * @param disc The disc to create
     * @return the created disc with its generated ID
     */
    Disc createDisc(Disc disc);

    // Read
    /**
     * Retrieves all discs from the database
     * @return List of all discs
     */
    List<Disc> getAllDiscs();

    Disc getDiscById(int discId);

    /**
     * Searches for discs based on a set of criteria
     * @param criteria The search criteria object containing any combination of filters
     * @return List of discs matching the criteria
     */
    List<Disc> searchDiscs(DiscSearchCriteria criteria);

    // Update
    /**
     * Updates an existing disc in the database
     * @param disc The disc to update
     * @return True if the disc was updated, false otherwise
     */
    boolean updateDisc(Disc disc);

    // Delete
    /**
     * Deletes a disc from the database
     * @param discId The ID of the disc to be deleted
     * @return true if the disc was deleted, false otherwise
     */
    boolean deleteDisc(int discId);

}
