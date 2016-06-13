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
        
        if(request.getParameter("Submit") != null) {
            Prodotto p = new Prodotto();
            String nomeprodotto = request.getParameter("NomeProdotto");
            String urlimmagine = request.getParameter("URLImmagine");
            String descrizione = request.getParameter("Descrizione");
            int quantita = Integer.parseInt(request.getParameter("Quantita"));
            Double prezzo = Double.parseDouble(request.getParameter("Prezzo"));
            
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
        else if(request.getParameter("GiocoID") != null) {
            int giocoid = Integer.parseInt(request.getParameter("GiocoID"));
            
            ArrayList<Prodotto> listaProdotti = GocceFactory.getInstance().getListaProdotti();
            Prodotto p = GocceFactory.getInstance().getProdottoById(giocoid);
            
//            if(!p.controllaVenditore((int) session.getAttribute("id"))) {
//                request.setAttribute("error", true);
//                request.getRequestDispatcher("venditore.jsp").forward(request, response); 
//            }
            
            if(request.getParameter("Modifica") != null) {
                String nomeprodotto = request.getParameter("NomeProdotto");
                String urlimmagine = request.getParameter("URLImmagine");
                String descrizione = request.getParameter("Descrizione");
                int quantita = Integer.parseInt(request.getParameter("Quantita"));
                Double prezzo = Double.parseDouble(request.getParameter("Prezzo"));
                
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
            else {
                request.setAttribute("prodotto", p);
                request.getRequestDispatcher("venditore_modifica.jsp").forward(request, response);
            }
        }
        else if(request.getParameter("RimuoviGiocoID") != null) {
            int giocoid = Integer.parseInt(request.getParameter("RimuoviGiocoID"));
            
            ArrayList<Prodotto> listaProdotti = GocceFactory.getInstance().getListaProdotti();
            Prodotto p = GocceFactory.getInstance().getProdottoById(giocoid);
            
//            if(!p.controllaVenditore((int) session.getAttribute("id"))) {
//                request.setAttribute("error", true);
//                request.getRequestDispatcher("venditore.jsp").forward(request, response); 
//            }
            
            if(request.getParameter("Rimuovi") != null) {
                GocceFactory.getInstance().removeProdotto(p);

                request.setAttribute("rimosso", true);
                request.getRequestDispatcher("venditore_rimuovi.jsp").forward(request, response);
            }

            request.setAttribute("prodotto", p);
            request.getRequestDispatcher("venditore_rimuovi.jsp").forward(request, response);
        }
        if(session.getAttribute("loggedIn") != null){
            if(session.getAttribute("classe").equals("venditore")) {
                request.setAttribute("listaProdotti", GocceFactory.getInstance().getListaProdottiV((int) session.getAttribute("id")));
                request.getRequestDispatcher("venditore.jsp").forward(request, response);
            }
        }
        request.setAttribute("error", true);
        request.getRequestDispatcher("venditore.jsp").forward(request, response);  
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
