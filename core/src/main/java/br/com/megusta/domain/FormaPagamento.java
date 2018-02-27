/**
 *
 */
package br.com.megusta.domain;

/**
 * @author fabio
 *
 */
public enum FormaPagamento {

	DINHEIRO, DEBITO, CREDITO, BRINDE, PALITO_PREMIADO, CARTAO_FIDELIDADE;

	public boolean isFree() {
		return this.equals(FormaPagamento.BRINDE)
				|| this.equals(FormaPagamento.PALITO_PREMIADO)
				|| this.equals(FormaPagamento.CARTAO_FIDELIDADE);
	}

}
