package modeloConvenienciaGuinnesBeer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tab_produto")
public class Produtos implements Serializable {
 
		public Produtos() {
			super();
		}

		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id_produto")
		private Long id;

		@NotNull(message = "O campo nome não pode ser nulo")
		@Column(nullable = false)
		private String nome;
		
		@Digits(integer=6, fraction=2 , message="Informe um valor válido para o campo preço compra")
		@Column(nullable=false, precision = 7, scale = 2)
		private BigDecimal valorCompra;
		
		@Digits(integer=6, fraction=2 , message="Informe um valor válido para o campo preço compra")
		@Column(nullable=false, precision = 7, scale = 2)
		private BigDecimal valorVenda;
		
		@Digits(integer=6, fraction=2 , message="Informe um valor válido para o campo preço 2")
		@Column(nullable=false, precision = 7, scale = 2)
		private BigDecimal preco2;
		 
		private String codBarra;
		
	 

		@Column(nullable = false)
		private Boolean status;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public BigDecimal getValorCompra() {
			return valorCompra;
		}

		public void setValorCompra(BigDecimal valorCompra) {
			this.valorCompra = valorCompra;
		}

		public BigDecimal getValorVenda() {
			return valorVenda;
		}

		public void setValorVenda(BigDecimal valorVenda) {
			this.valorVenda = valorVenda;
		}
 

		public Boolean getStatus() {
			return status;
		}

		public void setStatus(Boolean status) {
			this.status = status;
		}

		public BigDecimal getPreco2() {
			return preco2;
		}

		public void setPreco2(BigDecimal preco2) {
			this.preco2 = preco2;
		}

		public String getCodBarra() {
			return codBarra;
		}

		public void setCodBarra(String codBarra) {
			this.codBarra = codBarra;
		}

		
		
}
