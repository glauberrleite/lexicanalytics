package org.lexicanalytics.persistence;

import java.util.ArrayList;
import java.util.List;

import org.lexicanalytics.model.Production;

/**
 * Implements production temporary persistence using ArrayList
 * 
 * @author glauberrleite
 *
 */
public class ProductionArray implements ProductionDAO {

	private List<Production> productions;

	public ProductionArray() {
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

	@Override
	public Production getByIndex(int index) {
		if (index < productions.size())
			return productions.get(index);
		else
			return null;
	}

	@Override
	public int getIndex(Production production) {
		if (productions.contains(production)) {
			return productions.indexOf(production);
		} else {
			return -1;
		}
	}
}
