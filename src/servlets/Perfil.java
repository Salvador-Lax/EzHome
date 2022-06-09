package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.PerfilBean;
import miModelo.AgenteDAO;
import miModelo.AgenteVO;
import miModelo.ClienteDAO;
import miModelo.ClienteVO;

/**
 * Servlet implementation class Perfil
 */
@WebServlet("/Perfil")
public class Perfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion;
		ClienteVO cvo;
		ClienteDAO cdao;
		AgenteVO avo;
		AgenteDAO adao;
		PerfilBean pb;

		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos que el usuario este logueado y, dependiendo del tipo de usuario,
		// cargamos sus datos en un bean para mostrarlos
		if (avo != null) {
			adao = new AgenteDAO();
			avo = adao.consultaGenerica("id = '" + avo.getId() + "'");

			sesion.setAttribute("agente", avo);

			pb = new PerfilBean(avo.getDni(), avo.getNombre(), avo.getApellido(), avo.getDireccion(),
					avo.getTelefono());

			sesion.setAttribute("isAgente", true);
			sesion.setAttribute("perfilbean", pb);

			request.getRequestDispatcher("WEB-INF/perfil.jsp").forward(request, response);
		} else {
			if (cvo != null && cvo.getCodigoVerificar().equals("0")) {
				cdao = new ClienteDAO();
				cvo = cdao.consultaGenerica("id = '" + cvo.getId() + "'");

				sesion.setAttribute("cliente", cvo);

				pb = new PerfilBean(cvo.getDni(), cvo.getNombre(), cvo.getApellido(), cvo.getDireccion(),
						cvo.getTelefono());

				sesion.setAttribute("isAgente", false);
				sesion.setAttribute("perfilbean", pb);

				request.getRequestDispatcher("WEB-INF/perfil.jsp").forward(request, response);
			} else {
				response.sendRedirect("Login");
			}
		}
	}

}
