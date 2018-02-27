package br.com.megusta.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jmine.tec.component.exception.MessageHolder;
import jmine.tec.persist.api.validation.ValidationError;
import jmine.tec.persist.impl.validator.AbstractValidator;
import br.com.megusta.domain.Pedido;
import br.com.megusta.domain.Sorvete;

public class PedidoValidator extends AbstractValidator<Pedido> {

	@Override
	public List<ValidationError> validateInsert(Pedido pedido) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if (pedido.getStatusPedido().isPago()) {
			if (pedido.getValorRecebido() <= 0 && !pedido.getFormaPagamento().isFree()) {
				MessageHolder message = new MessageHolder("core-message", "pedido.valor.recebido.zero.negativo", pedido.getValorRecebido());
				errors.add(new ValidationError(message));
			}

			if (pedido.getValorRecebido() < pedido.getValor() && !pedido.getFormaPagamento().isFree()) {
				MessageHolder message = new MessageHolder("core-message", "pedido.valor.recebido.menor.valor", pedido.getValorRecebido(), pedido.getValor());
				errors.add(new ValidationError(message));
			}
		}

		Map<Sorvete, Integer> map = new HashMap<Sorvete, Integer>();
		for (Sorvete sorvete : pedido.getSorvetes()) {
			Integer qtd = map.get(sorvete);
			if (qtd == null) {
				map.put(sorvete, 1);
			} else {
				qtd++;
				map.put(sorvete, qtd);
			}
		}

		for (Entry entry : map.entrySet()) {
			Sorvete sorvete = (Sorvete) entry.getKey();
			int qtdDisponivel = sorvete.getQuantidade();
			int qtdAcomprar = (Integer) entry.getValue();
			if (qtdDisponivel < qtdAcomprar) {
				MessageHolder message = new MessageHolder("core-message", "sorvete.quantidade.negativa", sorvete.getSabor());
				errors.add(new ValidationError(message));
			}

		}
		return errors;
	}
}
