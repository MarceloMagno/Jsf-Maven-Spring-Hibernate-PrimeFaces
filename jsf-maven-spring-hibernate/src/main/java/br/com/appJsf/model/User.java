package br.com.appJsf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.appJsf.dao.AbstractEntity;

@Entity
@Table(name="USER")
public class User extends AbstractEntity implements Serializable{
 
    private static final long serialVersionUID = 1L;
	
	@Column(name="NAME", unique = true, nullable = false)
	private String name;
	
	@Column(name="SURNAME", nullable = false)
    private String surname;
	
	@ManyToOne
	@JoinColumn(name =  "FK_TIPO_USUARIO",  nullable=false,referencedColumnName="id" )
	private UserType tipoUsuario;
	
	/*@ForeignKey(name = "FK_TIPO_USUARIO")
	private Long fkTipoDeUsuario;*/
	
	public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getSurname() {
        return surname;
    }
 
    public void setSurname(String surname) {
        this.surname = surname;
    }

	public UserType getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(UserType tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}