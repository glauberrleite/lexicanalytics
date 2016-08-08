package org.lexicanalytics.persistence;

import java.util.List;

import org.lexicanalytics.model.Production;

/**
 * Data Access Object to provide persistence for production objects
 * 
 * @author glauberrleite
 *
 */
public interface ProductionDAO {

	/**
	 * Offer a list of all productions for loops and reports. It's not the true
	 * persistence list if a ArrayList is implemented, but a copy, avoiding
	 * unwanted manipulation.
	 * 
	 * @return A list of productions that represents the persistence.
	 */
	List<Production> listAll();

	/**
	 * Add a production to persistence
	 * 
	 * @param production
	 *            Instance to be included in the persistence
	 */
	void insertProduction(Production production);

	/**
	 * Remove a production to persistence
	 * 
	 * @param production
	 *            Instance to be removed from the persistence
	 */
	void deleteProduction(Production production);

	/**
	 * Gives a production instance based on its index
	 * 
	 * @param index
	 *            The production instance index
	 * @return Production instance or null if the index is not used by a production
	 *         stored with the persistence
	 */
	Production getByIndex(int index);

	/**
	 * Get the production index that identifies the production instance with
	 * the persistence
	 * 
	 * @param production Production instance
	 * @return Production instance index or -1 if production is not stored 
	 */
	int getIndex(Production production);

	/**
	 * Analog to a list size method
	 * 
	 * @return The number of elements stored in the persistence
	 */
	int size();

}
