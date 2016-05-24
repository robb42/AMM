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
public class Cliente extends Goccia {
    private int clienteId;
    private ArrayList<Prodotto> prodottiPosseduti = new ArrayList<Prodotto>();

    public Cliente(){
        super();
        clienteId = 0;
    }

    /**
     * @return the clienteId
     */
    public int getClienteId() {
        return clienteId;
    }

    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * @return the prodottiPosseduti
     */
    public ArrayList<Prodotto> getProdottiPosseduti() {
        return prodottiPosseduti;
    }

    /**
     * @param prodottiPosseduti the prodottiPosseduti to set
     */
    public void setProdottiPosseduti(ArrayList<Prodotto> prodottiPosseduti) {
        this.prodottiPosseduti = prodottiPosseduti;
    }
    
    public void addProdottoPosseduto(Prodotto prodottoPosseduto){
        this.prodottiPosseduti.add(prodottoPosseduto);
    }
}
