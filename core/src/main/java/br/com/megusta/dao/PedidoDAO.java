package br.com.megusta.dao;

import java.util.ArrayList;
import java.util.List;

import jmine.tec.persist.impl.dao.BaseDAO;
import jmine.tec.persist.impl.hibernate.RestrictionsUtils;
import jmine.tec.utils.date.Timestamp;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.sun.mail.imap.protocol.Status;

import br.com.megusta.domain.FormaPagamento;
import br.com.megusta.domain.Pedido;
import br.com.megusta.domain.StatusPedido;

public class PedidoDAO extends BaseDAO<Pedido> {
	
	
	public List<Pedido> findByPeriodoPesquisa(Timestamp dataInicio, Timestamp dataFim, FormaPagamento formaPagamento, Boolean isEntrega) {
		Criteria criteria = this.createCriteria();
		RestrictionsUtils.addRestrictionGe(criteria, "dataHora", dataInicio);
		RestrictionsUtils.addRestrictionLe(criteria, "dataHora", dataFim);
		RestrictionsUtils.addRestrictionEq(criteria, "statusPedido", StatusPedido.PAGO);
		RestrictionsUtils.addRestrictionEq(criteria, "formaPagamento", formaPagamento);
		if (isEntrega != null && isEntrega == true) {
			RestrictionsUtils.addRestrictionEq(criteria, "entrega", isEntrega);
		}
		criteria.addOrder(Order.asc("dataHora"));
		return this.executeQuery(criteria);
	}

	public List<Pedido> findByPeriodo(Timestamp dataInicio, Timestamp dataFim, FormaPagamento formaPagamento, Boolean isEntrega) {
		Criteria criteria = this.createCriteria();
		RestrictionsUtils.addRestrictionGe(criteria, "dataHora", dataInicio);
		RestrictionsUtils.addRestrictionLe(criteria, "dataHora", dataFim);

		RestrictionsUtils.addRestrictionEq(criteria, "formaPagamento", formaPagamento);
		if (isEntrega != null && isEntrega == true) {
			RestrictionsUtils.addRestrictionEq(criteria, "entrega", isEntrega);
		}
		criteria.addOrder(Order.asc("dataHora"));

		List<Pedido> pedidos = new ArrayList<Pedido>();

		boolean vouAdd = true;

		boolean temDataInicio = dataInicio.getHours() != 0;
		boolean temDataFim = dataFim.getHours() != 0;

		List list = criteria.list();
		if (temDataInicio && temDataFim) {
			for (Object obj : list) {
				Pedido pedido = (Pedido) obj;
				int hours = pedido.getDataHora().getHours();
				if(hours >= dataInicio.getHours() && hours <= dataFim.getHours()){
					pedidos.add(pedido);
				}
			}
			return pedidos;
		}
		return this.executeQuery(criteria);
	}

	public Double somaValorPeriodo(Timestamp dataInicio, Timestamp dataFim, FormaPagamento formaPagamento) {
		Criteria criteria = this.createCriteria();
		RestrictionsUtils.addRestrictionGe(criteria, "dataHora", dataInicio);
		RestrictionsUtils.addRestrictionLe(criteria, "dataHora", dataFim);
		RestrictionsUtils.addRestrictionEq(criteria, "formaPagamento", formaPagamento);
		// criteria.addOrder(Order.asc("dataHora"));
		criteria.setProjection(Projections.sum("valor"));
		List<Object> list = criteria.list();
		if (list.size() == 0 || list.get(0) == null) {
			return 0d;
		}
		return (Double) list.get(0);
	}

	public Double somaValorLiquidoPeriodo(Timestamp dataInicio, Timestamp dataFim, FormaPagamento formaPagamento) {
		Criteria criteria = this.createCriteria();
		RestrictionsUtils.addRestrictionGe(criteria, "dataHora", dataInicio);
		RestrictionsUtils.addRestrictionLe(criteria, "dataHora", dataFim);
		RestrictionsUtils.addRestrictionEq(criteria, "formaPagamento", formaPagamento);
		// criteria.addOrder(Order.asc("dataHora"));
		criteria.setProjection(Projections.sum("valor"));
		List<Object> list = criteria.list();
		if (list.size() == 0 || list.get(0) == null) {
			return 0d;
		}
		if (formaPagamento != null) {
			if (formaPagamento.equals(FormaPagamento.DEBITO)) {
				Double valor = (Double) list.get(0);
				valor = valor - (valor * 0.026);
				return valor;
			}
			if (formaPagamento.equals(FormaPagamento.CREDITO)) {
				Double valor = (Double) list.get(0);
				valor = valor - (valor * 0.034);
				return valor;
			}
		}
		return (Double) list.get(0);
	}

	public List<Pedido> findByPeriodoPesquisaEmAberto(Timestamp dataInicio, Timestamp dataFim, FormaPagamento formaPagamento, Boolean isEntrega) {
		Criteria criteria = this.createCriteria();
		RestrictionsUtils.addRestrictionEq(criteria, "statusPedido", StatusPedido.ABERTO);
		RestrictionsUtils.addRestrictionGe(criteria, "dataHora", dataInicio);
		RestrictionsUtils.addRestrictionLe(criteria, "dataHora", dataFim);

		RestrictionsUtils.addRestrictionEq(criteria, "formaPagamento", formaPagamento);
		if (isEntrega != null && isEntrega == true) {
			RestrictionsUtils.addRestrictionEq(criteria, "entrega", isEntrega);
		}
		criteria.addOrder(Order.asc("dataHora"));
		return this.executeQuery(criteria);
	}
}
