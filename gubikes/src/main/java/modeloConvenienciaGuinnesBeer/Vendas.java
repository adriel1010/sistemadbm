package modeloConvenienciaGuinnesBeer;

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
@Table(name = "tab_venda")
public class Vendas implements Serializable {
 
		public Vendas() {
			super();
		}

		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id_venda")
		private Long id;

		
		@Column(nullable = false)
		private Boolean status;
		
		
		private String mes;
		
		private String tipoPagamento;
		
		private String ano;
		 
		private String situacao;
		
		private String situacaoAbertura;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal subTotal;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal lucroVenda;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal valorTotal;
		 
		 
		private String dataVenda;
 
		@NotNull(message = "O campo usuario não pode ser nulo")
		@JoinColumn(name = "id_usuario", nullable = false)
		@OneToOne
		private Usuario usuario;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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

		public String getTipoPagamento() {
			return tipoPagamento;
		}

		public void setTipoPagamento(String tipoPagamento) {
			this.tipoPagamento = tipoPagamento;
		}

		public BigDecimal getSubTotal() {
			return subTotal;
		}

		public void setSubTotal(BigDecimal subTotal) {
			this.subTotal = subTotal;
		}

		public BigDecimal getValorTotal() {
			return valorTotal;
		}

		public void setValorTotal(BigDecimal valorTotal) {
			this.valorTotal = valorTotal;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

		public BigDecimal getLucroVenda() {
			return lucroVenda;
		}

		public void setLucroVenda(BigDecimal lucroVenda) {
			this.lucroVenda = lucroVenda;
		}

	 


	

 
		
		
}
