package modeloConveniencia24horas;

import java.io.Serializable;
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
@Table(name = "tab_permissao")
public class PermissaoConveniencia24Horas implements Serializable {

	public PermissaoConveniencia24Horas() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_permissao")
	private Long id;
	
	@Column(nullable=false)
	private Date dataInclusao;
	
	@Column(nullable=false)
    private Boolean status;
	
	@JoinColumn(name = "id_usuario", nullable = false)
	@ManyToOne
	private UsuarioConveniencia24Horas usuario;
	
	@JoinColumn(name = "id_tipo", nullable = false)
	@ManyToOne
	private TipoConveniencia24Horas tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public UsuarioConveniencia24Horas getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioConveniencia24Horas usuario) {
		this.usuario = usuario;
	}

	public TipoConveniencia24Horas getTipo() {
		return tipo;
	}

	public void setTipo(TipoConveniencia24Horas tipo) {
		this.tipo = tipo;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

 
	
	

}
