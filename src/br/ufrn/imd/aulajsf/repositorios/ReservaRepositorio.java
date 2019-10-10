package br.ufrn.imd.aulajsf.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import br.ufrn.imd.aulajsf.dominio.Reserva;
import br.ufrn.imd.aulajsf.util.HibernateUtil;
@Stateless
public class ReservaRepositorio {
	@PersistenceContext
	private static EntityManager entityManager = HibernateUtil.getEntityManager();
	public static List<Reserva> reservas;

	
	@SuppressWarnings("unchecked")
	public  Reserva getReserva(int id) {
		EntityTransaction transaction = entityManager.getTransaction();
		if(!transaction.isActive())
			transaction.begin();
		List<Reserva> retorno = entityManager.createQuery("from Reserva u where u.id='" + id + "'")
				.getResultList();

		System.out.println(retorno.size());
		for (Reserva u : retorno) {
			if (u.getId()==id) {
				return u;
			}
		}
		return null;
	}

	public void salvar(Reserva entidade) {
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
	public List<Reserva> listarReservas(){
		List<Reserva> retorno = entityManager.createQuery("from Reserva").getResultList();
		reservas=retorno;
		return reservas;
	}

	public void remover(Reserva reservaRemovida) {
		Object id = HibernateUtil.getPrimaryKey(reservaRemovida);
		
		EntityTransaction transaction = entityManager.getTransaction();
		if (!transaction.isActive())
			transaction.begin();
		
		entityManager.createNativeQuery("delete from reserva where id ="+id).executeUpdate();
		transaction.commit();
	}
}
