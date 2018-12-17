package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.lucene.analysis.core.TypeTokenFilter;
import org.glassfish.hk2.api.ServiceLocator;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.primefaces.mobile.component.footer.Footer;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import dao.GenericDAO;
import modeloDBM.ProdutosDBM;
import util.CriptografiaSenha;

@Path("/service")
public class ServicoHello {

	@Inject
	private GenericDAO<ProdutosDBM> daoProdutosDBM;

	/*
	 * @POST
	 * 
	 * @Produces("application/json; charset=UTF-8")
	 * 
	 * @Consumes({ "application/json; charset=UTF-8" })
	 * 
	 * @Path("/logar") public String verificaLogin(ProdutosDBM servidor) {
	 * 
	 * List<Servidor> listServidor = new ArrayList<>(); listServidor =
	 * daoServidor.listar(Servidor.class, " usuario = '" + login + "'");
	 * 
	 * if (listServidor.size() > 0) {
	 * 
	 * if (CriptografiaSenha.decptrografar(senha,
	 * listServidor.get(0).getSenha())) { return "" +
	 * listServidor.get(0).getId(); } else return "" + 0;
	 * 
	 * } else { return "" + 0; }
	 * 
	 * }
	 */

	/*
	 * @POST
	 * 
	 * @Produces("application/json; charset=UTF-8")
	 * 
	 * @Consumes({ "application/json; charset=UTF-8" })
	 * 
	 * @Path("/logar/{login}/{senha}") public String
	 * verificaLogin(@PathParam("login") String login, @PathParam("senha")
	 * String senha) {
	 * 
	 * List<Servidor> listServidor = new ArrayList<>(); listServidor =
	 * daoServidor.listar(Servidor.class, " usuario = '" + login + "'");
	 * 
	 * if (listServidor.size() > 0) {
	 * 
	 * if (CriptografiaSenha.decptrografar(senha,
	 * listServidor.get(0).getSenha())) { return "" +
	 * listServidor.get(0).getId(); } else return "" + 0;
	 * 
	 * } else { return "" + 0; }
	 * 
	 * }
	 */
	
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/buscarProdutos")
	public Response buscarProdutos() {

		List<ProdutosDBM> listaProduto = daoProdutosDBM.listaComStatus(ProdutosDBM.class);
		GenericEntity<List<ProdutosDBM>> lista = new GenericEntity<List<ProdutosDBM>>(listaProduto) {
		};

		return Response.status(200).entity(lista).build();

	}

	@POST
	@Produces("application/json; charset=UTF-8")
	@Consumes({ "application/json; charset=UTF-8" })
	@Path("/consultaProduto/{codigo}")
	public Response consultaProduto(@PathParam("codigo") String codigo) {

		List<ProdutosDBM> listaProduto = daoProdutosDBM.listar(ProdutosDBM.class, " codBarra = '" + codigo + "'");

		GenericEntity<List<ProdutosDBM>> lista = new GenericEntity<List<ProdutosDBM>>(listaProduto) {
		};

		return Response.status(200).entity(lista).build();

	}

	/*
	 * @POST
	 * 
	 * @Produces("application/json; charset=UTF-8")
	 * 
	 * @Consumes({ "application/json; charset=UTF-8" })
	 * 
	 * @Path("/locaisConferir/{idInventario}/{idServidor}") public Response
	 * locaisConferir(@PathParam("idInventario") Integer idInventario,
	 * 
	 * @PathParam("idServidor") Integer idServidor) {
	 * 
	 * List<LocalInventario> listaLocalInventario =
	 * daoLocaisInventario.listar(LocalInventario.class, " Inventario = '" +
	 * idInventario + "' and servidorConferencia = '" + idServidor + "'");
	 * 
	 * GenericEntity<List<LocalInventario>> lista = new
	 * GenericEntity<List<LocalInventario>>(listaLocalInventario) { };
	 * 
	 * return Response.status(200).entity(lista).build();
	 * 
	 * }
	 */

