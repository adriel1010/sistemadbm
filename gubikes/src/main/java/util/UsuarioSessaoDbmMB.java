package util;
 
import dao.UsuarioDAO;
import modeloDBM.UsuarioDBM;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@SessionScoped
@Named("usuarioSessaoDbmMB")
public class UsuarioSessaoDbmMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UsuarioDBM usuario; 
	


	@Inject
	private UsuarioDAO daoUsuario;

	
	 public UsuarioSessaoDbmMB() { 
		 System.out.println("no usuario");
		usuario = new UsuarioDBM();
		SecurityContext context = SecurityContextHolder.getContext();
		if (context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if (authentication instanceof Authentication) {
				usuario.setNomeUsuario(((User) authentication.getPrincipal()).getUsername());
			}
		}
		
 
		
	}


	public UsuarioDBM getUsuario() {
		return usuario;
	}


	public void setUsuario(UsuarioDBM usuario) {
		this.usuario = usuario;
	}

  

 
	public UsuarioDBM recuperarUsuario() { 
		  
		
		try {
			usuario = (UsuarioDBM) daoUsuario.retornarLogado(UsuarioDBM.class, usuario.getNomeUsuario()).get(0);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
		
	}

 
 

 

}
