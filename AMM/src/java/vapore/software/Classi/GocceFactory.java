/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vapore.software.Classi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    // Variabile per la connessione al db
    private String connectionString;
    
    private GocceFactory(){
        
        /*Prodotto gioco1 = new Prodotto();
        gioco1.setId(0);
        gioco1.setNome("Squadra Fortezza 2");
        gioco1.setUrlImmagine("Images/001.jpg");
        gioco1.setDescrizione("Boh bello");
        gioco1.setQuantita(5);
        gioco1.setPrezzo(9.99);
        listaProdotti.add(gioco1);
        Prodotto gioco2 = new Prodotto();
        gioco2.setId(1);
        gioco2.setNome("Mezza-Vita");
        gioco2.setUrlImmagine("Images/002.jpg");
        gioco2.setDescrizione("Boh bello");
        gioco2.setQuantita(5);
        gioco2.setPrezzo(9.99);
        listaProdotti.add(gioco2);
        
        Venditore venditore1 = new Venditore();
        venditore1.setNome("Gabe");
        venditore1.setCognome("Newell");
        venditore1.setUsername("Gaben");
        venditore1.setPassword("3");
        venditore1.setId(0);
        venditore1.setSaldo(30.0);
        venditore1.addProdottoInVendita(gioco1);
        //ArrayList<Prodotto> arrayGiochi1 = new ArrayList<Prodotto>();
        //arrayGiochi1.add(gioco1);
        //venditore1.setProdottiInVendita(arrayGiochi1);
        listaVenditori.add(venditore1);
        
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Lil");
        cliente1.setCognome("Mayo");
        cliente1.setUsername("Ayy");
        cliente1.setPassword("Lmao");
        cliente1.setId(0);
        cliente1.setSaldo(30.0);
        listaClienti.add(cliente1);*/
    }
    
    public Goccia getGoccia(String username, String password) {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")){
            // commando SQL
            String query = "select * from goccia where " + "username=? and password=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            // dati
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet set = stmt.executeQuery();
            
            int gocciaId = set.getInt("id");
            // controllo se l'utente è un cliente
            query = "select id from cliente where " + "gocciaid = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, gocciaId);
            ResultSet setGoccia = stmt.executeQuery();
            
            if(setGoccia.next()){
                Cliente cliente = new Cliente();
                cliente.setId(set.getInt("id"));
                cliente.setNome(set.getString("nome"));
                cliente.setCognome(set.getString("cognome"));
                cliente.setUsername(set.getString("username"));
                cliente.setPassword(set.getString("password"));
                cliente.setClienteId(setGoccia.getInt("id"));
                cliente.setSaldo(set.getDouble("saldo"));
                
                query = "select * from prodotto where gocciaid=" + set.getInt("id");
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery(query);
                Prodotto p = new Prodotto();
                while(res.next()){
                    p.setId(res.getInt("id"));
                    p.setNome(res.getString("nome"));
                    p.setUrlImmagine(res.getString("urlimmagine"));
                    p.setDescrizione(res.getString("descrizione"));
                    p.setQuantita(res.getInt("quantita"));
                    p.setPrezzo(res.getDouble("prezzo"));
                    cliente.addProdottoPosseduto(p);
                }
                return cliente;
            }
            
            // controllo se l'utente è un venditore
            query = "select id from venditore where " + "gocciaid = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, gocciaId);
            setGoccia = stmt.executeQuery();
            
            if(setGoccia.next()){
                Venditore venditore = new Venditore();
                venditore.setId(set.getInt("id"));
                venditore.setNome(set.getString("nome"));
                venditore.setCognome(set.getString("cognome"));
                venditore.setUsername(set.getString("username"));
                venditore.setPassword(set.getString("password"));
                venditore.setVenditoreId(setGoccia.getInt("id"));
                venditore.setSaldo(set.getDouble("saldo"));
                
                query = "select * from prodotto where gocciaid=" + set.getInt("id");
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery(query);
                Prodotto p = new Prodotto();
                while(res.next()){
                    p.setId(res.getInt("id"));
                    p.setNome(res.getString("nome"));
                    p.setUrlImmagine(res.getString("urlimmagine"));
                    p.setDescrizione(res.getString("descrizione"));
                    p.setQuantita(res.getInt("quantita"));
                    p.setPrezzo(res.getDouble("prezzo"));
                    venditore.addProdottoInVendita(p);
                }
                return venditore;
            }
            stmt.close();
            conn.close();
        } catch (SQLException e){
            
        }
        return null;
    }
    
    public void setConnectionString(String s){
        this.connectionString = s;
    }
    public String getConnectionString(){
        return this.connectionString;
    }

    /**
     * @return the listaProdotti
     */
    public ArrayList<Prodotto> getListaProdotti() {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")){
            String query = "select * from prodotto";
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(query);
            Prodotto p = new Prodotto();
            while(res.next()){
                p.setId(res.getInt("id"));
                p.setNome(res.getString("nome"));
                p.setUrlImmagine(res.getString("urlimmagine"));
                p.setDescrizione(res.getString("descrizione"));
                p.setQuantita(res.getInt("quantita"));
                p.setPrezzo(res.getDouble("prezzo"));
                p.setGocciaId(res.getInt("gocciaid"));
                listaProdotti.add(p);
            }
            return listaProdotti;
        } catch (SQLException e){
            
        }
        return null;
    }
    public Prodotto getProdottoById(int id) {
        listaProdotti = getListaProdotti();
        for(Prodotto u : listaProdotti) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Prodotto getProdottoByNome(String nome) {
        listaProdotti = getListaProdotti();
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
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")){
            String query =  "SELECT * " +
                            "FROM goccia " +
                            "JOIN venditore ON goccia.id = venditore.gocciaid";
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(query);
            Venditore venditore = new Venditore();
            while(res.next()){
                venditore.setId(res.getInt("id"));
                venditore.setNome(res.getString("nome"));
                venditore.setCognome(res.getString("cognome"));
                venditore.setUsername(res.getString("username"));
                venditore.setPassword(res.getString("password"));
                venditore.setVenditoreId(res.getInt("venditore.id"));
                venditore.setSaldo(res.getDouble("saldo"));
                listaVenditori.add(venditore);
            }
            return listaVenditori;
        } catch (SQLException e){
            
        }
        return null;
    }
    /**
     * @param listaVenditori the listaVenditori to set
     */
    public void setListaVenditori(ArrayList<Goccia> listaVenditori) {
        this.listaVenditori = listaVenditori;
    }
    public Goccia getVenditoreById(int id) {
        listaVenditori = getListaVenditori();
        for(Goccia u : listaVenditori) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Goccia getVenditoreByNome(String nome) {
        listaVenditori = getListaVenditori();
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
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")){
            String query =  "SELECT * " +
                            "FROM goccia " +
                            "JOIN cliente ON goccia.id = cliente.gocciaid";
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(query);
            Cliente cliente = new Cliente();
            while(res.next()){
                cliente.setId(res.getInt("id"));
                cliente.setNome(res.getString("nome"));
                cliente.setCognome(res.getString("cognome"));
                cliente.setUsername(res.getString("username"));
                cliente.setPassword(res.getString("password"));
                cliente.setClienteId(res.getInt("venditore.id"));
                cliente.setSaldo(res.getDouble("saldo"));
                listaClienti.add(cliente);
            }
            return listaClienti;
        } catch (SQLException e){
            
        }
        return null;
    }
    /**
     * @param listaClienti the listaClienti to set
     */
    public void setListaClienti(ArrayList<Goccia> listaClienti) {
        this.listaClienti = listaClienti;
    }
    public Goccia getClienteById(int id) {
        listaClienti = getListaClienti();
        for(Goccia u : listaClienti) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Goccia getClienteByNome(String nome) {
        listaClienti = getListaClienti();
        for(Goccia u : listaClienti) {
            if(nome.equals(u.getNome()))
                return u;
        }
        return null;
    }
    
    public ArrayList<Goccia> getListaGocce() {
        ArrayList<Goccia> listaGocce = new ArrayList<Goccia>();
        listaVenditori = getListaVenditori();
        listaClienti = getListaClienti();
        
        listaGocce.addAll(listaVenditori);
        listaGocce.addAll(listaClienti);
        
        return listaGocce;
    }
    
}
