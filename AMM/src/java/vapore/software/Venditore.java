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
            
            GocceFactory.getInstance().insertProdotto(p, (int)session.getAttribute("id"));
            
            request.setAttribute("prodotto", p);
            request.getRequestDispatcher("venditore_inserito.jsp").forward(request, response);  
        }
        
        if(request.getParameter("Gestione") != null) {
            //Elenco solo i prodotti posseduti da questo venditore
            
            //request.setAttribute("prodotto", p);
            //request.getRequestDispatcher("venditore_inserito.jsp").forward(request, response);  
        }
        
        if(request.getParameter("GiocoID") != null) {
            //Controllo che il prodotto sia realmente posseduto dal venditore
            //Invio i dati aggiornati al server

            //request.setAttribute("prodotto", p);
            //request.getRequestDispatcher("cliente_riepilogo.jsp").forward(request, response);
        }
        
        if(request.getParameter("RimuoviGiocoID") != null) {
            //Controllo che il prodotto sia realmente posseduto dal venditore
            //Rimuovo il prodotto dal server

            //request.setAttribute("prodotto", p);
            //request.getRequestDispatcher("cliente_riepilogo.jsp").forward(request, response);
        }
        
        if(session.getAttribute("loggedIn") != null){
            if(session.getAttribute("classe").equals("venditore")) {
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
