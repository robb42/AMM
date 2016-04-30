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
public class GocceFactory {
    private static GocceFactory singleton;
    public static GocceFactory getInstance(){
        if (singleton == null) {
            singleton = new GocceFactory();
        }
        return singleton;
    }
    // Lista Prodotti
    private ArrayList<Prodotto> listaProdotti = new ArrayList<Prodotto>();
    // Lista Venditori
    private ArrayList<Goccia> listaVenditori = new ArrayList<Goccia>();
    // Lista Clienti
    private ArrayList<Goccia> listaClienti = new ArrayList<Goccia>();
    
    private GocceFactory(){
        
        Prodotto gioco1 = new Prodotto();
        gioco1.setId(0);
        gioco1.setNome("Squadra Fortezza 2");
        gioco1.setUrlImmagine("Images/001.jpg");
        gioco1.setDescrizione("Boh bello");
        gioco1.setQuantita(5);
        gioco1.setPrezzo(9.99);
        listaProdotti.add(gioco1);
        
        Venditore venditore1 = new Venditore();
        venditore1.setNome("Gabe");
        venditore1.setCognome("Newell");
        venditore1.setUsername("Gaben");
        venditore1.setPassword("3");
        venditore1.setId(0);
        venditore1.addProdottoInVendita(gioco1);
        /*ArrayList<Prodotto> arrayGiochi1 = new ArrayList<Prodotto>();
        arrayGiochi1.add(gioco1);
        venditore1.setProdottiInVendita(arrayGiochi1);*/
        listaVenditori.add(venditore1);
        
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Lil");
        cliente1.setCognome("Mayo");
        cliente1.setUsername("Ayy");
        cliente1.setPassword("Lmao");
        cliente1.setId(0);
        listaClienti.add(cliente1);
    }

    /**
     * @return the listaProdotti
     */
    public ArrayList<Prodotto> getListaProdotti() {
        return listaProdotti;
    }
    /**
     * @param listaProdotti the listaProdotti to set
     */
    public void setListaProdotti(ArrayList<Prodotto> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }
    public Prodotto getProdottoById(int id) {
        for(Prodotto u : listaProdotti) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Prodotto getProdottoByNome(String nome) {
        for(Prodotto u : listaProdotti) {
            if(nome.equals(u.getNome()))
                return u;
        }
        return null;
    }

    /**
     * @return the listaVenditori
     */
    public ArrayList<Goccia> getListaVenditori() {
        return listaVenditori;
    }
    /**
     * @param listaVenditori the listaVenditori to set
     */
    public void setListaVenditori(ArrayList<Goccia> listaVenditori) {
        this.listaVenditori = listaVenditori;
    }
    public Goccia getVenditoreById(int id) {
        for(Goccia u : listaVenditori) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Goccia getVenditoreByNome(String nome) {
        for(Goccia u : listaVenditori) {
            if(nome.equals(u.getNome()))
                return u;
        }
        return null;
    }

    /**
     * @return the listaClienti
     */
    public ArrayList<Goccia> getListaClienti() {
        return listaClienti;
    }
    /**
     * @param listaClienti the listaClienti to set
     */
    public void setListaClienti(ArrayList<Goccia> listaClienti) {
        this.listaClienti = listaClienti;
    }
    public Goccia getClienteById(int id) {
        for(Goccia u : listaClienti) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Goccia getClienteByNome(String nome) {
        for(Goccia u : listaClienti) {
            if(nome.equals(u.getNome()))
                return u;
        }
        return null;
    }
    
    public ArrayList<Goccia> getListaGocce() {
        ArrayList<Goccia> listaGocce = new ArrayList<Goccia>();
        
        listaGocce.addAll(listaVenditori);
        listaGocce.addAll(listaClienti);
        
        return listaGocce;
    }
    
}
