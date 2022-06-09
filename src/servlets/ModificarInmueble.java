package servlets;

import java.io.IOException;

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
 * Servlet implementation class ModificarInmueble
 */
@WebServlet("/ModificarInmueble")
public class ModificarInmueble extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
	InmuebleVO ivo;
	InmuebleDAO idao;
	String codigoInmueble;

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

		// Comprobamos que el usuario este logueado y cargamos los datos del inmueble en
		// el formulario
		if (avo != null) {
			codigoInmueble = request.getParameter("inm");

			if (codigoInmueble != null) {
				idao = new InmuebleDAO();

				ivo = idao.consultaGenerica("codigo= '" + codigoInmueble + "'");

				sesion.setAttribute("datosInmueble", ivo);

				request.getRequestDispatcher("WEB-INF/modificarInmueble.jsp").forward(request, response);
			} else {
				response.sendRedirect("GestionInmueble");
			}

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
		InmuebleVO ivoMod;
		String descripcion;
		Double precio;
		Double metroCuadrado;
		int numHabitacion;
		String provincia;
		String poblacion;
		int codigoPostal;
		String direccion;
		String coordenada;
		String contenido;

		codigoInmueble = request.getParameter("inm");

		idao = new InmuebleDAO();

		ivo = idao.consultaGenerica("codigo = '" + codigoInmueble + "'");

		// Comprobamos que el inmueble exista y comprobamos que el formulario contuviera
		// datos
		if (ivo != null) {
			descripcion = request.getParameter("descripcion");
			if (descripcion.equals("")) {
				descripcion = ivo.getDescripcion();
			}

			contenido = request.getParameter("precio");
			if (contenido.length() == 0) {
				precio = ivo.getPrecio();
			} else {
				precio = Double.parseDouble(contenido);
			}

			contenido = request.getParameter("metrocuadrado");
			if (contenido.length() == 0) {
				metroCuadrado = ivo.getMetrocuadrado();
			} else {
				metroCuadrado = Double.parseDouble(contenido);
			}

			contenido = request.getParameter("numhabitacion");
			if (contenido.length() == 0) {
				numHabitacion = ivo.getNumhabitacion();
			} else {
				numHabitacion = Integer.parseInt(contenido);

			}

			provincia = request.getParameter("provincia");
			if (provincia.equals("")) {
				provincia = ivo.getProvincia();
			}

			poblacion = request.getParameter("poblacion");
			if (poblacion.equals("")) {
				poblacion = ivo.getPoblacion();
			}

			contenido = request.getParameter("codigopostal");
			if (contenido.length() == 0) {
				codigoPostal = ivo.getCodigopostal();
			} else {
				codigoPostal = Integer.parseInt(contenido);

			}

			direccion = request.getParameter("direccion");
			if (direccion.equals("")) {
				direccion = ivo.getDireccion();
			}

			coordenada = request.getParameter("coordenada");
			if (coordenada.equals("")) {
				coordenada = ivo.getCoordenada();
			}

			ivoMod = new InmuebleVO(descripcion, precio, metroCuadrado, numHabitacion, provincia, poblacion,
					codigoPostal, direccion, coordenada);

			// Comprobamos si la modificacion ha funcionado
			if (idao.modificarInmueble(ivoMod, codigoInmueble)) {
				response.sendRedirect("GestionInmueble");
			} else {
				sesion.setAttribute("errorModificarInmueble", "Error al modificar el Inmueble");
				response.sendRedirect("ModificarInmueble");
			}
		} else {
			sesion.setAttribute("errorModificarInmueble", "El Inmueble que desea modificar no existe.");
			response.sendRedirect("ModificarInmueble");
		}

	}
}
