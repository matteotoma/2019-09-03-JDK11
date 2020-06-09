package it.polito.tdp.food.model;

public class PorzioneAdiacente {
	
	private String v;
	private Double peso;
	
	public PorzioneAdiacente(String v, Double peso) {
		super();
		this.v = v;
		this.peso = peso;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

}
