package br.com.megusta.validator;

import java.util.ArrayList;
import java.util.List;

import jmine.tec.component.exception.MessageHolder;
import jmine.tec.persist.api.validation.ValidationError;
import jmine.tec.persist.impl.validator.AbstractValidator;
import br.com.megusta.domain.Sorvete;

public class SorveteValidator extends AbstractValidator<Sorvete> {

	@Override
	public List<ValidationError> validateUpdate(Sorvete bean) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if (bean.getQuantidade() < 0) {
			MessageHolder message = new MessageHolder("core-message", "sorvete.quantidade.negativa", bean.getSabor());
			errors.add(new ValidationError(message));
		}
		return errors;
	}
}
