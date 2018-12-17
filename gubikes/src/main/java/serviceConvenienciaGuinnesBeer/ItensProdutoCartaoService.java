package serviceConvenienciaGuinnesBeer;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConvenienciaGuinnesBeer.ItensVendaProduto;
import modeloConvenienciaGuinnesBeer.ItensVendaProdutoCartao;
import util.Transacional;

public class ItensProdutoCartaoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ItensVendaProdutoCartao> dao;
	
	@Transacional
	public void inserirAlterar(ItensVendaProdutoCartao entidade){
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
