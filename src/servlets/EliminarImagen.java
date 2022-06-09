package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miModelo.ImagenDAO;

/**
 * Servlet implementation class EliminarImagen
 */
@WebServlet("/EliminarImagen")
public class EliminarImagen extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion;
		ImagenDAO imdao;

		sesion = request.getSession();

		imdao = new ImagenDAO();

		// Comprobamos si la operacion tiene exito y redireccionamos
		if (imdao.eliminarImagen("id = '" + Long.parseLong(request.getParameter("img")) + "'")) {
			response.sendRedirect("ModificarGaleria?inm=" + request.getParameter("inm"));
		} else {
			sesion.setAttribute("errorEliminarImagen", "Ocurrio un error al intentar eliminar la imagen");
			response.sendRedirect("ModificarGaleria?inm=" + request.getParameter("inm"));
		}
	}

}
