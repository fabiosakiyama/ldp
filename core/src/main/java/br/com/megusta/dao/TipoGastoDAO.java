package br.com.megusta.dao;

import java.util.ArrayList;
import java.util.List;

import jmine.tec.persist.api.dao.BeanNotFoundException;
import jmine.tec.persist.impl.dao.BaseDAO;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.megusta.domain.TipoGasto;

public class TipoGastoDAO extends BaseDAO<TipoGasto> {

	public TipoGasto findByTipoGasto(String tipoGasto) throws BeanNotFoundException {
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.eq("tipoGasto", tipoGasto));
		return this.executeSingleQuery(criteria);
	}
	
	public List<String> findAllNames() {
		Criteria criteria = this.createCriteria();
		List<String> nomes = new ArrayList<String>();
		for(Object obj : criteria.list()){
			TipoGasto tp = (TipoGasto) obj;
			nomes.add(tp.getTipoGasto());
		}
		return nomes;
	}

}
