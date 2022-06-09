package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miModelo.ClienteDAO;
import miModelo.ClienteVO;

/**
 * Servlet implementation class PrimerInicio
 */
@WebServlet("/PrimerInicio")
public class PrimerInicio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
	ClienteVO cvo;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");

		// Comprobamos si el usuario esta logueado y que no este verificado
		if (cvo != null && cvo.getCodigoVerificar() != "0") {
			request.getRequestDispatcher("WEB-INF/primerInicio.jsp").forward(request, response);
		} else {
			response.sendRedirect("Login");
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
		String codigo;

		sesion = request.getSession();

		// Cargamos el codigo indicado en el formulario y comprobamos si es igual que el
		// establecido para verificar al usuario
		codigo = request.getParameter("codigo");
		cvo = (ClienteVO) sesion.getAttribute("cliente");

		cdao = new ClienteDAO();

		if (codigo.equals(cvo.getCodigoVerificar())) {
			cvo.setCodigoVerificar("0");
			cdao.modificarCliente(cvo, cvo.getId());
			response.sendRedirect("Home");
		} else {
			sesion.setAttribute("errorPrimerInicio", "Error al introducir el código");
			response.sendRedirect("PrimerInicio");
		}
	}

}
