package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.inject.Inject;
import javax.persistence.EntityManager;
 
public class UsuarioDAO implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
 
	public List retornarLogado(Class classe, String usuario) {
		Query q = null;
 
			q = manager.createQuery(
					"from " + classe.getSimpleName() + " where status is true and nomeUsuario = '" + usuario + "'");

		 
		return q.getResultList();
	}

}
