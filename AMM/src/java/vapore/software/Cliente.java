/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vapore.software;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vapore.software.Classi.GocceFactory;
import vapore.software.Classi.Goccia;
import vapore.software.Classi.Prodotto;

/**
 *
 * @author rober
 */
@WebServlet(name = "Cliente", urlPatterns = {"/Cliente"})
public class Cliente extends HttpServlet {

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
        
        // Controllo che l'utente sia già loggato e sia un cliente
        if(session.getAttribute("loggedIn") != null && session.getAttribute("classe").equals("cliente")) {
                
            // Se mi trovo nella pagina di un prodotto
            if(request.getParameter("GiocoID") != null) {
                int giocoid = Integer.parseInt(request.getParameter("GiocoID"));

                Prodotto p = GocceFactory.getInstance().getProdottoById(giocoid);

                // Controllo che esista un prodotto con questo id
                if(p != null) {

                    // Se l'utente sta effettuando l'acquisto
                    if(request.getParameter("Submit") != null) {
                        int clienteid = (int) session.getAttribute("id");

                        // Controllo che la procedura sia andata a buon fine
                        if(GocceFactory.getInstance().transaction(giocoid, clienteid)) {
                            Goccia c = GocceFactory.getInstance().getClienteById(clienteid);

                            // Aggiorno il saldo in sessione
                            session.setAttribute("saldo", c.getSaldo());
                            request.setAttribute("acquisto", true);
                            request.getRequestDispatcher("cliente_riepilogo.jsp").forward(request, response);
                        }
                        else{
                            request.setAttribute("error", true);
                            request.getRequestDispatcher("cliente_riepilogo.jsp").forward(request, response);
                        }
                    }
                    // Carico la pagina di riepilogo
                    else {
                        request.setAttribute("prodotto", p);
                        request.getRequestDispatcher("cliente_riepilogo.jsp").forward(request, response);
                    }
                }
                // Se l'id non corrisponde a nessun prodotto, richiamo l'elenco dei prodotto
                else {
                    request.setAttribute("listaProdotti", GocceFactory.getInstance().getListaProdotti());
                    request.getRequestDispatcher("cliente.jsp").forward(request, response);
                }
            }
            // Carico l'elenco dei prodotti
            else {
                request.setAttribute("listaProdotti", GocceFactory.getInstance().getListaProdotti());
                request.getRequestDispatcher("cliente.jsp").forward(request, response);
            }
        }
        // Mostro un errore
        else {
            request.setAttribute("error", true);
            request.getRequestDispatcher("cliente.jsp").forward(request, response);
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
