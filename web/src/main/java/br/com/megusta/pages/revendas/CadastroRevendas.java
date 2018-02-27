package br.com.megusta.pages.revendas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.ComponentHelper;
import jmine.tec.web.wicket.pages.form.FormPage;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.domain.RevendaEnum;
import br.com.megusta.domain.Revendas;

@SuppressWarnings("serial")
@Secure(id = "URL_EDIT_REVENDAS", permissionType = UrlPermission.class)
public class CadastroRevendas extends FormPage<Revendas> {

	public CadastroRevendas(Class<? extends Page> source,
			PageParameters sourcePageParameters) {
		super(source, sourcePageParameters);
	}

	public CadastroRevendas(PageParameters sourcePageParameters) {
		super(sourcePageParameters);
	}
	

	@Override
	protected List<Component> createFormComponents() {
		List<Component> components = new ArrayList<Component>();
		components.add(new FeedbackPanel("feedback"));

		final RequiredTextField<Double> campoValor = new RequiredTextField<Double>("valor", new PropertyModel<Double>(this.getEntity(), "valor"));
		components.add(campoValor);
		
		final TextField<Integer> campoQuantidade = new TextField<Integer>("quantidade", new PropertyModel<Integer>(this.getEntity(), "quantidade"));
		components.add(campoQuantidade);

		components.add(ComponentHelper.createBootstrapDateField("data"));
		
		final DropDownChoice<RevendaEnum> dropDown = ComponentHelper.createDropDown("revendaEnum", new PropertyModel<RevendaEnum>(this.getEntity(), "revendaEnum"), Arrays.asList(RevendaEnum.values()));
		dropDown.setNullValid(false);
		components.add(dropDown);
		return components;
	}


}
