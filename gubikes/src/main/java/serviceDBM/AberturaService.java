package serviceDBM;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloDBM.AberturaDBM;
import util.Transacional;

public class AberturaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<AberturaDBM> daoAbertura;
	
	@Transacional
	public void inserirAlterar(AberturaDBM abertura){
		if(abertura.getId()==null){
			daoAbertura.inserir(abertura);
		}else{
			daoAbertura.alterar(abertura);
		}
	}
	
	@Transacional
	public void update(String valor){
		daoAbertura.update(valor);
	}

}
