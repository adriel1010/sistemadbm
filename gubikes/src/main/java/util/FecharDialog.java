package util;

import org.primefaces.context.RequestContext;

public class FecharDialog {

	
	public static void fecharDialogTipoServidor() {
		RequestContext.getCurrentInstance().execute("PF('dlgTipoServidor').hide();");
	}
	public static void fecharDialogCidade() {
		RequestContext.getCurrentInstance().execute("PF('dlgCidade').hide();");
	}
	public static void fecharDialogBairro() {
		RequestContext.getCurrentInstance().execute("PF('dlgBairro').hide();");
	}
	
	public static void fecharDialogPreco2() {
		RequestContext.getCurrentInstance().execute("PF('dlgPreco2').hide();");
	}
	
	public static void fecharDialogCliente() {
		RequestContext.getCurrentInstance().execute("PF('dlgCliente').hide();");
	}
	
	public static void abrirDialogCliente() {
		RequestContext.getCurrentInstance().execute("PF('dlgFuncionarioSenha').show();");
	}
	
	public static void abrirDialogCaixa() {
		RequestContext.getCurrentInstance().execute("PF('dlgAbrirCaixa').show();");
	}
	
	public static void abrirDlgFechaVenda() {
		RequestContext.getCurrentInstance().execute("PF('dlgFecharVenda').show();");
	}
	
	public static void abrirDlgFechaVendaParcial() {
		RequestContext.getCurrentInstance().execute("PF('dlgFecharVendaParcial').show();");
	}
	 
	public static void abrirDlgDiversos() {
		RequestContext.getCurrentInstance().execute("PF('dlgDiversos').show();");
	}
	
	public static void fecharDlgDiversos() {
		RequestContext.getCurrentInstance().execute("PF('dlgDiversos').hide();");
	}
	
	public static void abrirDlgRemoveItem() {
		RequestContext.getCurrentInstance().execute("PF('dlgRemoveItem').show();");
	}
	
	public static void abrirDlgAlterarCartao() {
		RequestContext.getCurrentInstance().execute("PF('dlgAlteraCartao').show();");
	}
	
	public static void abrirDlgLimites() {
		RequestContext.getCurrentInstance().execute("PF('dlgLimites').show();");
	}
	public static void abrirDlgValores() {
		RequestContext.getCurrentInstance().execute("PF('dlgValores').show();");
	}
	
	
	public static void fecharDlgLimites() {
		RequestContext.getCurrentInstance().execute("PF('dlgLimites').hide();");
	}
	
	public static void abrirDlgFechar() {
		RequestContext.getCurrentInstance().execute("PF('dlgAlteraCartao').hide();");
	}
	
	public static void fechaDlgRemoveItem() {
		RequestContext.getCurrentInstance().execute("PF('dlgRemoveItem').hide();");
	}
	
	public static void fecharDlgFechaVenda() {
		RequestContext.getCurrentInstance().execute("PF('dlgFecharVenda').hide();");
	}
	
	public static void fecharDlgFechaVendaParcial() {
		RequestContext.getCurrentInstance().execute("PF('dlgFecharVendaParcial').hide();");
	}
	
	public static void focoQuantidade() {
		RequestContext.getCurrentInstance().execute("if(event.keyCode===13){document.getElementById('dadosProduto:quantidade').focus();return false;}");
		
	}
	
	public static void fecharDialogCaixa() {
		RequestContext.getCurrentInstance().execute("PF('dlgAbrirCaixa').hide();");
	}
	
	public static void abrirDialogFinalizaOrcamento() {
		RequestContext.getCurrentInstance().execute("PF('dlgOrcamentoConfirma').show();");
	}
	
	public static void abrirDialogParcela() {
		RequestContext.getCurrentInstance().execute("PF('dlgParcelamento').show();");
	}
	
	public static void fechaDialogParcela() {
		RequestContext.getCurrentInstance().execute("PF('dlgParcelamento').hide();");
	}	
	
	public static void fechaNovoCartao() {
		RequestContext.getCurrentInstance().execute("PF('dlgCartao').hide();");
	}	
	
	public static void fechaDialoAddValor() {
		RequestContext.getCurrentInstance().execute("PF('dlgValor').hide();");
	}	
	
	public static void abrirDialogObra() {
		RequestContext.getCurrentInstance().execute("PF('dlgParcelasmaoObra').show();");
	}
	
	public static void fechaDialogObra() {
		RequestContext.getCurrentInstance().execute("PF('dlgParcelasmaoObra').hide();");
	}
	
	 
	public static void fechaDialogFinalizaOrcamento() {
		RequestContext.getCurrentInstance().execute("PF('dlgOrcamentoConfirma').hide();");
	}
	
	public static void fechaDialogBaixa() {
		RequestContext.getCurrentInstance().execute("PF('dlgParcelamentoPagamento').hide();");
	}
	
	 
	
	public static void fechaDialogOrcamento() {
		RequestContext.getCurrentInstance().execute("PF('dlgOrcamento').hide();");
	}
	public static void fecharDialogClienteSenha() {
		RequestContext.getCurrentInstance().execute("PF('dlgFuncionarioSenha').hide();");
	}
	
	public static void fecharDialogFuncionarioSenha() {
		RequestContext.getCurrentInstance().execute("PF('dlgClienteSenha').hide();");
	}
	
	public static void fecharDialogProduto() {
		RequestContext.getCurrentInstance().execute("PF('dlgProduto').hide();");
	}
	
	public static void fecharDialogLimite() {
		RequestContext.getCurrentInstance().execute("PF('dlgLimites').hide();");
	}
	
	
	 

}
