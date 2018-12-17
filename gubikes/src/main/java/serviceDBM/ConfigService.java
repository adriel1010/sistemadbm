package serviceDBM;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO; 
import modeloDBM.ConfigDBM; 
import util.Transacional;

public class ConfigService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ConfigDBM> dao;
	
	@Transacional
	public void inserirAlterar(ConfigDBM entidade){
		if(entidade.getId()==null){
			dao.inserir(entidade);
		}else{
			dao.alterar(entidade);
		}
	}
	
	@Transacional
	public void update(String valor){
		dao.update(valor);
	}

}
