package br.com.appJsf;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


/**
 * @author Marcelo
 *
 */
@Controller(value="usuarioBean") //aq esta sendo usado o @Controller do spring e nao o @ManagedBean do faces
@Scope(value="session") // aq esta sendo usado o @Scope do spring e nao o @SessionScoped do faces 
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
    
    private Date dataNascimento;
    
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
    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
    		String stringData = dateFormat.format(getDataNascimento());
    		System.out.println("Data de Nascimento: " + stringData);
    			//TODO essa validacao ja esta sendo feita na view, faço aq para garantir 
    			if((!userReset.getName().equals(null)) && (userReset.getName() != "") && ((! userReset.getSurname().equals(null)) && (userReset.getSurname() != "" )) && (this.tipoDeUsuarioSelecionado != 0)){
	    			
    				boolean existe = userDAO.buscaPorNome(userReset.getName());
    				
    				if(!existe){
	    				user.setId(null);
		    			user.setName(userReset.getName());
		    			user.setSurname(userReset.getSurname());
		    			user.setDataNascimento(getDataNascimento());
		    			userType = userTypeDAO.getById(this.tipoDeUsuarioSelecionado);
		    			user.setTipoUsuario(userType);
		    			userDAO.save(user);
		    			userReset.setName("");
		    			userReset.setSurname("");
		    			this.tipoDeUsuarioSelecionado = 0l;

		    			return "/pages/listaUsuarios";
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
    	
    	return "/pages/listaUsuarios";
    	
    }
    
    public String usuarios(){
    	return "/pages/listaUsuarios";
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
    	return "/pages/listaUsuarios";
    }
    
    public String editarUser(){
    	userType = userTypeDAO.getById(this.tipoDeUsuarioSelecionado);
    	user.setTipoUsuario(userType);
    	userDAO.update(userDAO.getById(user.getId()));
    	return "/pages/listaUsuarios";
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
        FacesMessage msg = new FacesMessage("Edição Cancelada");  
  
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
 
}