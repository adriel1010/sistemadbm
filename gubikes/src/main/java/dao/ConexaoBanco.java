package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.internal.EntityManagerImpl;

	
	
	public class ConexaoBanco {
	    
	
	private static ConexaoBanco conexao;
	private EntityManager em;

	public ConexaoBanco() {
		em = Persistence.createEntityManagerFactory("cronosPU").createEntityManager();
	}

	public synchronized static ConexaoBanco getConexao() {
		if (conexao == null) {
			conexao = new ConexaoBanco();
		}
		return conexao;
	}
	


	public EntityManager getEm() {
		return em;
	}

	public Connection getConnection() {
		EntityManagerImpl factory = (EntityManagerImpl) em;
		SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) factory.getSession().getSessionFactory();
		try {
			return sessionFactoryImpl.getConnectionProvider().getConnection();
		} catch (SQLException ex) {
			Logger.getLogger(ConexaoBanco.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
	
	
	  /* //classe responsavel em criar o gerenciador de entidades "entity manager", ele � muito pesado por isso s� cria um, para
		    //criar um utilizamos o static. 
		    
		    private static EntityManagerFactory fabrica;
		    
		    
		    static{
		    	
		        fabrica = Persistence.createEntityManagerFactory("cronosPU");
		    }
		    
		    //o atributo � private ent�o temos que criar o metodo para retornar a fabrica 
		    
		    public static  EntityManagerFactory getConexao(){
		        return fabrica;
		    }
		    
		    
		/*	public static Connection getConnection() {
				EntityManagerImpl factory = (EntityManagerImpl) fabrica;
				SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) factory.getSession().getSessionFactory();
				try {
					return sessionFactoryImpl.getConnectionProvider().getConnection();
				} catch (SQLException ex) {
					Logger.getLogger(ConexaoBanco.class.getName()).log(Level.SEVERE, null, ex);
				}
				return null;
			}*/
		    
		    
		//}
