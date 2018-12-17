package serviceConveniencia24Horas;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConveniencia24horas.PermissaoConveniencia24Horas;
import util.Transacional;

public class PermissaoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<PermissaoConveniencia24Horas> daoPermissoes;
	
	@Transacional
	public void inserirAlterar(PermissaoConveniencia24Horas tipo){
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
