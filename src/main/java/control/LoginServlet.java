package control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Utils.PasswordHasher;
import model.Studente;
import model.StudenteService;
import model.Professore;
import model.ProfessoreService;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            session.setAttribute("status", "Email e password sono obbligatori.");
            response.sendRedirect(request.getContextPath() + "/application/Login.jsp");
            return;
        }

        try {
            Studente studente = StudenteService.cercaStudenteEmail(email);

            if (studente != null && PasswordHasher.verifyPassword(password, studente.getPassword())) {
                session.setAttribute("utente", studente);
                session.setAttribute("role", "studente");
                session.setAttribute("matricolaStudente", studente.getMatricola());
                session.setAttribute("status", "Complimenti " + studente.getNome() + ", ti sei loggato con successo!");
                response.sendRedirect(request.getContextPath() + "/application/MyHome.jsp");
                return;
            }
            Professore professore = ProfessoreService.cercaProfessoreEmail(email);

            if (professore != null && PasswordHasher.verifyPassword(password, professore.getPassword())) {
                session.setAttribute("utente", professore);
                session.setAttribute("role", "professore");
                session.setAttribute("codiceProfessore", professore.getCodiceProfessore());
                session.setAttribute("status", "Complimenti " + professore.getNome()+", ti sei loggato con successo!");
                response.sendRedirect(request.getContextPath() + "/application/MyHome.jsp");
                return;
            }

            session.setAttribute("status", "Credenziali non valide.");
            response.sendRedirect(request.getContextPath() + "/application/Login.jsp");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante il login: ", e);
            session.setAttribute("status", "Errore interno. Riprova più tardi.");
            response.sendRedirect(request.getContextPath() + "/application/Login.jsp");
        }
    }
}
