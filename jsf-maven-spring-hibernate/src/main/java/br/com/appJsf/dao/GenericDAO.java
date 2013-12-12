package br.com.appJsf.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author Marcelo:
 * Essa he uma classe generica de persistencia, nela ja esta implementado os metodos basicos.
 * Caso vc queira criar novos metodos de persistencias isso tem q ser feito na respectiva classe dao.
 * Para usar esta classe basta estende-la na dao q se quer criar informando a classe tipo no parametro T
 * exemplo: extends GenericDAO<Pessoa> 
 */

@SuppressWarnings("unchecked")
public class GenericDAO<T extends AbstractEntity> {
	
	/*
	 * he criado no contexto o entity manager onde o spring ira
	 * controlar criacao do objeto, nao sendo necessario instancia-lo
	 */
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Transactional
	public T getById(Long id) {
        return (T) entityManager.find(getTypeClass(), id);
    }

	@Transactional
	public void save(T entity) {
        try {
              this.entityManager.persist(entity);           
        } catch (Exception e) {
            e.printStackTrace();
            this.entityManager.getTransaction().getRollbackOnly();
        }
    }
	
	@Transactional
	public void update(T entity) {
        try {
        	this.entityManager.merge(entity);
        } catch (Exception e) {
            e.printStackTrace();
            this.entityManager.getTransaction().getRollbackOnly();
        }
    }
 
	@Transactional
    public void delete(T entity) {
        try {
        	this.entityManager.remove(entity);
        } catch (Exception e) {
            e.printStackTrace();
            this.entityManager.getTransaction().getRollbackOnly();
        }
    }
 
	@Transactional
    public List<T> findAll() {
        return entityManager.createQuery(("From " + getTypeClass().getName())).getResultList();
    }
	
	private Class<?> getTypeClass() {
	        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	        return clazz;
	}
	
}
