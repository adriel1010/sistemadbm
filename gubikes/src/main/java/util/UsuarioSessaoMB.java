package util;
 
import dao.UsuarioDAO; 
import modeloConvenienciaGuinnesBeer.Usuario;

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
@Named("usuarioSessaoMB")
public class UsuarioSessaoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario; 
	


	@Inject 
	private UsuarioDAO daoUsuario;

	
	 public UsuarioSessaoMB() { 
		 System.out.println("no usuario");
		usuario = new Usuario();
		SecurityContext context = SecurityContextHolder.getContext();
		if (context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if (authentication instanceof Authentication) {
				usuario.setNomeUsuario(((User) authentication.getPrincipal()).getUsername());
			}
		}
		
 
		
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

  

 
	public Usuario recuperarUsuario() { 
		  
		
		try {
			usuario = (Usuario) daoUsuario.retornarLogado(Usuario.class, usuario.getNomeUsuario()).get(0);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
		
	}

 
 

 

}
