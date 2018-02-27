package br.com.megusta.pages.pedido;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import br.com.megusta.domain.Pedido;
import br.com.megusta.domain.Sorvete;

public class ImprimirPedido {

	private static PrintService impressora;

	public void imprimir(Pedido pedido) throws IOException {

		// Tentativa 3:
		// http://www.guj.com.br/java/290203-resolvidoimprimindo-bematech-nao-fiscal-usb-java-uso-do-modelo-mp-4200th
		DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);
		for (PrintService pse : ps) {
			if (pse.getName().contains("POS")) {
				impressora = pse;
			}

		}
		try {
			DocPrintJob dpj = impressora.createPrintJob();

			List<Sorvete> sorvetes = pedido.getSorvetes();
			Collections.sort(sorvetes);
			StringBuilder sb = new StringBuilder();
			sb.append("----------- La Divina ----------\n");
			String data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pedido.getDataHora());
			sb.append("Data: " + data + "\n");
			sb.append("Quantidade: " + sorvetes.size() + "\n");

			Map<Sorvete, Integer> map = new HashMap<Sorvete, Integer>();
			for (Sorvete sorvete : sorvetes) {
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
				String sabor = sorvete.getSabor();
				if (sabor.startsWith("Z") || sabor.startsWith("X")) {
					sabor = sabor.substring(1);
				}
				int v = (Integer) entry.getValue();
				sb.append(v + "x " + sabor + " " + sorvete.getPreco() * v + "\n");
			}
			if (pedido.getQtdCobertura() > 0 || pedido.getQtdTopping() > 0) {
				sb.append(pedido.getQtdCobertura() + "x cobertura + topping. \n");
			}
			if (pedido.getDesconto() > 0) {
				sb.append("Desconto: " + pedido.getDesconto() + "\n");
			}
			sb.append("Valor: " + pedido.getValor() + "\n");
			sb.append("\n");
			sb.append("\n");
			InputStream stream = new ByteArrayInputStream((sb.toString()).getBytes());
			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
			Doc doc = new SimpleDoc(stream, flavor, null);
			dpj.print(doc, null);
		} catch (PrintException e) {
			e.printStackTrace();
		}

	}

}
