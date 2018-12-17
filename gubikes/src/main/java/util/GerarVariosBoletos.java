package util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite; 

 
public class GerarVariosBoletos {

	
	//gerar vairos boletos 
	/*
	 * http://www.jrimum.org/bopepo/wiki/Componente/Documentacao/Tutoriais/VariosBoletosEmUmArquivo
	 * https://groups.google.com/forum/#!topic/jrimum-community/CvQkI9BlfPk
	 */
	 
	 // Executa o exemplo.
 
	public static void main(String[] args) throws IOException {
		
		new GerarVariosBoletos().exemplo();
	}

	 
	  // Um simples exemplo de como gerar um boleto.
	 
	 
	final void exemplo() throws IOException {

		/*
		 * Para gerar um boleto � preciso dos dados do boleto e de um
		 * visualizador de boletos.
		 */
		
		/*
		 * Primeiro crie os boletos.
		 */
		List<Boleto> boletos = crieBoletos(10);

		/*
		 * Em seguida, gere todos em um pdf usando uma das vers�es do m�doto goupInOnePDF.
		 */
		String template = "MeuTemplate.pdf";
		byte[] boletosInOnePDF = BoletoViewer.groupInOnePdfWithTemplate(boletos, new File(template));

		/*
		 * Depois, gere um arquivo, byte array ou stream. Nesse exemplo, um
		 * arquivo ser� salvo na mesma pasta do seu "projeto"
		 */
		File arquivoPdf = new File("boletos.pdf");
		
		//Colocando conte�do no arquivo
		FileUtils.writeByteArrayToFile(arquivoPdf, boletosInOnePDF); 

		// Agora veja o arquivo gerado na tela.
		mostreBoletoNaTela(arquivoPdf);
		
	}

	/**
	 * Cria um boleto, em passos distintos, com os dados necess�rios para a visualiza��o.
	 * 
	 * @return boleto com dados
	 */
	List<Boleto> crieBoletos(int quantidade) {
		
		/*
		 * PASSO 1: Voc� precisa dos dados de uma conta banc�ria habilitada para
		 * emiss�o de boletos.
		 */	
		ContaBancaria contaBancaria = crieUmaContaBancaria();
		
		/*
		 * PASSO 2: Informe os dados do cedente (caso seja o mesmo sempre).
		 */
		Cedente cedente = crieUmCedente(); 
		
		/*
		 * PASSO 3: Informe os dados do Sacado (caso seja o mesmo sempre).
		 */
		Sacado sacado = crieUmSacado();
		
		//Crie um t�tulo para cada boleto
		List<Boleto> boletos = new ArrayList<Boleto>(quantidade);
		
		for (int numero = 1; numero <= quantidade; numero++) {
		
			/*
			 * PASSO 4: Crie um novo t�tulo/cobran�a e informe os dados.
			 */
			Titulo titulo = crieOsDadosDoNovoTitulo(new Titulo(contaBancaria,sacado,cedente), numero);

			/*
			 * PASSO 5: Crie um novo boleto com o novo t�tulo e informe os dados necess�rios.
			 */
			Boleto boleto = crieOsDadosDoNovoBoleto(new Boleto(titulo));
			
			boletos.add(boleto);
		}

		
		return boletos;
	}

	/**
	 * Preenche os principais dados do boleto.
	 * 
	 * @param boleto
	 * @return boleto com os dados necss�rios
	 */
	final Boleto crieOsDadosDoNovoBoleto(Boleto boleto) {
		
		boleto.setLocalPagamento("Pag�vel preferencialmente na Rede X ou em qualquer Banco at� o Vencimento.");
		boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor cobrado n�o � o esperado, aproveite o DESCONT�O!");
		boleto.setInstrucao1("PARA PAGAMENTO 1 at� Hoje n�o cobrar nada!");
		boleto.setInstrucao2("PARA PAGAMENTO 2 at� Amanh� N�o cobre!");
		boleto.setInstrucao3("PARA PAGAMENTO 3 at� Depois de amanh�, OK, n�o cobre.");
		boleto.setInstrucao4("PARA PAGAMENTO 4 at� 04/xx/xxxx de 4 dias atr�s COBRAR O VALOR DE: R$ 01,00");
		boleto.setInstrucao5("PARA PAGAMENTO 5 at� 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
		boleto.setInstrucao6("PARA PAGAMENTO 6 at� 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
		boleto.setInstrucao7("PARA PAGAMENTO 7 at� xx/xx/xxxx COBRAR O VALOR QUE VOC� QUISER!");
		boleto.setInstrucao8("AP�S o Vencimento, Pag�vel Somente na Rede X.");
		
		return boleto;
	}

	/**
	 * Preenche os principais dados do t�tulo.
	 * 
	 * @param titulo
	 * @param numero 
	 * 
	 * @return t�tulo com os dados necss�rios
	 */
	final Titulo crieOsDadosDoNovoTitulo(Titulo titulo, int numero) {

		/*
		 * DADOS B�SICOS.
		 */
		
		titulo.setNumeroDoDocumento(""+numero);
		titulo.setNossoNumero(String.format("%011d", numero));
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(10 + numero));
		titulo.setDataDoDocumento(new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, numero);
		titulo.setDataDoVencimento(cal.getTime());		
		titulo.setTipoDeDocumento(TipoDeTitulo.FAT_FATURA);
		titulo.setAceite(EnumAceite.A);
		
		return titulo;
	}
	
	/**
	 * Cria uma inst�ncia de sacado com os principais dados para o boleto.
	 * 
	 * @return sacado com os dados necss�rios
	 */
	final Sacado crieUmSacado() {
		
		Sacado sacado = new Sacado("Java Developer Pronto Para F�rias", "222.222.222-22");

		// Informando o endere�o do sacado.
		Endereco enderecoSac = new Endereco();
		enderecoSac.setUF(UnidadeFederativa.RN);
		enderecoSac.setLocalidade("Natal");
		enderecoSac.setCep(new CEP("59064-120"));
		enderecoSac.setBairro("Grande Centro");
		enderecoSac.setLogradouro("Rua poeta dos programas");
		enderecoSac.setNumero("1");
		sacado.addEndereco(enderecoSac);
		
		return sacado;
	}
	
	/**
	 * Cria uma inst�ncia de cedente com os principais dados para o boleto.
	 * 
	 * @return cedente com os dados necss�rios
	 */
	final Cedente crieUmCedente() {
		
		return new Cedente("Projeto JRimum", "00.000.208/0001-00");
	}
	
	/**
	 * Cria uma inst�ncia de conta banc�ria com os principais dados para o
	 * boleto em quest�o (Banco Bradesco).
	 * 
	 * @return conta com os dados necss�rios
	 */
	final ContaBancaria crieUmaContaBancaria(){
		
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "0"));
		contaBancaria.setCarteira(new Carteira(30));
		contaBancaria.setAgencia(new Agencia(1234, "1"));
		
		return contaBancaria;
	}

	/**
	 * Exibe o arquivo na tela.
	 * 
	 * @param arquivoBoleto
	 */
	final void mostreBoletoNaTela(File arquivoBoleto) {
		
		try {
			// java 6 ou superior
		     java.awt.Desktop.getDesktop().open(arquivoBoleto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
