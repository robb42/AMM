/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vapore.software.Classi;

import java.util.ArrayList;

/**
 *
 * @author rober
 */
public class Venditore extends Goccia{
    private int venditoreId;
    private ArrayList<Prodotto> prodottiInVendita = new ArrayList<Prodotto>();
    
    public Venditore(){
        super();
        venditoreId = 0;
    }

    /**
     * @return the prodottiInVendita
     */
    public ArrayList<Prodotto> getProdottiInVendita() {
        return prodottiInVendita;
    }

    /**
     * @param prodottiInVendita the prodottiInVendita to set
     */
    public void setProdottiInVendita(ArrayList<Prodotto> prodottiInVendita) {
        this.prodottiInVendita = prodottiInVendita;
    }
    
    public void addProdottoInVendita(Prodotto prodottoInVendita){
        this.prodottiInVendita.add(prodottoInVendita);
    }

    /**
     * @return the venditoreId
     */
    public int getVenditoreId() {
        return venditoreId;
    }

    /**
     * @param venditoreId the venditoreId to set
     */
    public void setVenditoreId(int venditoreId) {
        this.venditoreId = venditoreId;
    }
    
}
