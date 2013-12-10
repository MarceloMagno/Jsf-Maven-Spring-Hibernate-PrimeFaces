package br.com.appJsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.appJsf.dao.UserDAO;
import br.com.appJsf.dao.UserTypeDAO;
import br.com.appJsf.model.User;
import br.com.appJsf.model.UserType;


@Controller(value="usuarioBean")
@Scope(value="session")
public class UserManagedBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
    private UserDAO userDAO;
	@Autowired
	private UserTypeDAO userTypeDAO;
	@Autowired
    private User user;
	@Autowired
	private UserType userType;
    private List<User> userList;
    private List<UserType> userTypeList;
    private List<SelectItem> listSelectItem; 
    private Long tipoDeUsuarioSelecionado;
    private User userReset;
    
    public UserManagedBean() {
    	userReset = new User();
	}
    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
     
    public String addUser() {
    	try {
    			if((!userReset.getName().equals(null)) && (userReset.getName() != "") && ((! userReset.getSurname().equals(null)) && (userReset.getSurname() != "" )) && (this.tipoDeUsuarioSelecionado != 0)){
	    			
    				boolean existe = userDAO.buscaPorNome(userReset.getName());
    				
    				if(!existe){
	    				user.setId(null);
		    			user.setName(userReset.getName());
		    			user.setSurname(userReset.getSurname());
		    			userType = userTypeDAO.getById(this.tipoDeUsuarioSelecionado);
		    			user.setTipoUsuario(userType);
		    			userDAO.save(user);
		    			userReset.setName("");
		    			userReset.setSurname("");
		    			this.tipoDeUsuarioSelecionado = 0l;

		    			return "/pages/listaUser";
	    			}else{
	    				FacesMessage msg = new FacesMessage(userReset.getName() + " já cadastrado!");  
	    		        FacesContext.getCurrentInstance().addMessage(null, msg);
	    			}
    			
	    			
    			}else{
    				FacesMessage msg = new FacesMessage("Certifique-se de que todos os campos foram preenchido!");  
    		        FacesContext.getCurrentInstance().addMessage(null, msg);
    			}
    			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return "/pages/listaUser";
    	
    }
    
    public List<User> getUserList() {
        userList = new ArrayList<User>();
        userList.addAll(userDAO.findAll());
        return userList;
    }
    
    public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public List<SelectItem> getListaTipoUser(){
    	userTypeList = new ArrayList<UserType>();
    	userTypeList.addAll(userTypeDAO.findAll());
    	
    	listSelectItem = new ArrayList<SelectItem>();
    	
    	for (UserType tp : userTypeList) {
    		SelectItem item = new SelectItem(tp.getId(), tp.getDescricao());
    		listSelectItem.add(item);
		}
    	
    	return listSelectItem;
    }
    
    public String excluiUser(){
       	userDAO.delete(userDAO.getById(user.getId()));
    	return "/pages/listaUser";
    }
    
    public String editarUser(){
    	userType = userTypeDAO.getById(this.tipoDeUsuarioSelecionado);
    	user.setTipoUsuario(userType);
    	userDAO.update(userDAO.getById(user.getId()));
    	return "/pages/listaUser";
    }
    
    public void onEdit(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage();
        
        user = (User) event.getObject();
        userType = userTypeDAO.getById(this.tipoDeUsuarioSelecionado);
    	user.setTipoUsuario(userType);
    	userDAO.update(userDAO.getById(user.getId()));
        
    	msg.setSummary(user.getName()+" alterado!");
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
      
    public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Cancelado edição do usuário");  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    public void reset(){
    	userReset.setName("");
		userReset.setSurname("");
		this.tipoDeUsuarioSelecionado = 0l;
    }

	public Long getTipoDeUsuarioSelecionado() {
		return tipoDeUsuarioSelecionado;
	}

	public void setTipoDeUsuarioSelecionado(Long tipoDeUsuarioSelecionado) {
		this.tipoDeUsuarioSelecionado = tipoDeUsuarioSelecionado;
	}

	public User getUserReset() {
		return userReset;
	}

	public void setUserReset(User userReset) {
		this.userReset = userReset;
	}
 	
}