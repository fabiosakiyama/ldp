package br.com.megusta.pages.gastos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jmine.tec.persist.api.dao.BeanNotFoundException;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.ComponentHelper;
import jmine.tec.web.wicket.pages.form.FormPage;
import jmine.tec.web.wicket.pages.form.FormType;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.dao.TipoGastoDAO;
import br.com.megusta.domain.CategoriaGasto;
import br.com.megusta.domain.FormaPagamento;
import br.com.megusta.domain.Gastos;
import br.com.megusta.domain.TipoGasto;

@SuppressWarnings("serial")
@Secure(id = "URL_EDIT_GASTOS", permissionType = UrlPermission.class)
public class CadastroGastos extends FormPage<Gastos> {
	
	private String tipoGasto;

	public CadastroGastos(Class<? extends Page> source, PageParameters sourcePageParameters, Gastos entity, FormType formType) {
		super(source, sourcePageParameters, entity, formType);
	}

	public CadastroGastos(PageParameters pageParameters) {
		super(pageParameters);
	}
	
	@Override
	protected List<Component> createFormComponents() {
		List<Component> components = new ArrayList<Component>();

		components.add(ComponentHelper.createBootstrapDateField("data"));
		
		final RequiredTextField<Double> campoValor = new RequiredTextField<Double>("valor", new PropertyModel<Double>(this.getEntity(), "valor"));
		components.add(campoValor);
		
		List<TipoGasto> tipos = getDAOFactory().getDAOByClass(TipoGastoDAO.class).findAll();
		List<String> choices = new ArrayList<String>();
		for (TipoGasto tipo : tipos) {
			choices.add(tipo.getTipoGasto());
		}
		final DropDownChoice<String> dropDownTipoGasto = new DropDownChoice<String>("tipoGasto", new PropertyModel<String>(this, "tipoGasto"), choices);
		dropDownTipoGasto.setNullValid(false);
		components.add(dropDownTipoGasto);
		
		final CheckBox checkIsFixo = new CheckBox("checkIsFixo", new PropertyModel<Boolean>(this.getEntity(), "isFixo"));
		components.add(checkIsFixo);
		
		final TextField<String> campoDetalhe = new TextField<String>("detalheGasto", new PropertyModel<String>(this.getEntity(), "detalheGasto"));
		components.add(campoDetalhe);
		
		final DropDownChoice<CategoriaGasto> dropDownCatGasto = ComponentHelper.createDropDown("catGasto", new PropertyModel<CategoriaGasto>(this.getEntity(),
				"categoriaGasto"), Arrays.asList(CategoriaGasto.values()));
		dropDownCatGasto.setNullValid(false);
		components.add(dropDownCatGasto);

		return components;
	}
	
	@Override
	protected boolean beforeSave(Gastos target) {
		TipoGastoDAO tipoGastoDAO = getDAOFactory().getDAOByClass(TipoGastoDAO.class);
		try {
			TipoGasto tipoGasto = tipoGastoDAO.findByTipoGasto(this.tipoGasto);
			target.setTipoGasto(tipoGasto);
		} catch (BeanNotFoundException e) {
			throw new RuntimeException("Não achou o tipo do gasto!");
		}
		return super.beforeSave(target);
	}
	
}
