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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tab_vendasCartao")
public class VendasCartaoDBM implements Serializable {
 
		public VendasCartaoDBM() {
			super();
		}

		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id_venda")
		private Long id;

		
		@Column(nullable = false)
		private Boolean status;
		
		@ManyToOne
		private UsuarioDBM usuario;
		 
		public UsuarioDBM getUsuario() {
			return usuario;
		}

		public void setUsuario(UsuarioDBM usuario) {
			this.usuario = usuario;
		}

		private String mes;
		  
		private String ano;
		 
		private String situacao;
		
		private String parcelado;
		 
		private String situacaoAbertura;
		  
		
		@Column(precision = 7, scale = 2)
		private BigDecimal lucroVenda;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal totalDinheiro;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal valorTotal;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal valorTotalAntigo;
 
		
		 
		@ManyToOne
		private CartaoDBM cartao;
		 
		 
		private String dataVenda;
 
		 

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		
	 

		public BigDecimal getValorTotalAntigo() {
			return valorTotalAntigo;
		}

		public void setValorTotalAntigo(BigDecimal valorTotalAntigo) {
			this.valorTotalAntigo = valorTotalAntigo;
		}

		public BigDecimal getTotalDinheiro() {
			return totalDinheiro;
		}

		public void setTotalDinheiro(BigDecimal totalDinheiro) {
			this.totalDinheiro = totalDinheiro;
		}

 

		public CartaoDBM getCartao() {
			return cartao;
		}
		

		public String getParcelado() {
			return parcelado;
		}

		public void setParcelado(String parcelado) {
			this.parcelado = parcelado;
		}

		public void setCartao(CartaoDBM cartao) {
			this.cartao = cartao;
		}

		public String getSituacaoAbertura() {
			return situacaoAbertura;
		}

		public void setSituacaoAbertura(String situacaoAbertura) {
			this.situacaoAbertura = situacaoAbertura;
		}

		public String getMes() {
			return mes;
		}

		public void setMes(String mes) {
			this.mes = mes;
		}

		public Boolean getStatus() {
			return status;
		}

		public void setStatus(Boolean status) {
			this.status = status;
		}

		public String getSituacao() {
			return situacao;
		}

		public void setSituacao(String situacao) {
			this.situacao = situacao;
		}

	 
		public String getDataVenda() {
			return dataVenda;
		}

		public void setDataVenda(String dataVenda) {
			this.dataVenda = dataVenda;
		}
 

		public String getAno() {
			return ano;
		}

		public void setAno(String ano) {
			this.ano = ano;
		}

	

		public BigDecimal getValorTotal() {
			return valorTotal;
		}

		public void setValorTotal(BigDecimal valorTotal) {
			this.valorTotal = valorTotal;
		}

	
		public BigDecimal getLucroVenda() {
			return lucroVenda;
		}

		public void setLucroVenda(BigDecimal lucroVenda) {
			this.lucroVenda = lucroVenda;
		}

	 


	

 
		
		
}
