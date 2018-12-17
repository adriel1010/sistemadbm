package br.com.cronos.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException; 
import javax.inject.Inject;
import javax.inject.Named;
 
import dao.GenericDAO;
import modeloConvenienciaGuinnesBeer.Produtos;
import util.ExibirMensagem;
import util.Mensagem;

@Named("converterProduto")
public class ConverteProduto implements Converter {

	
	@Inject
	private GenericDAO<Produtos> dao;
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		 
		if (value != null && value.trim().length() > 0) { 
			try {
				
				return  dao.buscarPorId(Produtos.class, Long.parseLong(value));
			
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConverterException(
						
						new FacesMessage(FacesMessage.SEVERITY_ERROR, Mensagem.ERRO_CONVERTER, ""));
				
			}
		}  
		
		 
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			Produtos cur = (Produtos) object;
			return String.valueOf(cur.getId());
		} else {
			return null;
		}
	}
}
