package br.com.megusta.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jmine.tec.component.Documentation;
import jmine.tec.persist.api.authorization.annotation.DisplayName;
import jmine.tec.persist.impl.annotation.Alias;
import jmine.tec.persist.impl.annotation.Constraint;
import jmine.tec.persist.impl.bussobj.PersistableBusinessObject;
import jmine.tec.utils.date.Timestamp;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.validator.NotNull;

@SuppressWarnings("serial")
@Entity
@Alias("PEDIDO")
@DisplayName("Pedido")
@Table(name = "PEDIDO")
@Documentation("Tabela que contém os pedidos")
@SequenceGenerator(name = "SEQ_PEDIDO", sequenceName = "SEQ_PEDIDO")
public class Pedido extends PersistableBusinessObject {

	private Long id;

	private Timestamp dataHora;

	private List<Sorvete> sorvetes = new ArrayList<Sorvete>();

	private Double valor = new Double(0);

	private Double troco = new Double(0);

	private Double valorRecebido = new Double(0);

	private int qtdCobertura;

	private int qtdTopping;

	private FormaPagamento formaPagamento;

	private Boolean entrega;
	
	private Double desconto = new Double(0);
	
	private StatusPedido statusPedido;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PEDIDO")
	@Column(name = "PK_PEDIDO")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Column(nullable = false)
	@Type(type = "jmine.tec.persist.impl.hibernate.type.TimestampType")
	public Timestamp getDataHora() {
		return this.dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	@NotNull
	@Alias("PEDSOR")
	@Cascade(CascadeType.LOCK)
	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(fetch = FetchType.EAGER)
	@DisplayName(value = "Pedido/Sorvete")
	@JoinTable(name = "PEDIDO_SORVETE", joinColumns = { @JoinColumn(name = "PK_PEDIDO", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PK_SORVET", nullable = false, updatable = false) })
	public List<Sorvete> getSorvetes() {
		return this.sorvetes;
	}

	public void setSorvetes(List<Sorvete> sorvetes) {
		this.sorvetes = sorvetes;
	}

	@NotNull
	@Column(nullable = false)
	public Double getValor() {
		return this.valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@NotNull
	@Column(nullable = false)
	public Double getTroco() {
		return this.troco;
	}

	public void setTroco(Double troco) {
		this.troco = troco;
	}

	@NotNull
	@Column(nullable = false)
	public Double getValorRecebido() {
		return this.valorRecebido;
	}

	public void setValorRecebido(Double valorRecebido) {
		this.valorRecebido = valorRecebido;
	}

	public void somaPreco(double preco) {
		this.valor += preco;
	}
	
	public void subtraiPreco(double preco) {
		this.valor -= preco;
	}

	@NotNull
	@Column(nullable = false)
	public int getQtdCobertura() {
		return this.qtdCobertura;
	}

	public void setQtdCobertura(int qtdCobertura) {
		this.qtdCobertura = qtdCobertura;
	}

	@NotNull
	@Column(nullable = false)
	public int getQtdTopping() {
		return this.qtdTopping;
	}

	public void setQtdTopping(int qtdTopping) {
		this.qtdTopping = qtdTopping;
	}

	@NotNull
	@Column(name = "TP_FORMA_PGMENTO")
	@Constraint(suffix = "1")
	@Documentation("TIPO DA FORMA DE PAGAMENTO : 0 (DINHEIRO), 1 (DEBITO), 2(CREDITO), 3(BRINDE), 4(PALITO PREMIADO), 5(CARTAO FIDELIDADE)")
	public FormaPagamento getFormaPagamento() {
		return this.formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	@NotNull
	@Column(name = "INDC_ENTREGA", nullable = true)
	@Documentation("INDICA SE É ENTREGA OU NÃO")
	public Boolean getEntrega() {
		return this.entrega;
	}

	public void setEntrega(Boolean entrega) {
		this.entrega = entrega;
	}

	@NotNull
	@Column(nullable = true)
	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	
	@NotNull
	@Column(name = "TP_STATUS_PEDIDO")
	@Constraint(suffix = "2")
	@Documentation("STATUS DO PEDIDO : 0 (ABERTO), 1 (PAGO)")
	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}

}
