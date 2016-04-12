package cat.gencat.demoadminlogs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cat.gencat.demoadminlogs.dao.EquipamentDAO;
import cat.gencat.demoadminlogs.model.Equipament;

@Service("equipamentService")
@Lazy
public class EquipamentService {

	@Autowired
	private EquipamentDAO equipamentDao;

	public List<Equipament> findAll() {
		return equipamentDao.findAll();
	}

	public List<Equipament> findPaginated(Integer page, Integer rpp,
			String sortField, String sortDirection, String filter) {
		return equipamentDao.findPaginated(page, rpp, sortField, sortDirection,
				filter);
	}

	public Long count(String filter) {
		return equipamentDao.count(filter);
	}

	public Equipament getEquipament(Long equipamentId) {
		return equipamentDao.get(equipamentId);
	}

	public Long saveEquipament(Equipament equipament) {
		equipamentDao.save(equipament);

		return equipament.getId();
	}

	public void updateEquipament(Equipament equipament) {
		equipamentDao.update(equipament);
	}

	public void deleteEquipament(Long equipamentId) {
		Equipament equipament = new Equipament(equipamentId);
		equipamentDao.delete(equipament);
	}

}
