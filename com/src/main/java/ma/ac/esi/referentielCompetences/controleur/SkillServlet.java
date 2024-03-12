package ma.ac.esi.referentielCompetences.controleur;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.ac.esi.referentielCompetences.model.Skill;
import ma.ac.esi.referentielCompetences.model.SkillDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/SkillServlet")
public class SkillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SkillServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String domain = request.getParameter("domain");
        String level = request.getParameter("level");

        Skill skill = new Skill(name, description, domain, level);

        SkillDAO skillDAO = new SkillDAO();
        boolean success = skillDAO.addSkill(skill);

        if (success) {
            request.setAttribute("message", "La compétence a été ajoutée avec succès.");
        } else {
            request.setAttribute("erreur", "Erreur lors de l'ajout de la compétence.");
        }

        try {
            List<Skill> skillList = skillDAO.getAllSkills();
            request.setAttribute("skillList", skillList);
        } catch (Exception e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée, par exemple, logger l'erreur.
            request.setAttribute("erreur", "Erreur lors de la récupération de la liste des compétences.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/SkillCrud.jsp");
        dispatcher.forward(request, response);
    }
}

