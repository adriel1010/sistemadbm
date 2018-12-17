package serviceDBM;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloDBM.TipoDBM;
import util.Transacional;

public class TipoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<TipoDBM> daoTipo;
	
	@Transacional
	public void inserirAlterar(TipoDBM tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}
	
	@Transacional
	public void update(String valor){
		daoTipo.update(valor);
	}

}
