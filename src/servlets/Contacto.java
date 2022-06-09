package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miBibliotecaGeneral.Correo;

/**
 * Servlet implementation class Contacto
 */
@WebServlet("/Contacto")
public class Contacto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/contacto.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion;

		String nombre;
		String correo;
		String asunto;
		String mensaje;
		Correo co;

		sesion = request.getSession();

		// Cargamos parametros y comprobamos que no esten vacios, luego enviamos el
		// correo
		nombre = request.getParameter("nombre");
		correo = request.getParameter("correo");
		asunto = request.getParameter("asunto");
		mensaje = request.getParameter("mensaje");

		if (!nombre.isEmpty() && !correo.isEmpty() && !asunto.isEmpty() && !mensaje.isEmpty()) {
			co = new Correo();

			co.enviar("slaxher@g.educaand.es", asunto,
					mensaje + "\nMensaje enviado por: " + nombre + "\nCorreo: " + correo);

			sesion.setAttribute("mensajeContactar", "Mensaje enviado exitosamente.");
			response.sendRedirect("Contacto");
		} else {
			sesion.setAttribute("errorContactar", "Debe rellenar todos los campos para enviar el mensaje.");
			response.sendRedirect("Contacto");

		}
	}

}
