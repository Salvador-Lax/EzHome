package servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miModelo.InmuebleDAO;
import miModelo.InmuebleVO;

/**
 * Servlet implementation class Catalogo
 */
@WebServlet("/Catalogo")
public class Catalogo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InmuebleVO[] inmuebles;
		InmuebleDAO idao;
		String filtroPrecio;
		String filtroMetrocuadrado;
		String filtroNumhabitacion;
		String filtroOrden;
		String filtroTipo;
		String filtroSubtipo;
		String filtroProvincia;
		int pagina;
		int totalPaginas;
		int resto;

		sesion = request.getSession();

		// Cargamos los parametros de la pagina
		filtroPrecio = request.getParameter("filtroPrecio");

		filtroMetrocuadrado = request.getParameter("filtroMetrocuadrado");

		filtroNumhabitacion = request.getParameter("filtroNumhabitacion");

		filtroOrden = "codigo";

		// Comprobamos los filtros introducidos para añadirlos a la sentencia SQL
		if (filtroPrecio != null && !filtroPrecio.isEmpty()) {
			if (filtroMetrocuadrado != null && !filtroMetrocuadrado.isEmpty()) {
				if (filtroNumhabitacion != null && !filtroNumhabitacion.isEmpty()) {
					filtroOrden = filtroPrecio + ", " + filtroMetrocuadrado + ", " + filtroNumhabitacion;
				}
			} else {
				if (filtroNumhabitacion != null && !filtroNumhabitacion.isEmpty()) {
					filtroOrden = filtroPrecio + ", " + filtroNumhabitacion;
				} else {
					filtroOrden = filtroPrecio;
				}
			}
		} else {
			if (filtroMetrocuadrado != null && !filtroMetrocuadrado.isEmpty()) {
				if (filtroNumhabitacion != null && !filtroNumhabitacion.isEmpty()) {
					filtroOrden = filtroMetrocuadrado + ", " + filtroNumhabitacion;
				}
			} else {
				if (filtroNumhabitacion != null && !filtroNumhabitacion.isEmpty()) {
					filtroOrden = filtroNumhabitacion;
				} else {
					filtroOrden = "codigo";
				}
			}
		}

		filtroTipo = request.getParameter("filtroTipo");

		if (filtroTipo != null && !filtroTipo.isEmpty()) {
			filtroTipo = "ILIKE '" + filtroTipo + "%'";
		} else {
			filtroTipo = "ILIKE '%%'";
		}

		filtroSubtipo = request.getParameter("filtroSubtipo");

		if (filtroSubtipo != null && !filtroSubtipo.isEmpty()) {
			filtroSubtipo = "ILIKE '" + filtroSubtipo + "%'";
		} else {
			filtroSubtipo = "ILIKE '%%'";
		}

		filtroProvincia = request.getParameter("filtroProvincia");

		if (filtroProvincia != null && !filtroProvincia.isEmpty()) {
			filtroProvincia = "ILIKE '" + filtroProvincia + "%'";
		} else {
			filtroProvincia = "ILIKE '%%'";
		}

		if (request.getParameter("pag") == null) {
			pagina = 0;
		} else {
			pagina = Integer.parseInt(request.getParameter("pag"));
		}

		// Calculamos el total de paginas en funcion de los filtros establecidos
		idao = new InmuebleDAO();
		totalPaginas = idao.totalInmuebles(filtroTipo, filtroSubtipo, filtroProvincia);
		resto = totalPaginas % 6;

		if (resto > 0) {
			totalPaginas = (totalPaginas / 6) + 1;
		} else {
			totalPaginas = totalPaginas / 6;
		}

		sesion.setAttribute("paginaFinal", totalPaginas);

		// Cargamos el Array de Inmuebles que enviamos a la pagina
		inmuebles = Arrays.copyOf(
				idao.consultaPaginada(filtroTipo, filtroSubtipo, filtroProvincia, filtroOrden, (pagina * 6)), 6);

		sesion.setAttribute("inmuebles", inmuebles);

		request.getRequestDispatcher("WEB-INF/catalogo.jsp").forward(request, response);
	}

}
