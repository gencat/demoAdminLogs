package cat.gencat.demoadminlogs.domain;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import cat.gencat.ctti.canigo.arch.persistence.jpa.dao.impl.JPAGenericDaoImpl;
import cat.gencat.demoadminlogs.dao.EquipamentDAO;
import cat.gencat.demoadminlogs.model.Equipament;

public class EquipamentDAOImpl extends JPAGenericDaoImpl<Equipament, Long> implements EquipamentDAO {

	@SuppressWarnings("unchecked")
	public List<Equipament> findPaginated(final Integer page, final Integer rpp, final String sortField, final String sortDirection, final String filter) throws PersistenceException {
		
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("select e from "
				+ getPersistentClass().getSimpleName() + " e");
		if (filter != null && !"".equals(filter)) {
			queryStr.append(" where match_against(e.nom, e.municipi, :filter) > 0");
		}
		if (sortField != null && !"".equals(sortField)) {
			queryStr.append(" order by " + sortField);
		}
		if (sortDirection != null && !"".equals(sortDirection)) {
			queryStr.append(" " + sortDirection);
		}
		Query query = getEntityManager().createQuery(queryStr.toString());
		if (filter != null && !"".equals(filter)) {
			query.setParameter("filter", filter.toUpperCase());
		}
		return query.setFirstResult((page == null ? 0 : (page - 1)) * rpp).setMaxResults(rpp).getResultList();
	}

	public Long count(final String filter) throws PersistenceException {
		
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("select count(e) from "
				+ getPersistentClass().getSimpleName() + " e");
		if (filter != null && !"".equals(filter)) {
			queryStr.append(" where match_against(e.nom, e.municipi, :filter) > 0");
		}
		Query query = getEntityManager().createQuery(queryStr.toString());
		if (filter != null && !"".equals(filter)) {
			query.setParameter("filter", filter.toUpperCase());
		}
		return (Long) query.getSingleResult();
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
