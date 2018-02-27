package br.com.megusta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jmine.tec.component.Documentation;
import jmine.tec.persist.api.authorization.annotation.DisplayName;
import jmine.tec.persist.impl.annotation.Alias;
import jmine.tec.persist.impl.annotation.Constraint;
import jmine.tec.persist.impl.bussobj.PersistableBusinessObject;
import jmine.tec.utils.date.Date;

import org.hibernate.annotations.Type;
import org.hibernate.validator.NotNull;

@SuppressWarnings("serial")
@Entity
@Alias("GASTOS")
@DisplayName("Gastos")
@Table(name = "GASTOS")
@Documentation("Tabela que contém os gastos")
@SequenceGenerator(name = "SEQ_GASTOS", sequenceName = "SEQ_GASTOS")
public class Gastos extends PersistableBusinessObject {
	
	private long id;

	private Date data;

	private double valor;

	private TipoGasto tipoGasto;

	private boolean fixo;
	
	private String detalheGasto;
	
	private CategoriaGasto categoriaGasto;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_GASTOS")
	@Column(name = "PK_GASTOS")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@NotNull
	@Column(nullable = false)
	@Type(type = "jmine.tec.persist.impl.hibernate.type.DateType")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@NotNull
	@Column(nullable = false)
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PK_TPGAST", nullable = false)
	public TipoGasto getTipoGasto() {
		return tipoGasto;
	}

	public void setTipoGasto(TipoGasto tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

	@NotNull
	@Column(name = "INDC_FIXO", nullable = true)
	@Documentation("INDICA SE É FIXO OU NÃO")
	public boolean isFixo() {
		return fixo;
	}

	public void setFixo(boolean fixo) {
		this.fixo = fixo;
	}

	@Column(nullable = true)
	public String getDetalheGasto() {
		return detalheGasto;
	}

	public void setDetalheGasto(String detalheGasto) {
		this.detalheGasto = detalheGasto;
	}

	@NotNull
	@Column(name = "TP_CAT_GASTO")
	@Constraint(suffix = "1")
	@Documentation("TIPO DA CATEGORIA DE GASTO : 0 (PESSOAL), 1 (PALETA_INGREDIENTE), 2(PALETA_ESTRUTURA)")
	public CategoriaGasto getCategoriaGasto() {
		return categoriaGasto;
	}

	public void setCategoriaGasto(CategoriaGasto categoriaGasto) {
		this.categoriaGasto = categoriaGasto;
	}
}