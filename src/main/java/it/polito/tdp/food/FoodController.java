/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.PorzioneAdiacente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...\n");
    	String tipo = this.boxPorzioni.getValue();
    	if(tipo == "" || tipo == null) {
    		txtResult.appendText(String.format("Devi selezionare un tipo di porzione!\n"));
    		return;
    	}
    	Integer k;
    	try {
    		k = Integer.parseInt(this.txtPassi.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero!\n");
    		return;
    	}
    	List<String> percorso = model.trovaPercorso(tipo, k);
    	txtResult.appendText(String.format("Trovato cammino di lunghezza %d e di peso massimo %d\n", k, model.getPesoMax()));
    	for(String s: percorso)
    		txtResult.appendText(String.format("%s \n", s));
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...\n");
    	String tipo = this.boxPorzioni.getValue();
    	if(tipo == "" || tipo == null) {
    		txtResult.appendText(String.format("Devi selezionare un tipo di porzione!\n"));
    		return;
    	}
    	List<PorzioneAdiacente> visita = model.getAdiacenti(tipo);
    	txtResult.appendText("Componenti connesse a: \n" + tipo);
    	for(PorzioneAdiacente s: visita)
    		txtResult.appendText(String.format("%s %f\n", s.getV(), s.getPeso()));
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n");
    	Integer c;
    	try {
    		c = Integer.parseInt(this.txtCalorie.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero!\n");
    		return;
    	}
    	model.creaGrafo(c);
    	txtResult.appendText(String.format("Grafo creato con %d vertici e %d archi!\n", model.getNVertici(), model.getNArchi()));
    	this.setBox();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

	private void setBox() {
		this.boxPorzioni.getItems().addAll(model.getTipiPorzione());
	}
}
