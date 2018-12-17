package serviceDBM;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloDBM.VendasDBM;
import util.Transacional;

public class VendaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<VendasDBM> dao;
	
	@Transacional
	public void inserirAlterar(VendasDBM entidade){
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
