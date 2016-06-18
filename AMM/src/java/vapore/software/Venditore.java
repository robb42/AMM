/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vapore.software;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vapore.software.Classi.GocceFactory;
import vapore.software.Classi.Prodotto;

/**
 *
 * @author rober
 */
@WebServlet(name = "Venditore", urlPatterns = {"/Venditore"})
public class Venditore extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(true);
        
        // Controllo che l'utente sia già loggato e che sia un venditore
        if(session.getAttribute("loggedIn") != null && session.getAttribute("classe").equals("venditore")){
                
            // Se l'utente sta aggiungendo un prodotto
            if(request.getParameter("Submit") != null) {
                Prodotto p = new Prodotto();
                String nomeprodotto = request.getParameter("NomeProdotto");
                String urlimmagine = request.getParameter("URLImmagine");
                String descrizione = request.getParameter("Descrizione");
                String quantitaTemp = request.getParameter("Quantita");
                Integer quantita = -1;
                if(!quantitaTemp.isEmpty()) {
                    quantita = Integer.parseInt(quantitaTemp);
                }
                String prezzoTemp = request.getParameter("Prezzo");
                Double prezzo = -1.0;
                if(!prezzoTemp.isEmpty()) {
                    prezzo = Double.parseDouble(prezzoTemp);
                }

                // Se sono stati passati dei valori validi
                if (!nomeprodotto.isEmpty() && !urlimmagine.isEmpty() && !descrizione.isEmpty() && quantita>=0 && prezzo>=0) {
                    p.setNome(nomeprodotto);
                    p.setUrlImmagine(urlimmagine);
                    p.setDescrizione(descrizione);
                    p.setQuantita(quantita);
                    p.setPrezzo(prezzo);
                    p.setGocciaId((int) session.getAttribute("id"));

                    GocceFactory.getInstance().insertProdotto(p);

                    request.setAttribute("prodotto", p);
                    request.getRequestDispatcher("venditore_inserito.jsp").forward(request, response);
                }
                // Carico la pagina normalmente
                else {
                    request.setAttribute("listaProdotti", GocceFactory.getInstance().getListaProdottiV((int) session.getAttribute("id")));
                    request.getRequestDispatcher("venditore.jsp").forward(request, response);
                }
            }
            // Se l'utente è nella pagina per la modifica di un prodotto
            else if(request.getParameter("GiocoID") != null) {
                int giocoid = Integer.parseInt(request.getParameter("GiocoID"));

                Prodotto p = GocceFactory.getInstance().getProdottoById(giocoid);
                int venditoreId = (int) session.getAttribute("id");
                
                // Controllo che sia l'id di un prodotto e che appartenga al venditore attuale
                if(p != null && p.controllaVenditore(venditoreId)) {
                    
                    // Se l'utente ha completato le modifiche
                    if(request.getParameter("Modifica") != null) {
                        String nomeprodotto = request.getParameter("NomeProdotto");
                        String urlimmagine = request.getParameter("URLImmagine");
                        String descrizione = request.getParameter("Descrizione");
                        String quantitaTemp = request.getParameter("Quantita");
                        Integer quantita = -1;
                        if(!quantitaTemp.isEmpty()) {
                            quantita = Integer.parseInt(quantitaTemp);
                        }
                        String prezzoTemp = request.getParameter("Prezzo");
                        Double prezzo = -1.0;
                        if(!prezzoTemp.isEmpty()) {
                            prezzo = Double.parseDouble(prezzoTemp);
                        }

                        // Se sono stati passati dei valori validi
                        if (!nomeprodotto.isEmpty() && !urlimmagine.isEmpty() && !descrizione.isEmpty() && quantita>=0 && prezzo>=0) {
                            p.setId(giocoid);
                            p.setNome(nomeprodotto);
                            p.setUrlImmagine(urlimmagine);
                            p.setDescrizione(descrizione);
                            p.setQuantita(quantita);
                            p.setPrezzo(prezzo);
                            p.setGocciaId((int) session.getAttribute("id"));

                            GocceFactory.getInstance().updateProdotto(p);

                            request.setAttribute("prodotto", p);
                            request.getRequestDispatcher("venditore_inserito.jsp").forward(request, response);
                        }
                        // Carico la pagina normalmente
                        else {
                            request.setAttribute("prodotto", p);
                            request.getRequestDispatcher("venditore_modifica.jsp").forward(request, response);
                        }
                    }
                    // Carico la pagina per la modifica
                    else {
                        request.setAttribute("prodotto", p);
                        request.getRequestDispatcher("venditore_modifica.jsp").forward(request, response);
                    }
                }
                // Carico la pagina normalmente
                else {
                    request.setAttribute("listaProdotti", GocceFactory.getInstance().getListaProdottiV(venditoreId));
                    request.getRequestDispatcher("venditore.jsp").forward(request, response);
                }
            }
            // Se l'utente è nella pagina per la rimozione di un prodotto
            else if(request.getParameter("RimuoviGiocoID") != null) {
                int giocoid = Integer.parseInt(request.getParameter("RimuoviGiocoID"));

                Prodotto p = GocceFactory.getInstance().getProdottoById(giocoid);
                int venditoreId = (int) session.getAttribute("id");

                // Controllo che sia l'id di un prodotto e che appartenga al venditore attuale
                if(p != null && p.controllaVenditore(venditoreId)) {
                    
                    // Se l'utente ha deciso di rimuovere il gioco
                    if(request.getParameter("Rimuovi") != null) {
                        GocceFactory.getInstance().removeProdotto(p);

                        request.setAttribute("rimosso", true);
                        request.getRequestDispatcher("venditore_rimuovi.jsp").forward(request, response);
                    }
                    // Chiamo la pagina di riepilogo
                    else {
                        request.setAttribute("prodotto", p);
                        request.getRequestDispatcher("venditore_rimuovi.jsp").forward(request, response);
                    }
                }
                // Carico la pagina normalmente
                else {
                    request.setAttribute("listaProdotti", GocceFactory.getInstance().getListaProdottiV(venditoreId));
                    request.getRequestDispatcher("venditore.jsp").forward(request, response);
                }
            }
            // Carico la pagina normalmente
            else {
                request.setAttribute("listaProdotti", GocceFactory.getInstance().getListaProdottiV((int) session.getAttribute("id")));
                request.getRequestDispatcher("venditore.jsp").forward(request, response);
            }
        }
        // Mostro un errore
        else {
            request.setAttribute("error", true);
            request.getRequestDispatcher("venditore.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
