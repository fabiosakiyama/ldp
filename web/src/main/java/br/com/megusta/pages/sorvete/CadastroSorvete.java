package br.com.megusta.pages.sorvete;

import java.util.ArrayList;
import java.util.List;

import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.ComponentHelper;
import jmine.tec.web.wicket.pages.form.FormPage;
import jmine.tec.web.wicket.pages.form.FormType;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.domain.Sorvete;

@SuppressWarnings("serial")
@Secure(id = "URL_EDIT_SORVETE", permissionType = UrlPermission.class)
public class CadastroSorvete extends FormPage<Sorvete> {

	public CadastroSorvete(Class<? extends Page> source,
			PageParameters sourcePageParameters) {
		super(source, sourcePageParameters);
	}

	public CadastroSorvete(Class<? extends Page> source,
			PageParameters sourcePageParameters, Sorvete entity,
			FormType formType) {
		super(source, sourcePageParameters, entity, formType);
	}

	public CadastroSorvete(PageParameters sourcePageParameters) {
		super(sourcePageParameters);
	}

	@Override
	protected List<Component> createFormComponents() {
		List<Component> components = new ArrayList<Component>();
		components.add(new RequiredTextField<String>("sabor"));
		components.add(ComponentHelper.createField(Double.class, "preco"));
		components
				.add(ComponentHelper.createField(Integer.class, "quantidade"));
		final CheckBox checkForaDeLinha = new CheckBox("foraDeLinha", new PropertyModel<Boolean>(this.getEntity(), "foraDeLinha"));
		components.add(checkForaDeLinha);
		return components;
	}

	@Override
	protected boolean beforeSave(Sorvete target) {
		String sabor = target.getSabor();
		String lowerCase = sabor.toLowerCase();
		Character charAt = lowerCase.charAt(0);
		String primeiraLetra = charAt.toString().toUpperCase();
		String restante = lowerCase.substring(1);
		target.setSabor(primeiraLetra + restante);
		return true;
	}

}
