package br.com.megusta.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import jmine.tec.persist.impl.dao.BaseDAO;
import jmine.tec.persist.impl.hibernate.RestrictionsUtils;
import jmine.tec.utils.date.Timestamp;
import br.com.megusta.domain.FormaPagamento;
import br.com.megusta.domain.Pedido;
import br.com.megusta.domain.Sorvete;

public class SorveteDAO extends BaseDAO<Sorvete> {
	
	public int findQtdTotalSomenteSorvetes(){
		Criteria criteria = this.createCriteria();
		int qtd = 0;
		List<Sorvete> list = criteria.list();
		for(Sorvete sorvete : list){
			if(sorvete.getSabor().startsWith("z") || sorvete.getSabor().startsWith("Z")){
				continue;
			}
			qtd = qtd + sorvete.getQuantidade();
		}
		return qtd;
	}

	public List<Sorvete> findAllEmLinha() {
		Criteria criteria = this.createCriteria();
		RestrictionsUtils.addRestrictionEq(criteria, "foraDeLinha", false);
		criteria.addOrder(Order.asc("sabor"));
		return this.executeQuery(criteria);
	}
	
	public List<Sorvete> findAllByForaDeLinha(boolean foraDeLinha) {
		Criteria criteria = this.createCriteria();
		RestrictionsUtils.addRestrictionEq(criteria, "foraDeLinha", foraDeLinha);
		criteria.addOrder(Order.asc("sabor"));
		return this.executeQuery(criteria);
	}
}
