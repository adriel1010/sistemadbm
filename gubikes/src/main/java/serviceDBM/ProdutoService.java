package serviceDBM;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloDBM.ProdutosDBM;
import util.Transacional;

public class ProdutoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ProdutosDBM> daoProduto;
	
	@Transacional
	public void inserirAlterar(ProdutosDBM produto){
		if(produto.getId()==null){
			daoProduto.inserir(produto);
		}else{
			daoProduto.alterar(produto);
		}
	}
	
	@Transacional
	public void update(String valor){
		daoProduto.update(valor);
	}

}
