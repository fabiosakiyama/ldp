package br.com.megusta.pages.pedido;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jmine.tec.di.annotation.Injected;
import jmine.tec.persist.api.dao.BeanNotFoundException;
import jmine.tec.security.api.annotation.Secure;
import jmine.tec.security.api.authorization.UrlPermission;
import jmine.tec.utils.date.Timestamp;
import jmine.tec.web.wicket.ComponentHelper;
import jmine.tec.web.wicket.component.InlinedTextField;
import jmine.tec.web.wicket.pages.Template;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.megusta.dao.PedidoDAO;
import br.com.megusta.dao.SorveteDAO;
import br.com.megusta.domain.FormaPagamento;
import br.com.megusta.domain.Pedido;
import br.com.megusta.domain.Sorvete;
import br.com.megusta.domain.StatusPedido;

@SuppressWarnings("serial")
@Secure(id = "URL_EDIT_PEDIDO", permissionType = UrlPermission.class)
public class CadastroPedido extends Template {

	private Form<Pedido> form;

	@Injected
	private SorveteDAO sorveteDAO;

	@Injected
	private PedidoDAO pedidoDAO;

	private List<CadastroPedidoModel> models = new ArrayList<CadastroPedidoModel>();

	private Pedido pedido;

	private String resumoString;

	private Boolean checkImprime = false;
	
	private Boolean checkEmAberto = false;

	private Boolean entrega = false;

	public CadastroPedido(PageParameters sourcePageParameters) {
		super(sourcePageParameters);
		this.pedido = this.pedidoDAO.createBean();
		this.pedido.setFormaPagamento(FormaPagamento.DINHEIRO);
		this.init();
	}

