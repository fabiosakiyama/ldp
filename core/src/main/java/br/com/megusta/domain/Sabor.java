package br.com.megusta.domain;

public enum Sabor {

	MORANGO_LEITECONDENSADO(1L, "Morango com Leite Condensado"),

	ACAI_BANANA(2L, "Açai com Banana"),

	PACOCA(3L, "Paçoca");

	private long id;
	private String nome;

	Sabor(long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public long getId() {
		return this.id;
	}
}
