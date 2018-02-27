package br.com.megusta.dao;

import java.util.List;

import jmine.tec.persist.impl.dao.BaseDAO;
import jmine.tec.persist.impl.hibernate.RestrictionsUtils;
import jmine.tec.utils.date.Date;
import jmine.tec.utils.date.Timestamp;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import br.com.megusta.domain.FormaPagamento;
import br.com.megusta.domain.RevendaEnum;
import br.com.megusta.domain.Revendas;

public class RevendasDAO extends BaseDAO<Revendas>{
	
	public List<Revendas> findByPeriodoLocalRevenda(Date dataInicio, Date dataFim, RevendaEnum revendasEnum) {
		Criteria criteria = this.createCriteria();
		RestrictionsUtils.addRestrictionGe(criteria, "data", dataInicio);
		RestrictionsUtils.addRestrictionLe(criteria, "data", dataFim);
		RestrictionsUtils.addRestrictionEq(criteria, "revendaEnum", revendasEnum);
		criteria.addOrder(Order.asc("data"));
		return this.executeQuery(criteria);
	}
	
	public Double somaValorRevenda(Date dataInicio, Date dataFim) {
		Criteria criteria = this.createCriteria();
		RestrictionsUtils.addRestrictionGe(criteria, "data", dataInicio);
		RestrictionsUtils.addRestrictionLe(criteria, "data", dataFim);
		criteria.setProjection(Projections.sum("valor"));
		List<Object> list = criteria.list();
		if (list.size() == 0 || list.get(0) == null) {
			return 0d;
		}
		return (Double) list.get(0);
	}

}