	public void init() {
		this.form = new Form<Pedido>("mainForm");
		this.add(new FeedbackPanel("feedback"));
		List<Sorvete> sorvetes = this.sorveteDAO.findAllEmLinha();
		Collections.sort(sorvetes);
		this.models = this.populaModel(sorvetes);

		final Label labelValorTotal = new Label("valorTotal", new PropertyModel<Double>(this.pedido, "valor"));
		labelValorTotal.setOutputMarkupId(true);
		this.form.add(labelValorTotal);
		
		TextField<Double> txtDesconto = new TextField<Double>("desconto", new PropertyModel<Double>(this.pedido, "desconto"));
		this.form.add(txtDesconto);
		
		final Label labelTroco = new Label("troco", new PropertyModel<Double>(this.pedido, "troco"));
		labelTroco.setOutputMarkupId(true);
		this.form.add(labelTroco);

		final DropDownChoice<FormaPagamento> dropDown = ComponentHelper.createDropDown("formaPagamento", new PropertyModel<FormaPagamento>(this.pedido,
				"formaPagamento"), Arrays.asList(FormaPagamento.values()));
		dropDown.setNullValid(false);

		this.form.add(dropDown);

		Label resumo = new Label("resumo", new PropertyModel<String>(this, "resumoString"));
		this.form.add(resumo);

		TextField<Double> qtdCobertura = new TextField<Double>("qtdCobertura", new PropertyModel<Double>(this.pedido, "qtdCobertura"));
		this.form.add(qtdCobertura);

		TextField<Double> qtdTopping = new TextField<Double>("qtdTopping", new PropertyModel<Double>(this.pedido, "qtdTopping"));
		qtdTopping.setVisible(false);
		this.form.add(qtdTopping);

		final TextField<Double> valorAReceber = new TextField<Double>("valorReceber", new PropertyModel<Double>(this.pedido, "valorRecebido"));
		valorAReceber.setRequired(true);
		valorAReceber.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Double valor = CadastroPedido.this.pedido.getValor();
				Double troco = CadastroPedido.this.pedido.getValorRecebido() - valor;
				CadastroPedido.this.pedido.setTroco(troco);
				target.add(labelTroco);
			}
		});

		dropDown.add(new AjaxFormComponentUpdatingBehavior("onblur") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				boolean isCredito = CadastroPedido.this.pedido.getFormaPagamento().equals(FormaPagamento.CREDITO);
				boolean isDebito = CadastroPedido.this.pedido.getFormaPagamento().equals(FormaPagamento.DEBITO);
				if (isCredito || isDebito) {
					Double valor = CadastroPedido.this.pedido.getValor();
					CadastroPedido.this.pedido.setValorRecebido(valor);
					target.add(valorAReceber);
				}

			}
		});

		valorAReceber.add(new AjaxFormComponentUpdatingBehavior("onblur") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Double valor = CadastroPedido.this.pedido.getValor();
				Double troco = CadastroPedido.this.pedido.getValorRecebido() - valor;
				CadastroPedido.this.pedido.setTroco(troco);
				target.add(labelTroco);
			}
		});
		this.form.add(valorAReceber);

		dropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (CadastroPedido.this.pedido.getFormaPagamento().isFree()) {
					CadastroPedido.this.pedido.setValorRecebido(0d);
					target.add(valorAReceber.setEnabled(false));
				} else {
					target.add(valorAReceber.setEnabled(true));
				}

			}
		});

		final ListView<CadastroPedidoModel> modelView = new ListView<CadastroPedidoModel>("sorvetes", new PropertyModel<List<CadastroPedidoModel>>(this,
				"models")) {
			@Override
			protected void populateItem(ListItem<CadastroPedidoModel> item) {
				final CadastroPedidoModel model = item.getModelObject();
				item.add(new Label("listSabor", model.getSabor()));
				item.add(new Label("listPreco", model.getPreco() + ""));
				item.add(new Label("listQuantidade", model.getQuantidadeDisponivel() + ""));
				InlinedTextField<Integer> inlinedTextField = new InlinedTextField<Integer>("listQuantidadeAComprar", new PropertyModel<Integer>(model,
						"quantidadeAComprar"));

				item.add(inlinedTextField);
			}
		};
		this.form.add(modelView);
		final CheckBox checkImprime = new CheckBox("checkImprime", new PropertyModel<Boolean>(this, "checkImprime"));
		this.form.add(checkImprime);
		final CheckBox checkEmAberto = new CheckBox("checkEmAberto", new PropertyModel<Boolean>(this, "checkEmAberto"));
		this.form.add(checkEmAberto);
		final CheckBox checkEntrega = new CheckBox("entrega", new PropertyModel<Boolean>(this, "pedido.entrega"));
		this.form.add(checkEntrega);
		SubmitLink linkCalcular = this.criarBotaoCalcularPedido();
		SubmitLink linkProcessar = this.criarBotaoFecharPedido();
		this.form.add(linkProcessar);
		this.form.add(linkCalcular);
		this.add(this.form);
	}

	private SubmitLink criarBotaoCalcularPedido() {
		return new SubmitLink("calcularPedido") {
			@Override
			public void onSubmit() {
				CadastroPedido.this.pedido.getSorvetes().clear();
				CadastroPedido.this.pedido.setValor(0d);
				for (CadastroPedidoModel model : CadastroPedido.this.models) {
					CadastroPedido.this.populaPedido(model);
				}
				CadastroPedido.this.somaCoberturaTopping();
				CadastroPedido.this.subtraiDesconto();
				CadastroPedido.this.resumoMaker();

			}
		};
	}

	protected void subtraiDesconto() {
		double desconto = this.pedido.getDesconto();
		this.pedido.subtraiPreco(desconto);
	}

	private SubmitLink criarBotaoFecharPedido() {

		return new SubmitLink("fecharPedido") {
			@Override
			public void onSubmit() {
				CadastroPedido.this.pedido.getSorvetes().clear();
				CadastroPedido.this.pedido.setValor(0d);
				for (CadastroPedidoModel model : CadastroPedido.this.models) {
					CadastroPedido.this.populaPedido(model);
				}
				CadastroPedido.this.somaCoberturaTopping();
				CadastroPedido.this.subtraiDesconto();
				if (CadastroPedido.this.pedido.getSorvetes().isEmpty()) {
					this.error("Para fechar um pedido, é preciso ter pelo menos um sorvete!");
				} else {
					CadastroPedido.this.pedido.setDataHora(new Timestamp());
					try {
						if (CadastroPedido.this.pedido.getFormaPagamento().isFree()) {
							CadastroPedido.this.pedido.setValorRecebido(0d);
							CadastroPedido.this.pedido.setValor(0d);
						}
						if(CadastroPedido.this.checkEmAberto) {
							CadastroPedido.this.pedido.setStatusPedido(StatusPedido.ABERTO);
						}else{
							CadastroPedido.this.pedido.setStatusPedido(StatusPedido.PAGO);
						}
						CadastroPedido.this.pedido.getPersister().save();
						CadastroPedido.this.setResponsePage(new CadastroPedido(new PageParameters()));
						if (CadastroPedido.this.checkImprime) {
							new ImprimirPedido().imprimir(CadastroPedido.this.pedido);
						}
						this.info("Pedido fechado com sucesso!");
					} catch (Throwable e) {
						CadastroPedido.this.pedido.setValor(0d);
						this.error(e.getLocalizedMessage());
					}

				}
			}

		};
	}

	public void populaPedido(CadastroPedidoModel model) {
		this.criaSorvete(this.pedido, model.getQuantidadeAComprar(), model.getSabor());
	}

	private void resumoMaker() {
		List<Sorvete> sorvetes = this.pedido.getSorvetes();
		Collections.sort(sorvetes);
		StringBuilder sb = new StringBuilder();
		sb.append("Total: " + sorvetes.size() + " | ");

		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Sorvete sorvete : sorvetes) {
			Integer qtd = map.get(sorvete.getSabor());
			if (qtd == null) {
				map.put(sorvete.getSabor(), 1);
			} else {
				qtd++;
				map.put(sorvete.getSabor(), qtd);
			}
		}

		for (Entry entry : map.entrySet()) {
			sb.append(entry.getValue() + "x " + entry.getKey() + ".");
		}
		sb.append(this.pedido.getQtdCobertura() + "x cobertura.");
		sb.append(this.pedido.getQtdTopping() + "x toppings.");
		this.resumoString = sb.toString();
	}

	private void somaCoberturaTopping() {
		int qtdCobertura = this.pedido.getQtdCobertura();
		this.pedido.somaPreco(qtdCobertura * 1);

		int qtdTopping = this.pedido.getQtdTopping();
		this.pedido.somaPreco(qtdTopping * 1);
	}

	private void criaSorvete(Pedido pedido, int quantidadeSorvete, String sabor) {
		Sorvete sorvete = null;
		try {
			sorvete = this.sorveteDAO.findByNaturalKey(sabor);
		} catch (BeanNotFoundException e) {
			throw new RuntimeException("Sorvete não encontrado com esse nome/sabor!");
		}
		for (int i = 0; i < quantidadeSorvete; i++) {
			pedido.getSorvetes().add(sorvete);
			pedido.somaPreco(sorvete.getPreco());
		}
	}

	private List<CadastroPedidoModel> populaModel(List<Sorvete> sorvetes) {
		List<CadastroPedidoModel> models = new ArrayList<CadastroPedidoModel>();
		for (Sorvete sorvete : sorvetes) {
			CadastroPedidoModel model = new CadastroPedidoModel();
			model.setSabor(sorvete.getSabor());
			model.setPreco(sorvete.getPreco());
			model.setQuantidadeDisponivel(sorvete.getQuantidade());
			models.add(model);
		}
		return models;
	}

}
