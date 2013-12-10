package br.com.appJsf.dao;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrimaryKeyJoinColumn;

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
