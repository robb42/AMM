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
        
        if(request.getParameter("GiocoID") != null) {
            int giocoid = Integer.parseInt(request.getParameter("GiocoID"));
            
            ArrayList<Prodotto> listaProdotti = GocceFactory.getInstance().getListaProdotti();
            for(Prodotto p : listaProdotti) {
                if(p.getId() == giocoid){
                    if(request.getParameter("Submit") != null) {
                        Double saldo = (Double) session.getAttribute("saldo");
                        
                        if(saldo >= p.getPrezzo()){
                            saldo -= p.getPrezzo();
                            session.setAttribute("saldo", saldo);
                            request.setAttribute("acquisto", true);
                        }
                        else{
                            request.setAttribute("error", true);
                        }
                    }
                    
                    request.setAttribute("prodotto", p);
                    request.getRequestDispatcher("cliente_riepilogo.jsp").forward(request, response);
                }
            }
        }
        
        if(session.getAttribute("loggedIn") != null){
            if(session.getAttribute("classe").equals("cliente")) {
                request.setAttribute("listaProdotti", GocceFactory.getInstance().getListaProdotti());
                request.getRequestDispatcher("cliente.jsp").forward(request, response);
            }
        }
        request.setAttribute("error", true);
        request.getRequestDispatcher("cliente.jsp").forward(request, response);
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
