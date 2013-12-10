package br.com.appJsf.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.appJsf.model.User;

@Repository
public class UserDAO extends GenericDAO<User>{
	
	@Transactional
	public boolean buscaPorNome(String nome){
		try {
			
			TypedQuery<String> query = entityManager.createQuery("SELECT u.name FROM User u WHERE u.name = :nome", String.class);
			query.setParameter("nome", nome);
			
			if (query.getResultList().size() > 0) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
            this.entityManager.getTransaction().getRollbackOnly();
		}
		return false;
	}

}
