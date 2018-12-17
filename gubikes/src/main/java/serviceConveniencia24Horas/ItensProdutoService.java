package serviceConveniencia24Horas;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConveniencia24horas.ItensVendaProdutoConveniencia24Horas;
import util.Transacional;

public class ItensProdutoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ItensVendaProdutoConveniencia24Horas> dao;
	
	@Transacional
	public void inserirAlterar(ItensVendaProdutoConveniencia24Horas entidade){
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
