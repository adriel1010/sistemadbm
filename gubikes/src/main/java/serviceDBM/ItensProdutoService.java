package serviceDBM;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloDBM.ItensVendaProdutoDBM;
import util.Transacional;

public class ItensProdutoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ItensVendaProdutoDBM> dao;
	
	@Transacional
	public void inserirAlterar(ItensVendaProdutoDBM entidade){
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
