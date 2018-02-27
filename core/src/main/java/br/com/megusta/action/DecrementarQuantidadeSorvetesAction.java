package br.com.megusta.action;

import java.util.List;

import jmine.tec.component.Action;
import br.com.megusta.domain.Pedido;
import br.com.megusta.domain.Sorvete;

public class DecrementarQuantidadeSorvetesAction implements Action<Pedido> {

	public void act(Pedido pedido) {
		// Buscar todos sorvetes envolvidos no pedido e decrementar a quantidade
		List<Sorvete> sorvetes = pedido.getSorvetes();
		Sorvete antigo = null;
		int qtd = 0;
		for (Sorvete sorvete : sorvetes) {
			if (antigo == null) {
				antigo = sorvete;
				qtd++;
				continue;
			}
			if (sorvete.getSabor().equalsIgnoreCase(antigo.getSabor())) {
				qtd++;
			} else {
				antigo.decrementar(qtd);
				antigo.getPersister().save();
				qtd = 1;
				antigo = sorvete;
			}
		}
		int size = pedido.getSorvetes().size();
		Sorvete ultimoSorvete = pedido.getSorvetes().get(size - 1);
		ultimoSorvete.decrementar(qtd);
		ultimoSorvete.getPersister().save();
	}
}
