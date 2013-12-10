package br.com.appJsf.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.appJsf.dao.AbstractEntity;

@Entity
@Table(name="tipo_usuario")
public class UserType extends AbstractEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String descricao;
	
	@OneToMany(mappedBy="tipoUsuario", targetEntity=User.class, cascade=CascadeType.ALL)
	private Collection<User> listaUser;
	
	public UserType() {
		
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
