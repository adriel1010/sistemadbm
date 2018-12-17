package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConveniencia24horas.ProdutosConveniencia24Horas;
import modeloConveniencia24horas.UsuarioConveniencia24Horas;
import modeloConvenienciaGuinnesBeer.*;
import modeloDBM.ProdutosDBM;
import modeloDBM.UsuarioDBM;;

public class ValidaCadastro implements Serializable{
	
	
	private static final long serialVersionUID = 1L;


	/*@Inject
	private GenericDAO<Cidade> daoCidade; //faz as buscas
	
	@Inject
	private GenericDAO<Bairro> daoBairro;
	
	@Inject
	private GenericDAO<Pessoas> daoCliente; 
	*/
	@Inject
	private GenericDAO<Usuario> daoUsuario; 
	
	@Inject
	private GenericDAO<UsuarioConveniencia24Horas> daoUsuarioConveniencia24horas; 
	
	@Inject
	private GenericDAO<UsuarioDBM> daoUsuarioDBM; 
	@Inject
	private GenericDAO<Produtos> daoProduto;
	
	@Inject
	private GenericDAO<ProdutosConveniencia24Horas> daoProdutosConveniencia24Horas;
	
	@Inject
	private GenericDAO<ProdutosDBM> daoProdutoDBM;
	
	/*@Inject
	private GenericDAO<Funcionario> daoFuncionario;
	

	public Boolean buscarCidade(Cidade cidade) {
		List<Cidade> cidades = new ArrayList<>();
		try {
			cidades = daoCidade.listarCadastro(Cidade.class, " status is true and nome = '" + cidade.getNome() + "' and estado = '"+cidade.getEstado()+"'");
			if (cidades.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarCidade");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validaExcluirCliente(Long id){
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			funcionarios = daoFuncionario.listarCadastro(Funcionario.class, " statusFuncionario is true and pessoa = '" +id+"'");
			if (funcionarios.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarCidade");
			e.printStackTrace();
		}
		return false;
	
	}
	
	public boolean validaAdicionaFuncionarioCliente(Long id){
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			funcionarios = daoFuncionario.listarCadastro(Funcionario.class, " controleFuncionario = 1 and pessoa = '" +id+"'");
			if (funcionarios.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarCidade");
			e.printStackTrace();
		}
		return false;
	
	}
	
	public boolean validaAdicionaClienteFuncionario(Long id){
		List<Cliente> funcionarios = new ArrayList<>();
		try {
			funcionarios = daoFuncionario.listarCadastro(Cliente.class, " controleCliente = 1 and pessoa = '" +id+"'");
			if (funcionarios.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarCidade");
			e.printStackTrace();
		}
		return false;
	
	}
	
	public boolean validaExcluirFuncionario(Long id){
		List<Cliente> funcionarios = new ArrayList<>();
		try {
			funcionarios = daoFuncionario.listarCadastro(Cliente.class, " statusCliente is true and pessoa = '" +id+"'");
			if (funcionarios.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarCidade");
			e.printStackTrace();
		}
		return false;
	
	}
	
	
	public Boolean buscarCidadeAlterar(Cidade cidade) {
		List<Cidade> cidades = new ArrayList<>();
		try {
			cidades = daoCidade.listarCadastro(Cidade.class, " status is true and nome = '" + cidade.getNome() + "' and estado = '"+cidade.getEstado()+"' "
					+ " and id = "+cidade.getId());
			if (cidades.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarCidade");
			e.printStackTrace();
		}
		return true;
	}
	
	public Boolean buscarBairro(Bairro bairro) {
		List<Bairro> bairos = new ArrayList<>();
		try {
			bairos = daoBairro.listarCadastro(Bairro.class, " status is true and nome = '" + bairro.getNome() + "' and cidade = "+bairro.getCidade().getId());
			if (bairos.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarBairroAlterar(Bairro bairro) {
		List<Bairro> bairos = new ArrayList<>();
		try {
			bairos = daoBairro.listarCadastro(Bairro.class, " status is true and nome = '" + bairro.getNome() + "' and cidade = "+bairro.getCidade().getId()+" "
					+ " and id = "+bairro.getId());
			if (bairos.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return true;
	}
	
	
	public Boolean buscarClienteCpf(Pessoas pessoa) {
		List<Pessoas> pes = new ArrayList<>();
		try {
			pes = daoCliente.listarCodicaoLivre(Pessoas.class, " status is true and cpf = '" +pessoa.getCpf()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarClienteEmail(Pessoas pessoa) {
		List<Pessoas> pes = new ArrayList<>();
		try {
			pes = daoCliente.listarCodicaoLivre(Pessoas.class, " status is true and usuario = '" +pessoa.getUsuario()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarFuncionarioEmail(Pessoas pessoa) {
		List<Pessoas> pes = new ArrayList<>();
		try {
			pes = daoCliente.listarCadastro(Pessoas.class, " status is true and usuario = '" +pessoa.getUsuario()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}*/
	
