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
import jmine.tec.persist.impl.bussobj.PersistableBusinessObject;

import org.hibernate.validator.NotNull;

@SuppressWarnings("serial")
@Entity
@Alias("TPGAST")
@DisplayName("Tipo Gastos")
@Table(name = "TPGAST")
@Documentation("Tabela que contém os tipos de gastos")
@SequenceGenerator(name = "SEQ_TPGAST", sequenceName = "SEQ_TPGAST")
public class TipoGasto extends PersistableBusinessObject {

	private long id;

	private String tipoGasto;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_TPGAST")
	@Column(name = "PK_TPGAST")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@NotNull
	@Column(nullable = false)
	public String getTipoGasto() {
		return tipoGasto;
	}

	public void setTipoGasto(String tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

}
