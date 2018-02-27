package br.com.megusta.listener;

import jmine.tec.component.annotations.Unmodifiable;
import jmine.tec.persist.impl.persister.listener.AbstractPersisterListener;
import br.com.megusta.action.DecrementarQuantidadeSorvetesAction;
import br.com.megusta.action.IncrementarQuantidadeSorvetesAction;
import br.com.megusta.domain.Pedido;

/**
 * @author fabio
 *
 */
@Unmodifiable
public class PedidoPersisterListener extends AbstractPersisterListener<Pedido> {

	@Override
	protected void afterInsert(Pedido bean) {
		new DecrementarQuantidadeSorvetesAction().act(bean);
	}
	
	@Override
	public void afterRemove(Pedido bean) {
		new IncrementarQuantidadeSorvetesAction().act(bean);
	}
}
