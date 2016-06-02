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
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            // commando SQL
            String query =  "SELECT GOCCIA.ID as GOCCIAID,\n" +
                                "GOCCIA.NOME as NOME,\n" +
                                "GOCCIA.COGNOME as COGNOME,\n" +
                                "GOCCIA.USERNAME as USERNAME,\n" +
                                "GOCCIA.PASSWORD as PASSWORD,\n" +
                                "CLIENTE.ID as CLIENTEID,\n" +
                                "GOCCIA.SALDO as SALDO\n" +
                            "FROM GOCCIA\n" +
                            "JOIN CLIENTE ON CLIENTE.GOCCIAID = GOCCIA.ID\n" +
                            "WHERE GOCCIA.USERNAME = ? AND GOCCIA.PASSWORD = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            // dati
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet set = stmt.executeQuery();
            
            if(set.next()){
                Cliente cliente = new Cliente();
                cliente.setId(set.getInt("GOCCIAID"));
                cliente.setNome(set.getString("NOME"));
                cliente.setCognome(set.getString("COGNOME"));
                cliente.setUsername(set.getString("USERNAME"));
                cliente.setPassword(set.getString("PASSWORD"));
                cliente.setClienteId(set.getInt("CLIENTEID"));
                cliente.setSaldo(set.getDouble("SALDO"));
                
                query = "SELECT PRODOTTO.ID as ID,\n" +
                            "PRODOTTO.NOME as NOME,\n" +
                            "PRODOTTO.URLIMMAGINE as URLIMMAGINE,\n" +
                            "PRODOTTO.DESCRIZIONE as DESCRIZIONE,\n" +
                            "PRODOTTO.PREZZO as PREZZO\n" +
                        "FROM PRODOTTO\n" +
                        "WHERE PRODOTTO.GOCCIAID = " + set.getInt("GOCCIAID");
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery(query);
                while(res.next()){
                    Prodotto p = new Prodotto();
                    p.setId(res.getInt("id"));
                    p.setNome(res.getString("nome"));
                    p.setUrlImmagine(res.getString("urlimmagine"));
                    p.setDescrizione(res.getString("descrizione"));
                    p.setPrezzo(res.getDouble("prezzo"));
                    cliente.addProdottoPosseduto(p);
                }
                return cliente;
            }
            
            // controllo se l'utente è un venditore
            query = "SELECT GOCCIA.ID as GOCCIAID,\n" +
                        "GOCCIA.NOME as NOME,\n" +
                        "GOCCIA.COGNOME as COGNOME,\n" +
                        "GOCCIA.USERNAME as USERNAME,\n" +
                        "GOCCIA.PASSWORD as PASSWORD,\n" +
                        "VENDITORE.ID as VENDITOREID,\n" +
                        "GOCCIA.SALDO as SALDO\n" +
                    "FROM GOCCIA\n" +
                    "JOIN VENDITORE ON VENDITORE.GOCCIAID = GOCCIA.ID\n" +
                    "WHERE GOCCIA.USERNAME = ? AND GOCCIA.PASSWORD = ?";
            stmt = conn.prepareStatement(query);
            // dati
            stmt.setString(1, username);
            stmt.setString(2, password);
            set = stmt.executeQuery();
            
            if(set.next()){
                Venditore venditore = new Venditore();
                venditore.setId(set.getInt("GOCCIAID"));
                venditore.setNome(set.getString("NOME"));
                venditore.setCognome(set.getString("COGNOME"));
                venditore.setUsername(set.getString("USERNAME"));
                venditore.setPassword(set.getString("PASSWORD"));
                venditore.setVenditoreId(set.getInt("VENDITOREID"));
                venditore.setSaldo(set.getDouble("SALDO"));
                
                query = "SELECT PRODOTTO.ID as ID,\n" +
                            "PRODOTTO.NOME as NOME,\n" +
                            "PRODOTTO.URLIMMAGINE as URLIMMAGINE,\n" +
                            "PRODOTTO.DESCRIZIONE as DESCRIZIONE,\n" +
                            "PRODOTTO.PREZZO as PREZZO\n" +
                        "FROM PRODOTTO\n" +
                        "WHERE PRODOTTO.GOCCIAID = " + set.getInt("GOCCIAID");
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery(query);
                while(res.next()){
                    Prodotto p = new Prodotto();
                    p.setId(res.getInt("ID"));
                    p.setNome(res.getString("NOME"));
                    p.setUrlImmagine(res.getString("URLIMMAGINE"));
                    p.setDescrizione(res.getString("DESCRIZIONE"));
                    p.setPrezzo(res.getDouble("PREZZO"));
                    venditore.addProdottoInVendita(p);
                }
                return venditore;
            }
            
            stmt.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
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
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            Statement stmt = conn.createStatement();
            ArrayList<Prodotto> listaProdotti = new ArrayList<Prodotto>();
            String query =  "SELECT *\n" +
                            "FROM PRODOTTO\n" +
                            "JOIN VENDITORE ON VENDITORE.GOCCIAID = PRODOTTO.GOCCIAID";
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                Prodotto p = new Prodotto();
                p.setId(res.getInt("ID"));
                p.setNome(res.getString("NOME"));
                p.setUrlImmagine(res.getString("URLIMMAGINE"));
                p.setDescrizione(res.getString("DESCRIZIONE"));
                p.setPrezzo(res.getDouble("PREZZO"));
                p.setGocciaId(res.getInt("GOCCIAID"));
                listaProdotti.add(p);
            }
            return listaProdotti;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public int insertProdotto(Prodotto p, int id) {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            int i;

            for (i=0; i<p.getQuantita(); i++){
                String query =  "INSERT INTO PRODOTTO " +
                        "VALUES (default, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                // dati
                stmt.setString(1, p.getNome());
                stmt.setString(2, p.getUrlImmagine());
                stmt.setString(3, p.getDescrizione());
                stmt.setDouble(4, p.getPrezzo());
                stmt.setInt(5, id);
                
                return stmt.executeUpdate();
            }
            
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return 0;
    }
    public Prodotto getProdottoById(int id) {
        ArrayList<Prodotto> listaProdotti = new ArrayList<Prodotto>();
        listaProdotti = getListaProdotti();
        for(Prodotto u : listaProdotti) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Prodotto getProdottoByNome(String nome) {
        ArrayList<Prodotto> listaProdotti = new ArrayList<Prodotto>();
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
            ArrayList<Goccia> listaVenditori = new ArrayList<Goccia>();
            String query =  "SELECT GOCCIA.ID as GOCCIAID,\n" +
                                "GOCCIA.NOME as NOME,\n" +
                                "GOCCIA.COGNOME as COGNOME,\n" +
                                "GOCCIA.USERNAME as USERNAME,\n" +
                                "GOCCIA.PASSWORD as PASSWORD,\n" +
                                "VENDITORE.ID as VENDITOREID,\n" +
                                "GOCCIA.SALDO as SALDO\n" +
                            "FROM GOCCIA\n" +
                            "JOIN VENDITORE ON VENDITORE.GOCCIAID = GOCCIA.ID";
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(query);
            while(res.next()){
                Venditore venditore = new Venditore();
                venditore.setId(res.getInt("GOCCIAID"));
                venditore.setNome(res.getString("NOME"));
                venditore.setCognome(res.getString("COGNOME"));
                venditore.setUsername(res.getString("USERNAME"));
                venditore.setPassword(res.getString("PASSWORD"));
                venditore.setVenditoreId(res.getInt("VENDITOREID"));
                venditore.setSaldo(res.getDouble("SALDO"));
                listaVenditori.add(venditore);
            }
            return listaVenditori;
        } catch (SQLException e){
            
        }
        return null;
    }
    public Goccia getVenditoreById(int id) {
        ArrayList<Goccia> listaVenditori = new ArrayList<Goccia>();
        listaVenditori = getListaVenditori();
        for(Goccia u : listaVenditori) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Goccia getVenditoreByNome(String nome) {
        ArrayList<Goccia> listaVenditori = new ArrayList<Goccia>();
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
            ArrayList<Goccia> listaClienti = new ArrayList<Goccia>();
            String query =  "SELECT GOCCIA.ID as GOCCIAID,\n" +
                                "GOCCIA.NOME as NOME,\n" +
                                "GOCCIA.COGNOME as COGNOME,\n" +
                                "GOCCIA.USERNAME as USERNAME,\n" +
                                "GOCCIA.PASSWORD as PASSWORD,\n" +
                                "CLIENTE.ID as CLIENTEID,\n" +
                                "GOCCIA.SALDO as SALDO\n" +
                            "FROM GOCCIA\n" +
                            "JOIN CLIENTE ON CLIENTE.GOCCIAID = GOCCIA.ID";
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(query);
            while(res.next()){
                Cliente cliente = new Cliente();
                cliente.setId(res.getInt("ID"));
                cliente.setNome(res.getString("NOME"));
                cliente.setCognome(res.getString("COGNOME"));
                cliente.setUsername(res.getString("USERNAME"));
                cliente.setPassword(res.getString("PASSWORD"));
                cliente.setClienteId(res.getInt("CLIENTEID"));
                cliente.setSaldo(res.getDouble("SALDO"));
                listaClienti.add(cliente);
            }
            return listaClienti;
        } catch (SQLException e){
            
        }
        return null;
    }
    public Goccia getClienteById(int id) {
        ArrayList<Goccia> listaClienti = new ArrayList<Goccia>();
        listaClienti = getListaClienti();
        for(Goccia u : listaClienti) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Goccia getClienteByNome(String nome) {
        ArrayList<Goccia> listaClienti = new ArrayList<Goccia>();
        listaClienti = getListaClienti();
        for(Goccia u : listaClienti) {
            if(nome.equals(u.getNome()))
                return u;
        }
        return null;
    }
    
    public ArrayList<Goccia> getListaGocce() {
        ArrayList<Goccia> listaVenditori = new ArrayList<Goccia>();
        ArrayList<Goccia> listaClienti = new ArrayList<Goccia>();
        ArrayList<Goccia> listaGocce = new ArrayList<Goccia>();
        listaVenditori = getListaVenditori();
        listaClienti = getListaClienti();
        
        listaGocce.addAll(listaVenditori);
        listaGocce.addAll(listaClienti);
        
        return listaGocce;
    }
    
}
