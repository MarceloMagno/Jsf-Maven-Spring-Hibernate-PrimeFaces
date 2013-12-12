package br.com.appJsf.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.appJsf.dao.AbstractEntity;

/**
 * @author Marcelo
 *
 */
@Entity
@Table(name="USER")
public class User extends AbstractEntity implements Serializable{
 
    private static final long serialVersionUID = 1L;
	
	@Column(name="NAME", unique = true, nullable = false)
	private String name;
	
	@Column(name="SURNAME", nullable = false)
    private String surname;
	
	@Column(name="data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@ManyToOne
	@JoinColumn(name =  "FK_TIPO_USUARIO",  nullable=false,referencedColumnName="id" )
	private UserType tipoUsuario;
	
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public UserType getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(UserType tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}