package modeloDBM;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import util.EntityManagerProducer;

public class TesteDBM    implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private  EntityManagerProducer ent;
	 
	 
	
	public static void sds(){
    
	}



	public EntityManagerProducer getEnt() {
		return ent;
	}



	public void setEnt(EntityManagerProducer ent) {
		this.ent = ent;
	}
     

}
