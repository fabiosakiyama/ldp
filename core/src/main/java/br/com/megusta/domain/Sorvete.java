package br.com.megusta.domain;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import jmine.tec.component.Documentation;
import jmine.tec.persist.api.authorization.annotation.DisplayName;
import jmine.tec.persist.api.persister.annotation.NaturalKey;
import jmine.tec.persist.impl.annotation.Alias;
import jmine.tec.persist.impl.bussobj.PersistableBusinessObject;

@SuppressWarnings("serial")
@Entity
@Alias("SORVET")
@DisplayName("Sorvete")
@Table(name = "SORVET")
@Documentation("Tabela que contém os sorvetes")
@SequenceGenerator(name = "SEQ_SORVET", sequenceName = "SEQ_SORVET")
public class Sorvete extends PersistableBusinessObject implements Comparable<Sorvete> {

	private Long id;

	private String sabor;

	private double preco;

	private int quantidade;

	private Boolean foraDeLinha = false;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_SORVET")
	@Column(name = "PK_SORVET")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NaturalKey
	@NotNull
	@Column(nullable = false)
	public String getSabor() {
		return this.sabor;
	}

	public void setSabor(String sabor) {
		this.sabor = sabor;
	}

	@NotNull
	@Column(nullable = false)
	public double getPreco() {
		return this.preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	@NotNull
	@Column(nullable = false)
	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the foraDeLinha
	 */
	@NotNull
	@Column(name = "INDC_FORA_DE_LINHA", nullable = true)
	@Documentation("INDICA SE ESTÁ FORA DE LINHA OU NÃO")
	public Boolean getForaDeLinha() {
		return this.foraDeLinha;
	}

	/**
	 * @param foraDeLinha
	 *            the foraDeLinha to set
	 */
	public void setForaDeLinha(Boolean foraDeLinha) {
		this.foraDeLinha = foraDeLinha;
	}

	@Override
	public String toString() {
		return this.sabor;
	}

	public int compareTo(Sorvete o) {
		return this.sabor.compareTo(o.getSabor());
	}

	@Transient
	public void decrementar(int quantidade) {
		this.quantidade = this.quantidade - quantidade;
		if (this.sabor.equalsIgnoreCase("Morango com leite condensado")) {
			if (this.quantidade <= 25) {
				this.enviarEmail();
				return;
			}
		}
		if (this.quantidade <= 15) {
			this.enviarEmail();
		}
	}

	@Transient
	public void enviarEmail() {
		// Properties props = new Properties();
		// /** Parâmetros de conexão com servidor Gmail */
		// props.put("mail.smtp.host", "smtp.gmail.com");
		// props.put("mail.smtp.socketFactory.port", "465");
		// props.put("mail.smtp.socketFactory.class",
		// "javax.net.ssl.SSLSocketFactory");
		// props.put("mail.smtp.auth", "true");
		// props.put("mail.smtp.port", "465");
		//
		// Session session = Session.getDefaultInstance(props, new
		// javax.mail.Authenticator() {
		// @Override
		// protected PasswordAuthentication getPasswordAuthentication() {
		// return new PasswordAuthentication("fabiosakiyama@gmail.com",
		// "tonira2006");
		// }
		// });
		//
		// /** Ativa Debug para sessão */
		// // session.setDebug(true);
		//
		// try {
		//
		// Message message = new MimeMessage(session);
		// message.setFrom(new InternetAddress("fabiosakiyama@gmail.com")); //
		// Remetente
		//
		// Address[] toUser = InternetAddress // Destinatário(s)
		// .parse("fabiosakiyama@gmail.com, bianca_pinda@hotmail.com");
		//
		// message.setRecipients(Message.RecipientType.TO, toUser);
		// message.setSubject("Estoque de sorvete");// Assunto
		// message.setText("O sorvete de sabor " + this.sabor +
		// " está com apenas " + this.quantidade + " unidades!");
		// /** Método para enviar a mensagem criada */
		// // Transport.send(message);
		//
		// } catch (MessagingException e) {
		// throw new RuntimeException(e);
		// }
	}

	@Transient
	public void incrementar(int quantidade) {
		this.quantidade = this.quantidade + quantidade;
	}

}
