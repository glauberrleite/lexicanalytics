package org.lexicanalytics.persistence;

import java.util.ArrayList;
import java.util.List;

import org.lexicanalytics.model.Production;

public class ProductionList implements ProductionDAO {

	private List<Production> productions;

	public ProductionList() {
		this.productions = new ArrayList<Production>();
	}

	@Override
	public List<Production> listAll() {
		return new ArrayList<Production>(productions);
	}

	@Override
	public void insertProduction(Production production) {
		if (production != null)
			productions.add(production);

	}

	@Override
	public void deleteProduction(Production production) {
		if (production != null)
			productions.remove(production);
		
	}

	@Override
	public int size() {
		return productions.size();
	}
}
