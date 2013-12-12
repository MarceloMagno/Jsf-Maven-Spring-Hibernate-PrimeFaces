package br.com.appJsf.dao;

import org.springframework.stereotype.Repository;

import br.com.appJsf.model.UserType;

/**
 * @author Marcelo
 *
 */
@Repository //essa anotacao he necessario para que spring possa fazer a injecao de dependencia
public class UserTypeDAO extends GenericDAO<UserType> {	

}
