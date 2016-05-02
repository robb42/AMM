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
import vapore.software.Classi.Venditore;

/**
 *
 * @author rober
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

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
            String username = request.getParameter("Username");
            String password = request.getParameter("Password");
            String usernameFound = "no";
            
            ArrayList<Goccia> listaGocce = GocceFactory.getInstance().getListaGocce();
            for(Goccia u : listaGocce) {
                if(u.getUsername().equals(username)) {
                    usernameFound = "yes";
                    if(u.getPassword().equals(password)){
                        session.setAttribute("loggedIn", true);
                        session.setAttribute("id", u.getId());
                        session.setAttribute("nome", u.getNome());
                        session.setAttribute("cognome", u.getCognome());
                        session.setAttribute("saldo", u.getSaldo());

                        if (u instanceof Venditore) {
                            session.setAttribute("classe", "venditore");
                            request.setAttribute("venditore", u);
                            request.getRequestDispatcher("venditore.jsp").forward(request, response);
                        }
                        else {
                            session.setAttribute("classe", "cliente");
                            request.setAttribute("cliente", u);
                            request.setAttribute("listaProdotti", GocceFactory.getInstance().getListaProdotti());
                            request.getRequestDispatcher("cliente.jsp").forward(request, response);  
                        }
                    }
                }
            }
            request.setAttribute("usernameFound", usernameFound);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        
        if(session.getAttribute("loggedIn") != null){
            session.invalidate();
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
