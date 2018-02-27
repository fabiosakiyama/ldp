package br.com.megusta.pages.relatorio.pedido.semana;

import java.io.Serializable;

public class MediaDiaSemanaVO implements Serializable {

	private double mediaSegunda = 0;

	private double mediaTerca = 0;

	private double mediaQuarta = 0;

	private double mediaQuinta = 0;

	private double mediaSexta = 0;

	private double mediaSabado = 0;

	private double mediaDomingo = 0;

	public double getMediaSegunda() {
		return this.mediaSegunda;
	}

	public void setMediaSegunda(double mediaSegunda) {
		this.mediaSegunda = mediaSegunda;
	}

	public double getMediaTerca() {
		return this.mediaTerca;
	}

	public void setMediaTerca(double mediaTerca) {
		this.mediaTerca = mediaTerca;
	}

	public double getMediaQuarta() {
		return this.mediaQuarta;
	}

	public void setMediaQuarta(double mediaQuarta) {
		this.mediaQuarta = mediaQuarta;
	}

	public double getMediaQuinta() {
		return this.mediaQuinta;
	}

	public void setMediaQuinta(double mediaQuinta) {
		this.mediaQuinta = mediaQuinta;
	}

	public double getMediaSexta() {
		return this.mediaSexta;
	}

	public void setMediaSexta(double mediaSexta) {
		this.mediaSexta = mediaSexta;
	}

	public double getMediaSabado() {
		return this.mediaSabado;
	}

	public void setMediaSabado(double mediaSabado) {
		this.mediaSabado = mediaSabado;
	}

	public double getMediaDomingo() {
		return this.mediaDomingo;
	}

	public void setMediaDomingo(double mediaDomingo) {
		this.mediaDomingo = mediaDomingo;
	}

}
