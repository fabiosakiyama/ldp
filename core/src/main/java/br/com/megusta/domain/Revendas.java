package br.com.megusta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Alias("RVENDAS")
@DisplayName("Revendas")
@Table(name = "REVENDAS")
@Documentation("Tabela que contém as revendas")
@SequenceGenerator(name = "SEQ_RVENDAS", sequenceName = "SEQ_RVENDAS")
public class Revendas extends PersistableBusinessObject {

	private Long id;

	private Date data;

	private Double valor = new Double(0);

	private RevendaEnum revendaEnum;
	
	private int quantidade;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_RVENDAS")
	@Column(name = "PK_RVENDAS")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	@Column(nullable = true)
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@NotNull
	@Column(name = "TP_REVENDA")
	@Constraint(suffix = "1")
	@Documentation("TIPO DA REVENDA : 0 (MINUANO), 1 (PEIXE_NA_ESTRADA), 2(EVENTOS), 3(CARRINHO)")
	public RevendaEnum getRevendaEnum() {
		return revendaEnum;
	}

	public void setRevendaEnum(RevendaEnum revendaEnum) {
		this.revendaEnum = revendaEnum;
	}
}
