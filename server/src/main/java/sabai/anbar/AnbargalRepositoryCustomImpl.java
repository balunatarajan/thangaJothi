package sabai.anbar;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sabai.anbar.Anbargal;

public class AnbargalRepositoryCustomImpl implements AnbargalRepositoryCustom{
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Anbargal> readAllAnbargal() {
		return this.entityManager.
				createQuery("select e from anbargal e ").
					getResultList();
	}

}

