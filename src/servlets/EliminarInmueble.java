package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miBibliotecaGeneral.CadenaAleatoria;
import miBibliotecaGeneral.Correo;
import miModelo.AgenteDAO;
import miModelo.AgenteVO;
import miModelo.CitaDAO;
import miModelo.CitaVO;
import miModelo.ClienteDAO;
import miModelo.ClienteVO;
import miModelo.ImagenDAO;
import miModelo.InmuebleDAO;
import miModelo.InmuebleVO;

/**
 * Servlet implementation class EliminarInmueble
 */
@WebServlet("/EliminarInmueble")
public class EliminarInmueble extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
	AgenteVO avo;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CadenaAleatoria ca;

		sesion = request.getSession();

		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos que el usuario este logueado y generamos un codigo de eliminacion
		if (avo != null) {
			ca = new CadenaAleatoria();
			sesion.setAttribute("codigoEliminar", ca.generar());
			request.getRequestDispatcher("WEB-INF/eliminarInmueble.jsp").forward(request, response);
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
		ClienteVO cvo;
		ClienteDAO cdao;
		AgenteVO avo;
		AgenteDAO adao;
		InmuebleVO ivo;
		InmuebleDAO idao;
		ImagenDAO imgdao;
		ArrayList<CitaVO> citas;
		CitaDAO cidao;
		String codInmueble;
		String codigoEliminar;
		String codigoInsertado;
		Correo co;

		sesion = request.getSession();

		codigoEliminar = (String) sesion.getAttribute("codigoEliminar");
		codigoInsertado = request.getParameter("eliminar");
		codInmueble = request.getParameter("inm");

		// Comprobamos que el codigo de eliminacion introducido equivalga al generado
		if (codigoInsertado.equals(codigoEliminar)) {

			imgdao = new ImagenDAO();

			// Comprobamos si inmueble tiene galeria, de tenerla la eliminamos
			if (!imgdao.consultaGaleria(codInmueble).isEmpty()) {
				if (imgdao.eliminarImagen("imagen.codigoinmueble = '" + codInmueble + "'")) {

					cidao = new CitaDAO();

					citas = cidao.consultaTotal("cita.codigoinmueble= '" + codInmueble + "'");

					// Comprobamos si el Inmueble tiene citas, de tenerlas se eliminaran y se
					// enviara un correo confirmando esta accion
					if (!citas.isEmpty()) {
						if (cidao.eliminarCita("cita.codigoinmueble= '" + codInmueble + "'")) {
							cdao = new ClienteDAO();
							adao = new AgenteDAO();
							idao = new InmuebleDAO();
							co = new Correo();

							ivo = idao.consultaGenerica("codigo= '" + codInmueble + "'");
							avo = adao.consultaGenerica("id= " + ivo.getIdagente());

							for (CitaVO cita : citas) {
								cvo = cdao.consultaGenerica("id= " + cita.getIdcliente());

								co.enviar(cvo.getEmail(), "Cita cancelada", "La cita que solicito para "
										+ ivo.getTnombre() + ", " + ivo.getStnombre() + "\nen la dirección "
										+ ivo.getDireccion() + ", " + ivo.getPoblacion() + ", " + ivo.getProvincia()
										+ "\nha sido cancelada debido a que dicha propiedad ya no se haya disponible."
										+ "\nSi esto fue inesperado, por favor póngase en contacto con el agente "
										+ avo.getApellido() + ", " + avo.getNombre() + ".\nTeléfono: "
										+ avo.getTelefono() + " Correo: " + avo.getEmail());

							}

							// Eliminamos el Inmueble
							if (idao.eliminarInmueble(codInmueble)) {
								response.sendRedirect("GestionInmueble");
							} else {
								sesion.setAttribute("errorEliminarInmueble",
										"Ocurrió un error al eliminar el Inmueble");
								response.sendRedirect("EliminarInmueble?inm=" + codInmueble);
							}
						} else {
							sesion.setAttribute("errorEliminarInmueble", "Ocurrió un error al eliminar el Inmueble");
							response.sendRedirect("EliminarInmueble?inm=" + codInmueble);
						}
					} else {
						idao = new InmuebleDAO();

						if (idao.eliminarInmueble(codInmueble)) {
							response.sendRedirect("GestionInmueble");
						} else {
							sesion.setAttribute("errorEliminarInmueble", "Ocurrió un error al eliminar el Inmueble");
							response.sendRedirect("EliminarInmueble?inm=" + codInmueble);
						}
					}
				} else {
					sesion.setAttribute("errorEliminarInmueble",
							"Ocurrió un error al eliminar las imagenes pertenecientes al Inmueble");
					response.sendRedirect("EliminarInmueble?inm=" + codInmueble);
				}
			} else {
				cidao = new CitaDAO();

				citas = cidao.consultaTotal("cita.codigoinmueble= '" + codInmueble + "'");

				// Comprobamos si el Inmueble tiene citas, de tenerlas se eliminaran y se
				// enviara un correo confirmando esta accion
				if (!citas.isEmpty()) {
					if (cidao.eliminarCita("cita.codigoinmueble = '" + codInmueble + "'")) {
						cdao = new ClienteDAO();
						adao = new AgenteDAO();
						idao = new InmuebleDAO();
						co = new Correo();

						ivo = idao.consultaGenerica("codigo= '" + codInmueble + "'");
						avo = adao.consultaGenerica("id= " + ivo.getIdagente());

						for (CitaVO cita : citas) {
							cvo = cdao.consultaGenerica("id= " + cita.getIdcliente());

							co.enviar(cvo.getEmail(), "Cita cancelada", "La cita que solicito para " + ivo.getTnombre()
									+ ", " + ivo.getStnombre() + "\nen la dirección " + ivo.getDireccion() + ", "
									+ ivo.getPoblacion() + ", " + ivo.getProvincia()
									+ "\nha sido cancelada debido a que dicha propiedad ya no se haya disponible."
									+ "\nSi esto fue inesperado, por favor póngase en contacto con el agente "
									+ avo.getApellido() + ", " + avo.getNombre() + ".\nTeléfono: " + avo.getTelefono()
									+ " Correo: " + avo.getEmail());

						}

						// Eliminamos el Inmueble
						if (idao.eliminarInmueble(codInmueble)) {
							response.sendRedirect("GestionInmueble");
						} else {
							sesion.setAttribute("errorEliminarInmueble", "Ocurrió un error al eliminar el Inmueble");
							response.sendRedirect("EliminarInmueble?inm=" + codInmueble);
						}
					} else {
						sesion.setAttribute("errorEliminarInmueble", "Ocurrió un error al eliminar el Inmueble");
						response.sendRedirect("EliminarInmueble?inm=" + codInmueble);
					}
				} else {
					idao = new InmuebleDAO();

					if (idao.eliminarInmueble(codInmueble)) {
						response.sendRedirect("GestionInmueble");
					} else {
						sesion.setAttribute("errorEliminarInmueble", "Ocurrió un error al eliminar el Inmueble");
						response.sendRedirect("EliminarInmueble?inm=" + codInmueble);
					}
				}
			}
		} else {
			sesion.setAttribute("errorEliminarInmueble", "Codigo equivocado");
			response.sendRedirect("EliminarInmueble?inm=" + codInmueble);
		}
	}

}
