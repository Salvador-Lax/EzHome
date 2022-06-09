package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miBibliotecaGeneral.Encriptado;
import miModelo.AgenteDAO;
import miModelo.AgenteVO;
import miModelo.ClienteDAO;
import miModelo.ClienteVO;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
	ClienteVO cvo;
	AgenteVO avo;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		if (avo != null) {
			response.sendRedirect("Home");
		} else {
			if (cvo != null) {
				if (cvo.getCodigoVerificar().equals("0")) {
					response.sendRedirect("Home");
				} else {
					request.getSession().invalidate();
					request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
				}
			} else {
				request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);

			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClienteDAO cdao;
		AgenteDAO adao;
		Encriptado e;

		String mail;
		String passwd;

		sesion = request.getSession();

		// Cogemos los parametros del jsp y encriptamos la contrase�a que recibimos
		mail = request.getParameter("mail");

		e = new Encriptado();
		passwd = e.generar(request.getParameter("passwd"));

		// Realizamos una consulta en la base de datos para saber si el usuario existe
		cdao = new ClienteDAO();
		cvo = cdao.consultaGenerica("email= '" + mail + "'");

		if (cvo != null) {
			if (passwd.equals(cvo.getPasswd())) {
				sesion.setAttribute("cliente", cvo);

				if (cvo.getCodigoVerificar().equals("0")) {
					response.sendRedirect("Home");
				} else {
					response.sendRedirect("PrimerInicio");
				}
			} else {
				sesion.setAttribute("errorLogin", "Error al iniciar sesión");
				response.sendRedirect("Login");
			}
		} else {
			adao = new AgenteDAO();
			avo = adao.consultaGenerica("email= '" + mail + "'");

			if (avo != null) {
				if (passwd.equals(avo.getPasswd())) {
					sesion.setAttribute("agente", avo);
					response.sendRedirect("Home");
				} else {
					sesion.setAttribute("errorLogin", "Error al iniciar sesión");
					response.sendRedirect("Login");
				}
			} else {
				sesion.setAttribute("errorLogin", "Error al iniciar sesión");
				response.sendRedirect("Login");
			}
		}
	}

}
