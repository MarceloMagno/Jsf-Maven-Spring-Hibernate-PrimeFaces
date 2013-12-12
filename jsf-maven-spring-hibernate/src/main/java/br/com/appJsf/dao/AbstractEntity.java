package br.com.appJsf.dao;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author Marcelo:
 * Essa classe serve de base para todas as outras classes de entidades (tabelas de banco)
 * Caso as suas tabelas tenham mais atributos comuns, esses atributos podem ser criados aq 
 */
@MappedSuperclass
public class AbstractEntity {
	 
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PrimaryKeyJoinColumn
    private Long id;

    public Long getId() {
        return id;
    }
    
	public void setId(Long id) {
        this.id = id;
    }
}
