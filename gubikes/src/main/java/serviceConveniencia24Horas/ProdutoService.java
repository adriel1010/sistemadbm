package serviceConveniencia24Horas;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConveniencia24horas.ProdutosConveniencia24Horas;
import util.Transacional;

public class ProdutoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ProdutosConveniencia24Horas> daoProduto;
	
	@Transacional
	public void inserirAlterar(ProdutosConveniencia24Horas produto){
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
