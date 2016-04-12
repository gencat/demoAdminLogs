package cat.gencat.demoadminlogs.dao;

import java.util.List;

import cat.gencat.demoadminlogs.model.Equipament;
import cat.gencat.ctti.canigo.arch.persistence.jpa.dao.GenericDAO;

public interface EquipamentDAO extends GenericDAO<Equipament, Long> {

	public List<Equipament> findPaginated(Integer page, Integer rpp,
			String sortField, String sortDirection, String filter);

	public Long count(String filter);

}