package br.com.megusta.pages.gastos;

import java.util.ArrayList;
import java.util.List;

import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.web.wicket.pages.form.FormPage;
import jmine.tec.web.wicket.pages.form.FormType;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.domain.TipoGasto;

@SuppressWarnings("serial")
@Secure(id = "URL_EDIT_TP_GASTOS", permissionType = UrlPermission.class)
public class CadastroTipoGastos extends FormPage<TipoGasto> {

	public CadastroTipoGastos(PageParameters pageParameters) {
		super(pageParameters);
	}

	public CadastroTipoGastos(Class<? extends Page> pageClass, PageParameters pageParameters, TipoGasto entity, FormType formType) {
		super(pageClass, pageParameters, entity, formType);
	}

	@Override
	protected List<Component> createFormComponents() {
		List<Component> components = new ArrayList<Component>();
		RequiredTextField<String> campoTipoGasto = new RequiredTextField<String>("tipoGasto");
		components.add(campoTipoGasto);
		return components;
	}

}
