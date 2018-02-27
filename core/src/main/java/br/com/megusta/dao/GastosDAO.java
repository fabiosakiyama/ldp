package br.com.megusta.dao;

import java.util.List;

import jmine.tec.persist.impl.dao.BaseDAO;
import jmine.tec.persist.impl.hibernate.RestrictionsUtils;
import jmine.tec.utils.date.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.megusta.domain.CategoriaGasto;
import br.com.megusta.domain.Gastos;

public class GastosDAO extends BaseDAO<Gastos> {

	public List<Gastos> findByDateBetween(Date dataInicio, Date dataFim) {
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.between("data", dataInicio, dataFim));
		return this.executeQuery(criteria);
	}

	public List<Gastos> findByPeriodoTipoGastoFixo(Date dataInicio, Date dataFim, String tipoGasto, boolean fixo, CategoriaGasto categoriaGasto) {
		Criteria criteria = this.createCriteria();
		Criteria critTipo = criteria.createCriteria("tipoGasto");
		RestrictionsUtils.addRestrictionGe(criteria, "data", dataInicio);
		RestrictionsUtils.addRestrictionLe(criteria, "data", dataFim);
		RestrictionsUtils.addRestrictionEq(criteria, "categoriaGasto", categoriaGasto);
		RestrictionsUtils.addRestrictionEq(critTipo, "tipoGasto", tipoGasto);
		if(fixo){
			RestrictionsUtils.addRestrictionEq(criteria, "fixo", fixo);
		}
		return this.executeQuery(criteria);
	}

	public double sumValorByPeriodoTipoGastoFixo(Date dataInicio, Date dataFim, String tipoGasto, Boolean fixo) {
		Criteria criteria = this.createCriteria();
		Criteria critTipo = criteria.createCriteria("tipoGasto");
		RestrictionsUtils.addRestrictionGe(criteria, "data", dataInicio);
		RestrictionsUtils.addRestrictionLe(criteria, "data", dataFim);
		RestrictionsUtils.addRestrictionEq(critTipo, "tipoGasto", tipoGasto);
		if(fixo){
			RestrictionsUtils.addRestrictionEq(criteria, "fixo", fixo);
		}
		
		double sum = 0;
		for(Object obj : criteria.list()){
			Gastos gastos = (Gastos) obj;
			sum+= gastos.getValor();
		}
		
		return sum;
	}
}
