package serviceDBM;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloDBM.ItensVendaProdutoDBM;
import modeloDBM.ItensVendaProdutoCartaoDBM;
import util.Transacional;

public class ItensProdutoCartaoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ItensVendaProdutoCartaoDBM> dao;
	
	@Transacional
	public void inserirAlterar(ItensVendaProdutoCartaoDBM entidade){
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