	@POST
	@Produces("application/json; charset=UTF-8")
	@Consumes({ "application/json; charset=UTF-8" })
	@Path("/listaConferencia")
	public String listaConferencia(List<ProdutosDBM> lista) {
		/*
		 * Gson gson = new Gson();
		 * 
		 * String listaString = gson.toJson(lista, new
		 * TypeToken<List<EquipamentoInventario>>() { }.getType());
		 * 
		 * System.out.println("sss " + listaString);
		 * 
		 * List<EquipamentoInventario> listasConvertida =
		 * gson.fromJson(listaString, new
		 * TypeToken<List<EquipamentoInventario>>() { }.getType());
		 * EquipamentoInventario eee = new EquipamentoInventario();
		 * List<Tombamento> listaTombamento =
		 * daoTombamento.listaComStatus(Tombamento.class);
		 * List<EquipamentoInventario> listaEquipamentoInventario =
		 * daoEquipamentoInventario
		 * .listaComStatus(EquipamentoInventario.class);
		 * 
		 * 
		 * for (EquipamentoInventario e : listasConvertida) {
		 * 
		 * Tombamento t = null; boolean salvar = true; for
		 * (EquipamentoInventario equipamento : listaEquipamentoInventario) { if
		 * (equipamento.getTombamento().getCodigo().equals(e.getTombamento().
		 * getCodigo()) && equipamento
		 * .getLocalInventario().getInventario().getId() ==
		 * e.getLocalInventario().getInventario().getId()) salvar = false;
		 * 
		 * } if (salvar) { for (Tombamento tombamento : listaTombamento) { if
		 * (tombamento.getCodigo().equals(e.getTombamento().getCodigo())) { t =
		 * tombamento; break; } } if (t != null) {
		 * 
		 * System.out.println("tombamento para inserir " + t.getCodigo()); try {
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Date date
		 * = sdf.parse(e.getDataConferenciaFormatada());
		 * e.setDataConferencia(date); } catch (ParseException e1) {
		 * e1.printStackTrace(); }
		 * 
		 * if (e.getLocalInventario().getLocal().getId() ==
		 * t.getLocal().getId()) e.setPertenceLocal(true); else
		 * e.setPertenceLocal(false);
		 * 
		 * e.setTombamento(t);
		 * 
		 * equipamentoInventarioService.inserirAlterar(e);
		 * 
		 * } else { System.out.println("tombamento não encontrado "); } } }
		 */

		return "";

	}

	// http://localhost:8080/hellows/rest/service/somarInteiros?valor=1&valor2=3
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/somarInteiros")
	public String helloWebService(@QueryParam("valor") Integer valor, @QueryParam("valor2") Integer valor2) {
		// return (valor+valor2);
		return "" + (valor + valor2);

	}

	// http://localhost:8080/hellows/rest/service/hello?valor=1&valor2=3
	@GET
	@Produces("application/json; charset=UTF-8")
	// @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	@Path("/listaTimesSP")
	public Response listaTimes() {

		List<Time> lt = new ArrayList<>();
		lt.add(new Time("Palmeiras - Maior Campeão Brasileiro", "São Paulo"));
		lt.add(new Time("São Paulo", "São Paulo"));
		lt.add(new Time("Corinthians", "São Paulo"));
		lt.add(new Time("Santos", "São Paulo"));
		lt.add(new Time("Ituano", "São Paulo"));
		lt.add(new Time("Linense", "São Paulo"));
		lt.add(new Time("aaaaa", "São Paulo"));

		GenericEntity<List<Time>> lista = new GenericEntity<List<Time>>(lt) {
		};
		// return lt;
		return Response.status(200).entity(lista).build();
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listaTimesRio")
	public List<Time> listaTimes2() {

		List<Time> lt = new ArrayList<>();

		lt.add(new Time("Flamengo", "Rio de Janeiro"));
		lt.add(new Time("Vasco", "Rio de Janeiro"));

		// GenericEntity<List<Time>> lista = new
		// GenericEntity<List<Time>>(lt){};
		return lt;
		// return Response.status(200).entity(lista).build();
	}

	// http://localhost:8080/hellows/rest/service/hello2/10/10
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/hello2/{valor}/{valor2}")
	public String helloWebService2(@PathParam("valor") Integer valor, @PathParam("valor2") Integer valor2) {
		return "Olá Mundo WebService " + (valor + valor2);
	}

	// http://localhost:8080/hellows/rest/service/hello3/10/
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/hello3/{valor}")
	public String helloWebService3(@PathParam("valor") Integer valor) {
		return "Olá Mundo WebService " + valor;
	}

}
