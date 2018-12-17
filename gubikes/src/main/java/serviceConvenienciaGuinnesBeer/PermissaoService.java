package serviceConvenienciaGuinnesBeer;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConvenienciaGuinnesBeer.Permissao;
import util.Transacional;

public class PermissaoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Permissao> daoPermissoes;
	
	@Transacional
	public void inserirAlterar(Permissao tipo){
		if(tipo.getId()==null){
			daoPermissoes.inserir(tipo);
		}else{
			daoPermissoes.alterar(tipo);
		}
	}
	
	@Transacional
	public void update(String valor){
		daoPermissoes.update(valor);
	}

}
