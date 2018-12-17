package serviceConvenienciaGuinnesBeer;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConvenienciaGuinnesBeer.ItensVendaProduto;
import util.Transacional;

public class ItensProdutoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ItensVendaProduto> dao;
	
	@Transacional
	public void inserirAlterar(ItensVendaProduto entidade){
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
