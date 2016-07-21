package org.lexicanalytics.persistence;

import java.util.List;

import org.lexicanalytics.model.Production;

public interface ProductionDAO {

	List<Production> listAll();
	void insertProduction(Production production);
	void deleteProduction(Production production);
	int size();
	
}
