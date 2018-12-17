package modeloDBM;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

 
@Entity
@Table(name = "tab_itensVendaProdutoCartao")
public class ItensVendaProdutoCartaoDBM implements Serializable {

	public ItensVendaProdutoCartaoDBM() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_itensVendaProdutoCartao")
	private Long id;
	
 	
	@Column(nullable=false)
    private Boolean status;
	
	@JoinColumn(name = "id_produto", nullable = false)
	@ManyToOne
	private ProdutosDBM produto;
	
	private String observacao;
	
	@JoinColumn(name = "id_venda", nullable = false)
	@ManyToOne
	private VendasCartaoDBM vendasCartao;
	
	@JoinColumn(name = "data")
	private Date dataVenda;
	
	
	private Integer quantidade;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal valorUnitario;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal subTotalItem;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal lucroUnitario;
	
	private Integer codigoItem;
	
	private String somar; 

	public Long getId() {
		return id;
	}
	
	
	

	public String getObservacao() {
		return observacao;
	}


	
	


	public String getSomar() {
		return somar;
	}




	public void setSomar(String somar) {
		this.somar = somar;
	}




	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}




	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public ProdutosDBM getProduto() {
		return produto;
	}

	public void setProduto(ProdutosDBM produto) {
		this.produto = produto;
	}

 

	public VendasCartaoDBM getVendasCartao() {
		return vendasCartao;
	}

	public void setVendasCartao(VendasCartaoDBM vendasCartao) {
		this.vendasCartao = vendasCartao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getLucroUnitario() {
		return lucroUnitario;
	}

	public void setLucroUnitario(BigDecimal lucroUnitario) {
		this.lucroUnitario = lucroUnitario;
	}

	public Integer getCodigoItem() {
		return codigoItem;
	}

	public void setCodigoItem(Integer codigoItem) {
		this.codigoItem = codigoItem;
	}

	public BigDecimal getSubTotalItem() {
		return subTotalItem;
	}

	public void setSubTotalItem(BigDecimal subTotalItem) {
		this.subTotalItem = subTotalItem;
	}
	

 
	

}
