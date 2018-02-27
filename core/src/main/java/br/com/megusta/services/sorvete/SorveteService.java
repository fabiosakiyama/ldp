package br.com.megusta.services.sorvete;

import br.com.megusta.dao.SorveteDAO;
import br.com.megusta.domain.Sorvete;
import jmine.tec.di.annotation.Injected;
import jmine.tec.persist.api.persister.StatelessPersister;
import jmine.tec.services.api.ActionsEnum;
import jmine.tec.services.api.annotations.Execution;
import jmine.tec.services.api.annotations.Input;
import jmine.tec.services.api.annotations.ServiceImplementor;

@ServiceImplementor(action = ActionsEnum.INCLUIR)
public class SorveteService {

	private static final String SABOR = "Sabor";

	private static final String PRECO = "Preço";

	private static final String QTDE = "Quantidade";

	@Input(fieldName = SABOR)
	private String sabor;

	@Input(fieldName = PRECO)
	private double preco;

	@Input(fieldName = QTDE)
	private int qtde;

	@Injected
	private SorveteDAO sorveteDAO;
	@Injected
	private StatelessPersister<Sorvete> statelessPersister;

	@Execution
	public void execute() {
		Sorvete sorvete = this.sorveteDAO.createBean();
		sorvete.setSabor(this.sabor);
		sorvete.setPreco(this.preco);
		sorvete.setQuantidade(this.qtde);
		this.statelessPersister.save(sorvete);
	}

}
