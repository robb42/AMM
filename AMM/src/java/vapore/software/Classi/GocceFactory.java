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

    public ArrayList<Prodotto> getListaProdotti() {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            Statement stmt = conn.createStatement();
            ArrayList<Prodotto> listaProdotti = new ArrayList<Prodotto>();
            String query =  "SELECT *\n" +
                            "FROM PRODOTTO\n";
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                Prodotto p = new Prodotto();
                p.setId(res.getInt("ID"));
                p.setNome(res.getString("NOME"));
                p.setUrlImmagine(res.getString("URLIMMAGINE"));
                p.setDescrizione(res.getString("DESCRIZIONE"));
                p.setPrezzo(res.getDouble("PREZZO"));
                p.setGocciaId(res.getInt("VENDITOREID"));
                String query2 = "SELECT COUNT(*) AS TOTAL\n" +
                                "FROM GIOCO\n" +
                                "WHERE GIOCO.PRODOTTOID = ? AND GIOCO.GOCCIAID = ?";
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                // dati
                stmt2.setInt(1, res.getInt("ID"));
                stmt2.setInt(2, res.getInt("VENDITOREID"));
                ResultSet res2 = stmt2.executeQuery();
                if(res2.next()) {
                    p.setQuantita(res2.getInt("TOTAL"));
                }
                else {
                    p.setQuantita(0);
                }
                    
                listaProdotti.add(p);
            }
            
            conn.close();
            return listaProdotti;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Prodotto> getListaProdotti(String nome) {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            ArrayList<Prodotto> listaProdotti = new ArrayList<Prodotto>();
            String query =  "SELECT * " +
                            "FROM PRODOTTO " +
                            "WHERE LOWER(NOME) LIKE LOWER(?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            // dati
            nome = "%"+nome+"%";
            stmt.setString(1, nome);
            ResultSet res = stmt.executeQuery();
            while(res.next()){
                Prodotto p = new Prodotto();
                p.setId(res.getInt("ID"));
                p.setNome(res.getString("NOME"));
                p.setUrlImmagine(res.getString("URLIMMAGINE"));
                p.setDescrizione(res.getString("DESCRIZIONE"));
                p.setPrezzo(res.getDouble("PREZZO"));
                p.setGocciaId(res.getInt("VENDITOREID"));
                String query2 = "SELECT COUNT(*) AS TOTAL\n" +
                                "FROM GIOCO\n" +
                                "WHERE GIOCO.PRODOTTOID = ? AND GIOCO.GOCCIAID = ?";
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                // dati
                stmt2.setInt(1, res.getInt("ID"));
                stmt2.setInt(2, res.getInt("VENDITOREID"));
                ResultSet res2 = stmt2.executeQuery();
                if(res2.next()) {
                    p.setQuantita(res2.getInt("TOTAL"));
                }
                else {
                    p.setQuantita(0);
                }
                    
                listaProdotti.add(p);
            }
            
            conn.close();
            return listaProdotti;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Prodotto> getListaProdottiV(int i) {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            ArrayList<Prodotto> listaProdotti = new ArrayList<Prodotto>();
            String query =  "SELECT *\n" +
                            "FROM PRODOTTO\n" +
                            "WHERE VENDITOREID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            // dati
            stmt.setInt(1, i);
            ResultSet res = stmt.executeQuery();
            while(res.next()) {
                Prodotto p = new Prodotto();
                p.setId(res.getInt("ID"));
                p.setNome(res.getString("NOME"));
                p.setUrlImmagine(res.getString("URLIMMAGINE"));
                p.setDescrizione(res.getString("DESCRIZIONE"));
                p.setPrezzo(res.getDouble("PREZZO"));
                p.setGocciaId(res.getInt("VENDITOREID"));
                String query2 = "SELECT COUNT(*) AS TOTAL\n" +
                                "FROM GIOCO\n" +
                                "WHERE GIOCO.PRODOTTOID = ? AND GIOCO.GOCCIAID = ?";
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                // dati
                stmt2.setInt(1, res.getInt("ID"));
                stmt2.setInt(2, res.getInt("VENDITOREID"));
                ResultSet res2 = stmt2.executeQuery();
                if(res2.next()) {
                    p.setQuantita(res2.getInt("TOTAL"));
                }
                else {
                    p.setQuantita(0);
                }
                    
                listaProdotti.add(p);
            }
            
            conn.close();
            return listaProdotti;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean transaction(int giocoid, int clienteid) {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            Prodotto p = getProdottoById(giocoid);
            Goccia c = getClienteById(clienteid);
            Goccia v =  getVenditoreById(p.getGocciaId());
            
            try {
                if(c.getSaldo() >= p.getPrezzo() && p.getQuantita() > 0) {
                    conn.setAutoCommit(false);
                    
                    String query =  "UPDATE GOCCIA " +
                                    "SET SALDO = ? " +
                                    "WHERE ID = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    Double saldo = (double) Math.round((c.getSaldo()-p.getPrezzo())*100)/100;
                    stmt.setDouble(1, saldo);
                    stmt.setInt(2, clienteid);
                    stmt.executeUpdate();
                    
                    query = "UPDATE GOCCIA " +
                            "SET SALDO = ? " +
                            "WHERE ID = ?";
                    stmt = conn.prepareStatement(query);
                    saldo = (double) Math.round((v.getSaldo()+p.getPrezzo())*100)/100;
                    stmt.setDouble(1, saldo);
                    stmt.setInt(2, p.getGocciaId());
                    stmt.executeUpdate();
                    
                    query = "UPDATE GIOCO " +
                            "SET GOCCIAID = ? " +
                            "WHERE ID = (SELECT MIN(ID) FROM GIOCO WHERE PRODOTTOID = ? AND GOCCIAID = ?)";
                    stmt = conn.prepareStatement(query);
                    stmt.setInt(1, clienteid);
                    stmt.setInt(2, p.getId());
                    stmt.setInt(3, p.getGocciaId());
                    stmt.executeUpdate();
                    
                    conn.commit();
                    return true;
                }
            } catch(SQLException e) {
                if(conn != null) {
                    try {
                        conn.rollback();
                        return false;
                    } catch(SQLException excep) {
                        
                    }
                }
            }
            
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public void insertProdotto(Prodotto p) {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            int i, key;
            
            String query =  "INSERT INTO PRODOTTO " +
                            "VALUES (default, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // dati
            stmt.setInt(1, p.getGocciaId());
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getUrlImmagine());
            stmt.setString(4, p.getDescrizione());
            stmt.setDouble(5, p.getPrezzo());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                key = rs.getInt(1);
                query = "INSERT INTO GIOCO " +
                        "VALUES (default, ?, ?)";
                stmt = conn.prepareStatement(query);
                // dati
                stmt.setInt(1, key);
                stmt.setInt(2, p.getGocciaId());
                for (i=0; i<p.getQuantita(); i++){
                    stmt.executeUpdate();
                }
            }
            
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void updateProdotto(Prodotto p) {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            int i, num;
            
            String query =  "UPDATE PRODOTTO " +
                            "SET VENDITOREID=?, NOME=?, URLIMMAGINE=?, DESCRIZIONE=?, PREZZO=? " +
                            "WHERE ID = ? AND VENDITOREID=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            // dati
            stmt.setInt(1, p.getGocciaId());
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getUrlImmagine());
            stmt.setString(4, p.getDescrizione());
            stmt.setDouble(5, p.getPrezzo());
            stmt.setInt(6, p.getId());
            stmt.setInt(7, p.getGocciaId());
            stmt.executeUpdate();
            
            query = "SELECT COUNT(*) AS TOTAL\n" +
                    "FROM GIOCO\n" +
                    "WHERE GIOCO.PRODOTTOID = ? AND GIOCO.GOCCIAID = ?";
            stmt = conn.prepareStatement(query);
            // dati
            stmt.setInt(1, p.getId());
            stmt.setInt(2, p.getGocciaId());
            ResultSet res = stmt.executeQuery();
            if(res.next()) {
                num = res.getInt("TOTAL");
                if(num < p.getQuantita()) {
                    num = p.getQuantita() - num;
                    query = "INSERT INTO GIOCO " +
                            "VALUES (default, ?, ?)";
                    stmt = conn.prepareStatement(query);
                    // dati
                    stmt.setInt(1, p.getId());
                    stmt.setInt(2, p.getGocciaId());
                    for (i=0; i<num; i++){
                        stmt.executeUpdate();
                    }
                }
                else if(num > p.getQuantita()) {
                    query = "DELETE FROM GIOCO " +
                            "WHERE ID = (SELECT MIN(ID) FROM GIOCO WHERE PRODOTTOID = ? AND GOCCIAID = ?)";
                    stmt = conn.prepareStatement(query);
                    // dati
                    stmt.setInt(1, p.getId());
                    stmt.setInt(2, p.getGocciaId());
                    for (i=0; i<num-p.getQuantita(); i++){
                        stmt.executeUpdate();
                    }
//                    query = "INSERT INTO GIOCO " +
//                            "VALUES (default, ?, ?)";
//                    stmt = conn.prepareStatement(query);
//                    // dati
//                    stmt.setInt(1, p.getId());
//                    stmt.setInt(2, p.getGocciaId());
//                    for (i=0; i<p.getQuantita(); i++){
//                        stmt.executeUpdate();
//                    }
                }
            }
            else {
                num = p.getQuantita();
                query = "INSERT INTO GIOCO " +
                        "VALUES (default, ?, ?)";
                stmt = conn.prepareStatement(query);
                // dati
                stmt.setInt(1, p.getId());
                stmt.setInt(2, p.getGocciaId());
                for (i=0; i<num; i++){
                    stmt.executeUpdate();
                }
            }
            
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void removeProdotto(Prodotto p) {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            String query = "DELETE FROM GIOCO " +
                    "WHERE PRODOTTOID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            // dati
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
            
            query = "DELETE FROM PRODOTTO " +
                    "WHERE ID = ?";
            stmt = conn.prepareStatement(query);
            // dati
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
            
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
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
            
            conn.close();
            return listaVenditori;
        } catch (SQLException e){
            
        }
        return null;
    }
    public Goccia getVenditoreById(int id) {
        try(Connection conn = DriverManager.getConnection(connectionString, "username", "pass")) {
            // commando SQL
            String query = "SELECT GOCCIA.ID as GOCCIAID,\n" +
                                "GOCCIA.NOME as NOME,\n" +
                                "GOCCIA.COGNOME as COGNOME,\n" +
                                "GOCCIA.USERNAME as USERNAME,\n" +
                                "GOCCIA.PASSWORD as PASSWORD,\n" +
                                "VENDITORE.ID as VENDITOREID,\n" +
                                "GOCCIA.SALDO as SALDO\n" +
                            "FROM GOCCIA\n" +
                            "JOIN VENDITORE ON VENDITORE.GOCCIAID = GOCCIA.ID\n" +
                            "WHERE GOCCIA.ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            // dati
            stmt.setInt(1, id);
            ResultSet set = stmt.executeQuery();
            
            if(set.next()){
                Venditore venditore = new Venditore();
                venditore.setId(set.getInt("GOCCIAID"));
                venditore.setNome(set.getString("NOME"));
                venditore.setCognome(set.getString("COGNOME"));
                venditore.setUsername(set.getString("USERNAME"));
                venditore.setPassword(set.getString("PASSWORD"));
                venditore.setVenditoreId(set.getInt("VENDITOREID"));
                venditore.setSaldo(set.getDouble("SALDO"));
                
                return venditore;
            }
        } catch (SQLException e){
            
        }
        return null;
    }
    public Goccia getVenditoreByNome(String nome) {
        ArrayList<Goccia> listaVenditori = getListaVenditori();
        for(Goccia u : listaVenditori) {
            if(nome.equals(u.getNome()))
                return u;
        }
        return null;
    }

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
            
            conn.close();
            return listaClienti;
        } catch (SQLException e){
            
        }
        return null;
    }
    public Goccia getClienteById(int id) {
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
                            "WHERE GOCCIA.ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            // dati
            stmt.setInt(1, id);
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
                
                return cliente;
            }
        } catch (SQLException e){
            
        }
        return null;
    }
    public Goccia getClienteByNome(String nome) {
        ArrayList<Goccia> listaClienti = getListaClienti();
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
    public Goccia getGocciaById(int id) {
        ArrayList<Goccia> listaGocce = getListaGocce();
        for(Goccia u : listaGocce) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }
    public Goccia getGocciaByNome(String nome) {
        ArrayList<Goccia> listaGocce = getListaGocce();
        for(Goccia u : listaGocce) {
            if(nome.equals(u.getNome()))
                return u;
        }
        return null;
    }
    
}
