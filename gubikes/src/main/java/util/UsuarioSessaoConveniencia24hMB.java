package util;
 
import dao.UsuarioDAO; 
import modeloConveniencia24horas.UsuarioConveniencia24Horas;

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
@Named("usuarioSessaoConveniencia24hMB")
public class UsuarioSessaoConveniencia24hMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UsuarioConveniencia24Horas usuario; 
	


	@Inject
	private UsuarioDAO daoUsuario;

	
	 public UsuarioSessaoConveniencia24hMB() { 
		 System.out.println("no usuario");
		usuario = new UsuarioConveniencia24Horas();
		SecurityContext context = SecurityContextHolder.getContext();
		if (context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if (authentication instanceof Authentication) {
				usuario.setNomeUsuario(((User) authentication.getPrincipal()).getUsername());
			}
		}
		
 
		
	}


	public UsuarioConveniencia24Horas getUsuario() {
		return usuario;
	}


	public void setUsuario(UsuarioConveniencia24Horas usuario) {
		this.usuario = usuario;
	}

  

 
	public UsuarioConveniencia24Horas recuperarUsuario() { 
		  
		
		try {
			usuario = (UsuarioConveniencia24Horas) daoUsuario.retornarLogado(UsuarioConveniencia24Horas.class, usuario.getNomeUsuario()).get(0);
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
		
	}

 
 

 

}
