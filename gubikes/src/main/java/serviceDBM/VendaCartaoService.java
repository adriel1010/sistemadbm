package serviceDBM;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloDBM.VendasDBM;
import modeloDBM.VendasCartaoDBM;
import util.Transacional;

public class VendaCartaoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<VendasCartaoDBM> dao;
	
	@Transacional
	public void inserirAlterar(VendasCartaoDBM entidade){
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
