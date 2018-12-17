package util;
import java.io.File;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jxl.Cell;

import jxl.NumberCell;

import jxl.Sheet;

import jxl.Workbook;

import jxl.read.biff.BiffException;
import modeloDBM.ProdutosDBM;

import javax.swing.JOptionPane;

import dao.DAOGenerico;

 

public class ImportDados{
	
	public static void main(String[] args) throws IOException, BiffException {

//	    Aluno aluno = new Aluno();
//	    AlunoTurma alunoTurma = new AlunoTurma();
//	    Movimentacao movimentacao = new Movimentacao();
//	    Turma turmaAluno = new Turma();
	 
		
		
		ProdutosDBM produtos = new ProdutosDBM();
		  
		
	    DAOGenerico dao = new DAOGenerico();
	    
	    

		Workbook workbook = Workbook.getWorkbook(new File("C:/Users/CEDI/workspace/guinesbir/gubikes/WebContent/produtos.xls"));
		
		Sheet sheet = workbook.getSheet(0);

	       

		int linhas = sheet.getRows();
		
		System.out.println("Iniciando a leitura da planilha XLS:");
		
		for(int i = 401; i < 532; i++){
			 

			Cell  codBarra = sheet.getCell(10, i);
			
			Cell  iud = sheet.getCell(0, i);
			
			Cell  nome = sheet.getCell(1, i);
			
			Cell  precoVenda = sheet.getCell(6, i);
			
			Cell  precoCompra = sheet.getCell(5, i);
			
			
			
			
			
			String id1 = iud.getContents();
			
			String nome1 = nome.getContents();

			String precoVenda1 = precoVenda.getContents();

			String precoCompra1 = precoCompra.getContents();

			String codBarra1 = codBarra.getContents();
			
			
			
			
//			abertura.setValorFechamentoCartao(new BigDecimal(0));  // valor de fechamento no cartão da pessoa 
//			abertura.setDataAbertura(String.valueOf(dataAbertura1));
//			abertura.setLucroDiario(new BigDecimal(lucroDiaDinheiro1)); 		// lucro no dinheiro "vai ficar para o cartão"
//			abertura.setSituacao("fechado");
//			abertura.setStatus(true);
//			abertura.setValorEntrada(new BigDecimal(valorAbertura1));
//			abertura.setValorFechamento(new BigDecimal(valorVendidoDinheiro1));              // valor vendido no dinheiro
//			abertura.setValorFechamentoDinheiro(new BigDecimal(valorVendidoDinheiro1));     // valor vendido no dinheiro
//			abertura.setLucroDiarioCartaoNotas(new BigDecimal(valorLucroCartao1));	// valor do lucro no cartão notinha*
//			abertura.setLucroDiarioTotal(abertura.getLucroDiarioCartaoNotas().add(abertura.getLucroDiario()));		// valor do lucro somando das notas + dinheiro + cartão da pessoa 
//			abertura.setValorFechamentoCartaoNotas(new BigDecimal(valorFechamentoCartao1)); // valor vendido no cartão notinha*
//			abertura.setValorFechamentoTotal(abertura.getValorFechamentoDinheiro().add(abertura.getValorFechamentoCartaoNotas()));  	// valor fechamento de todos os 3 junto
//			
//			Usuario u = (Usuario) dao.buscarPorId(Usuario.class, 1L);
//			abertura.setUsuario(u);
//			
			produtos.setCodBarra(codBarra1);
			produtos.setNome(nome1);
			produtos.setPreco2(new BigDecimal(precoVenda1));
			produtos.setValorVenda(new BigDecimal(precoVenda1));
			produtos.setValorCompra(new BigDecimal(precoCompra1));
			produtos.setStatus(true);
			
			
			
			dao.inserir(produtos);
			
			produtos = new ProdutosDBM();
			System.out.println("inseriu "+id1);
			 
			
//			abertura.setValorFechamentoCartao(new BigDecimal(valorFechamentoCartao1)); 
//			abertura.set
			
		/*	aluno.setCelular(celular1);
			aluno.setCpf(cpf1);
			aluno.setDataNascimento(new Date(dataNascimento1));
			aluno.setNatalidade(natalidade1);
			aluno.setNome(nome1);
			aluno.setNomeMae(nomeMae1);
			aluno.setNomePai(nomePai1);
			aluno.setOrgaoRg(orgaoRg1);
			aluno.setPerfilAluno("aluno");
			aluno.setRg(rg1);
			aluno.setSenha(CriptografiaSenha.criptografar("123"));
			aluno.setSexo(sexo1);
			aluno.setTelefone(telefone1);
			aluno.setUsuario(email1);
			aluno.setDataCadastro(new Date());
			aluno.setStatus(true);
			aluno.setPerfilAluno("aluno");
			
			dao.inserir(aluno);
			
			if(turma1.equals("BACHARELADO EM ENGENHARIA ELÉTRICA - 2017")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 4L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}else if(turma1.equals("LICENCIATURA EM QUÍMICA - 2014")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 5L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("LICENCIATURA EM QUÍMICA - 2015")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 3L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("LICENCIATURA EM QUÍMICA - 2016")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 3L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("LICENCIATURA EM QUÍMICA - 2017")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 2L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM AGROINDÚSTRIA INTEGRADO - 2014")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 8L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM AGROINDÚSTRIA INTEGRADO - 2015")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 9L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM AGROINDÚSTRIA INTEGRADO - 2016")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 10L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM AGROINDÚSTRIA INTEGRADO - 2017")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 11L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM ALIMENTOS SUBSEQUENTE - 2012")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 29L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM ELETROMECÂNICA INTEGRADO - 2014")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 12L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM ELETROMECÂNICA INTEGRADO - 2015")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 13L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM ELETROMECÂNICA INTEGRADO - 2016")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 14L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM ELETROMECÂNICA SUBSEQUENTE - 2015")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 15L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM ELETROMECÂNICA SUBSEQUENTE - 2016")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 16L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM ELETROMECÂNICA SUBSEQUENTE - 2017")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 17L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
//			else if(turma1.equals("LICENCIATURA EM QUÍMICA - 2014")){
//				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 5L);
//				
//				alunoTurma.setAluno(aluno);
//				alunoTurma.setControle(1);
//				alunoTurma.setStatus(true);
//				alunoTurma.setPermiteCadastroCertificado(1); 
//				alunoTurma.setDataMudanca(new Date());
//				alunoTurma.setRa(ra1);
//				alunoTurma.setTurma(turmaAluno);
//				
//				dao.inserir(alunoTurma);
//				
//				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
//				movimentacao.setSituacao(1);
//				movimentacao.setAlunoTurma(alunoTurma);
//				movimentacao.setStatus(true);
//				movimentacao.setControle(true);
//				
//				dao.inserir(movimentacao);
//			
//			}
			else if(turma1.equals("TÉCNICO EM INFORMÁTICA INTEGRADO - 2013")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 18L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM INFORMÁTICA INTEGRADO - 2014")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 19L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM INFORMÁTICA INTEGRADO - 2015")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 20L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM INFORMÁTICA INTEGRADO - 2016")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 21L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM INFORMÁTICA INTEGRADO - 2017")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 1L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM INFORMÁTICA SUBSEQUENTE - 2013")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 22L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM MECATRÔNICA INTEGRADO - 2017")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 23L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TÉCNICO EM MEIO AMBIENTE SUBSEQUENTE - 2016")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 24L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TECNOLOGIA EM ANÁLISE E DESENVOLVIMENTO DE SISTEMAS - 2014")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 25L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TECNOLOGIA EM ANÁLISE E DESENVOLVIMENTO DE SISTEMAS - 2015")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 26L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TECNOLOGIA EM ANÁLISE E DESENVOLVIMENTO DE SISTEMAS - 2016")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 27L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			else if(turma1.equals("TECNOLOGIA EM ANÁLISE E DESENVOLVIMENTO DE SISTEMAS - 2017")){
				turmaAluno = (Turma) dao.buscarPorId(Turma.class, 28L);
				
				alunoTurma.setAluno(aluno);
				alunoTurma.setControle(1);
				alunoTurma.setStatus(true);
				alunoTurma.setPermiteCadastroCertificado(1); 
				alunoTurma.setDataMudanca(new Date());
				alunoTurma.setRa(ra1);
				alunoTurma.setTurma(turmaAluno);
				
				dao.inserir(alunoTurma);
				
				movimentacao.setDataMovimentacao(alunoTurma.getDataMudanca());
				movimentacao.setSituacao(1);
				movimentacao.setAlunoTurma(alunoTurma);
				movimentacao.setStatus(true);
				movimentacao.setControle(true);
				
				dao.inserir(movimentacao);
			
			}
			
			aluno = new Aluno();
			alunoTurma = new AlunoTurma();
			movimentacao = new Movimentacao();
			dao = new DAOGenerico();
				
			
			System.out.println("inseriu aluno "+i+ " nome: " +nome1);
			
			
			
			
			
			
		//	System.out.println("celular: "+celular1+ " |  nome: " + nome1+" | nomeEae : " + nomeMae1 + " | nomePai : " + nomePai1 +" | turma: "+turma1 +" | ra : "+ra1);
 
		*/
		}
		
	

    

	workbook.close();

}
	
}
