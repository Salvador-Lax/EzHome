package servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miModelo.AgenteVO;
import miModelo.InmuebleDAO;
import miModelo.InmuebleVO;

/**
 * Servlet implementation class GestionInmueble
 */
@WebServlet("/GestionInmueble")
public class GestionInmueble extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AgenteVO avo;

		sesion = request.getSession();

		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos que el usuario este logueado
		if (avo != null) {
			InmuebleVO[] inmuebles;
			InmuebleDAO idao;
			int pagina;
			int totalPaginas;
			int resto;

			// Determinamos el total de paginas
			if (request.getParameter("pag") == null) {
				pagina = 0;
			} else {
				pagina = Integer.parseInt(request.getParameter("pag"));
			}

			idao = new InmuebleDAO();
			totalPaginas = idao.totalInmueblesAgente(avo.getId());
			resto = totalPaginas % 6;

			if (resto > 0) {
				totalPaginas = (totalPaginas / 6) + 1;
			} else {
				totalPaginas = totalPaginas / 6;
			}

			sesion.setAttribute("paginaFinal", totalPaginas);

			// Cargamos el Array de Inmuebles pertenecientes al actual agente logueado
			inmuebles = Arrays.copyOf(idao.consultaPaginadaAgente(avo.getId(), pagina * 6), 6);

			sesion.setAttribute("inmuebles", inmuebles);

			request.getRequestDispatcher("WEB-INF/gestionInmueble.jsp").forward(request, response);
		} else {
			response.sendRedirect("Login");
		}
	}

}
