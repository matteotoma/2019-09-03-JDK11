package it.polito.tdp.food.model;

import java.util.ArrayList;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private List<Adiacenza> adiacenze;
	private List<String> best;
	private int pesoMax;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(Integer c) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(grafo, dao.getTipiPorzione(c));
		
		// aggiungo gli archi
		this.adiacenze = new ArrayList<>(dao.getAdiacenze());
		for(Adiacenza a: adiacenze)
			if(grafo.containsVertex(a.getTipo1()) && grafo.containsVertex(a.getTipo2()) && grafo.getEdge(a.getTipo1(), a.getTipo2()) == null)
				Graphs.addEdge(grafo, a.getTipo1(), a.getTipo2(), a.getPeso());
	}
	
	public List<PorzioneAdiacente> getAdiacenti(String porzione) {
		List<String> vicini = Graphs.neighborListOf(this.grafo, porzione) ;
		List<PorzioneAdiacente> result = new ArrayList<>();
		for(String v: vicini) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(porzione, v)) ;
			result.add(new PorzioneAdiacente(v, peso)) ;
		}
		return result ;
	}
	
	public List<String> trovaPercorso(String source, Integer k){
		this.best = new ArrayList<>();
		this.pesoMax = 0;
		List<String> parziale = new ArrayList<>();
		parziale.add(source);
		cerca(parziale, source, k);
		return best;
	}

	private void cerca(List<String> parziale, String source, Integer k) {
		// caso terminale
		if(parziale.size() == k) {
			int pesoParziale = totPeso(parziale);
			if(pesoParziale > pesoMax) {
				pesoMax = pesoParziale;
				best = new ArrayList<>(parziale);
			}
			return;
		}
		
		for(String vicino: Graphs.neighborListOf(grafo, source)) {
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				cerca(parziale, vicino, k);
				parziale.remove(vicino);
			}
		}
	}

	private int totPeso(List<String> parziale) {
		int somma = 0;
		for(int i=0; i<parziale.size(); i++)
			if(!parziale.get(i).equals(parziale.get(parziale.size()-1)))
				somma += grafo.getEdgeWeight(grafo.getEdge(parziale.get(i), parziale.get(i+1)));
		return somma;
	}

	public List<String> getTipiPorzione() {
		List<String> tipiPorzione = new ArrayList<>(grafo.vertexSet());
		return tipiPorzione;
	}

	public int getNVertici() {
		return grafo.vertexSet().size();
	}

	public Object getNArchi() {
		return grafo.edgeSet().size();
	}

	public int getPesoMax() {
		return pesoMax;
	}
}
