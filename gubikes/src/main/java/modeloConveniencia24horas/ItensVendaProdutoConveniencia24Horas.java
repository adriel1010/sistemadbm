package modeloConveniencia24horas;

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
@Table(name = "tab_itensVendaProduto")
public class ItensVendaProdutoConveniencia24Horas implements Serializable {

	public ItensVendaProdutoConveniencia24Horas() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_itensVendaProduto")
	private Long id;
	
 	
	@Column(nullable=false)
    private Boolean status;
	
	@JoinColumn(name = "id_produto", nullable = false)
	@ManyToOne
	private ProdutosConveniencia24Horas produto;
	
	@JoinColumn(name = "id_venda", nullable = false)
	@ManyToOne
	private VendasConveniencia24Horas venda;
	
	 
	private Integer quantidade;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal valorUnitario;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal subTotalItem;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal lucroUnitario;
	
	private Integer codigoItem;

	public Long getId() {
		return id;
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

	public ProdutosConveniencia24Horas getProduto() {
		return produto;
	}

	public void setProduto(ProdutosConveniencia24Horas produto) {
		this.produto = produto;
	}

	public VendasConveniencia24Horas getVenda() {
		return venda;
	}

	public void setVenda(VendasConveniencia24Horas venda) {
		this.venda = venda;
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
