package serviceConvenienciaGuinnesBeer;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConvenienciaGuinnesBeer.Produtos;
import util.Transacional;

public class ProdutoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Produtos> daoProduto;
	
	@Transacional
	public void inserirAlterar(Produtos produto){
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