	public Boolean buscarUsuario(Usuario usuario) {
		List<Usuario> pes = new ArrayList<>();
		try {
			pes = daoUsuario.listarCadastro(Usuario.class, " status is true and nomeUsuario = '" +usuario.getNomeUsuario()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarUsuario(UsuarioDBM usuario) {
		List<UsuarioDBM> pes = new ArrayList<>();
		try {
			pes = daoUsuarioDBM.listarCadastro(UsuarioDBM.class, " status is true and nomeUsuario = '" +usuario.getNomeUsuario()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarUsuario(UsuarioConveniencia24Horas usuario) {
		List<UsuarioConveniencia24Horas> pes = new ArrayList<>();
		try {
			pes = daoUsuarioConveniencia24horas.listarCadastro(UsuarioConveniencia24Horas.class, " status is true and nomeUsuario = '" +usuario.getNomeUsuario()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarProduto(ProdutosConveniencia24Horas produto) {
		List<ProdutosConveniencia24Horas> pes = new ArrayList<>();
		try {
			pes = daoProdutosConveniencia24Horas.listarCadastro(ProdutosConveniencia24Horas.class, " status is true and codBarra = '" +produto.getCodBarra()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarProdutoAlterar(ProdutosConveniencia24Horas produto) {
		List<ProdutosConveniencia24Horas> pes = new ArrayList<>();
		try {
			pes = daoProdutosConveniencia24Horas.listarCadastro(ProdutosConveniencia24Horas.class, " status is true and codBarra = '" +produto.getCodBarra()+"' and id = "+produto.getId());
			if (pes.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return true;
	}
	
	
	/*
	public Boolean buscarFuncionarioEmailAlterar(Funcionario pessoa) {
		List<Pessoas> pes = new ArrayList<>();
		try {
			pes = daoCliente.listarCadastro(Pessoas.class, " status is true and usuario = '" +pessoa.getPessoa().getUsuario()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarFuncionario(Pessoas pessoa) {
		List<Pessoas> pes = new ArrayList<>();
		try {
			pes = daoFuncionario.listarCadastro(Pessoas.class, " status is true and cpf = '"+pessoa.getCpf()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	
	public Boolean buscarFuncionarioAtivar(Funcionario pessoa) {
		List<Pessoas> pes = new ArrayList<>();
		try {
			pes = daoFuncionario.listarCadastro(Funcionario.class, " statusFuncionario is false and id = "+pessoa.getId());
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarFuncionarioAlterar(Pessoas pessoa) {
		List<Pessoas> pes = new ArrayList<>();
		try {
			pes = daoFuncionario.listarCadastro(Pessoas.class, " status is true and cpf = '"+pessoa.getCpf()
			+"' and id = "+pessoa.getId());
			if (pes.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return true;
	}
	
	public Boolean buscarClienteEmailAlterar(Pessoas pessoa) {
		List<Pessoas> pes = new ArrayList<>();
		try {
			pes = daoCliente.listarCadastro(Pessoas.class, " status is true and usuario = '" +pessoa.getUsuario()+"' and id = "+pessoa.getId());
			if (pes.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return true;
	}
	
	public Boolean buscarClienteAlterarCpf(Pessoas pessoa) {
		List<Pessoas> pes = new ArrayList<>();
		try {
			pes = daoCliente.listarCadastro(Pessoas.class, " status is true and cpf = '" +pessoa.getCpf()+"' and id = "+pessoa.getId());
			if (pes.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return true;
	}*/
	
	public Boolean buscarProduto(Produtos produto) {
		List<Produtos> pes = new ArrayList<>(); 
		try {
			pes = daoProduto.listarCadastro(Produtos.class, " status is true and codBarra = '" +produto.getCodBarra()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarProduto(ProdutosDBM produto) {
		List<ProdutosDBM> pes = new ArrayList<>();
		try {
			pes = daoProdutoDBM.listarCadastro(ProdutosDBM.class, " status is true and codBarra = '" +produto.getCodBarra()+"'");
			if (pes.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean buscarProdutoAlterar(ProdutosDBM produto) {
		List<ProdutosDBM> pes = new ArrayList<>();
		try {
			pes = daoProdutoDBM.listarCadastro(ProdutosDBM.class, " status is true and codBarra = '" +produto.getCodBarra()+"' and id = "+produto.getId());
			if (pes.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return true;
	}
	
	public Boolean buscarProdutoAlterar(Produtos produto) {
		List<Produtos> pes = new ArrayList<>();
		try {
			pes = daoProduto.listarCadastro(Produtos.class, " status is true and codBarra = '" +produto.getCodBarra()+"' and id = "+produto.getId());
			if (pes.size() > 0) {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarBairro");
			e.printStackTrace();
		}
		return true;
	}
	
}
