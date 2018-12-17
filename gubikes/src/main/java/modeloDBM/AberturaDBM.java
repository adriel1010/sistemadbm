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
@Table(name = "tab_abertura")
public class AberturaDBM implements Serializable {
 
		public AberturaDBM() {
			super();
		}

		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id_abertura")
		private Long id;

		
		@Column(nullable = false)
		private Boolean status;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal valorEntrada;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal valorFechamento;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal lucroDiario;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal ValorFechamentoCartao;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal valorFechamentoDinheiro;
		
		
		@Column(precision = 7, scale = 2)
		private BigDecimal lucroDiarioCartaoNotas;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal valorFechamentoCartaoNotas;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal lucroDiarioTotal;
		
		@Column(precision = 7, scale = 2)
		private BigDecimal valorFechamentoTotal;
		
		
		 
		private String dataAbertura;
		  
		private String situacao;
		
		private String mes; 
		
		private String ano; 
 
		
		@JoinColumn(name = "id_usuario")
		@ManyToOne
		private UsuarioDBM usuario;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		

		public BigDecimal getLucroDiarioCartaoNotas() {
			return lucroDiarioCartaoNotas;
		}

		public void setLucroDiarioCartaoNotas(BigDecimal lucroDiarioCartaoNotas) {
			this.lucroDiarioCartaoNotas = lucroDiarioCartaoNotas;
		}

		public BigDecimal getValorFechamentoCartaoNotas() {
			return valorFechamentoCartaoNotas;
		}

		public void setValorFechamentoCartaoNotas(BigDecimal valorFechamentoCartaoNotas) {
			this.valorFechamentoCartaoNotas = valorFechamentoCartaoNotas;
		}

		public BigDecimal getLucroDiarioTotal() {
			return lucroDiarioTotal;
		}

		public void setLucroDiarioTotal(BigDecimal lucroDiarioTotal) {
			this.lucroDiarioTotal = lucroDiarioTotal;
		}

		public BigDecimal getValorFechamentoTotal() {
			return valorFechamentoTotal;
		}

		public void setValorFechamentoTotal(BigDecimal valorFechamentoTotal) {
			this.valorFechamentoTotal = valorFechamentoTotal;
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

		public BigDecimal getValorEntrada() {
			return valorEntrada;
		}

		public void setValorEntrada(BigDecimal valorEntrada) {
			this.valorEntrada = valorEntrada;
		}

		public BigDecimal getValorFechamento() {
			return valorFechamento;
		}

		public void setValorFechamento(BigDecimal valorFechamento) {
			this.valorFechamento = valorFechamento;
		}

		public BigDecimal getLucroDiario() {
			return lucroDiario;
		}

		public void setLucroDiario(BigDecimal lucroDiario) {
			this.lucroDiario = lucroDiario;
		}

		public BigDecimal getValorFechamentoCartao() {
			return ValorFechamentoCartao;
		}

		public void setValorFechamentoCartao(BigDecimal valorFechamentoCartao) {
			ValorFechamentoCartao = valorFechamentoCartao;
		}

		public BigDecimal getValorFechamentoDinheiro() {
			return valorFechamentoDinheiro;
		}

		public void setValorFechamentoDinheiro(BigDecimal valorFechamentoDinheiro) {
			this.valorFechamentoDinheiro = valorFechamentoDinheiro;
		}

		
	 
		public String getDataAbertura() {
			return dataAbertura;
		}

		public void setDataAbertura(String dataAbertura) {
			this.dataAbertura = dataAbertura;
		}

		public String getAno() {
			return ano;
		}

		public void setAno(String ano) {
			this.ano = ano;
		}

		public UsuarioDBM getUsuario() {
			return usuario;
		}

		public void setUsuario(UsuarioDBM usuario) {
			this.usuario = usuario;
		}
 
 
		
		
}
