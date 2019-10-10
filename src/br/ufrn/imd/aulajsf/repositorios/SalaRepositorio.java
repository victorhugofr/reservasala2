package br.ufrn.imd.aulajsf.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import br.ufrn.imd.aulajsf.dominio.Sala;
import br.ufrn.imd.aulajsf.util.HibernateUtil;
public class SalaRepositorio {
	private static EntityManager entityManager = HibernateUtil.getEntityManager();
	public static List<Sala> salas;

	
	@SuppressWarnings("unchecked")
	public static Sala getSala(int id) {
		EntityTransaction transaction = entityManager.getTransaction();
		if(!transaction.isActive())
			transaction.begin();
		List<Sala> retorno = entityManager.createQuery("from Sala u where u.id='" + id + "'")
				.getResultList();

		System.out.println(retorno.size());
		for (Sala u : retorno) {
			if (u.getId()==id) {
				return u;
			}
		}
		return null;
	}

	public static void salvar(Sala entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		if (!transaction.isActive())
			transaction.begin();
		if(entidade.getId()==0)
			entityManager.persist(entidade);
		else 
			entityManager.merge(entidade);
		transaction.commit();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Sala> listarSalas(){
		List<Sala> retorno = entityManager.createQuery("from Sala").getResultList();
		salas=retorno;
		return salas;
	}

	public static void remover(Sala salaRemovida) {
		Object id = HibernateUtil.getPrimaryKey(salaRemovida);
		
		EntityTransaction transaction = entityManager.getTransaction();
		if (!transaction.isActive())
			transaction.begin();
		
		entityManager.createNativeQuery("delete from sala where id ="+id).executeUpdate();
		transaction.commit();
	}
}
