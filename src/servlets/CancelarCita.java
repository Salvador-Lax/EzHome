package servlets;

import java.io.IOException;

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
import miModelo.ClienteDAO;
import miModelo.ClienteVO;
import miModelo.InmuebleDAO;
import miModelo.InmuebleVO;

/**
 * Servlet implementation class CancelarCita
 */
@WebServlet("/CancelarCita")
public class CancelarCita extends HttpServlet {
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
		CadenaAleatoria ca;

		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos que el usuario este logeado
		if ((cvo != null && cvo.getCodigoVerificar().equals("0")) || avo != null) {
			ca = new CadenaAleatoria();
			// Creamos un codigo de eliminacion
			sesion.setAttribute("codigoEliminar", ca.generar());
			request.getRequestDispatcher("WEB-INF/cancelarCita.jsp").forward(request, response);
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
		AgenteDAO adao;
		CitaDAO cidao;
		InmuebleVO ivo;
		InmuebleDAO idao;
		String inm;
		String age;
		String cli;
		String codigoEliminar;
		String codigoInsertado;
		Correo co;

		sesion = request.getSession();

		// Cargamos los parametros de la pagina
		cli = request.getParameter("cli");
		inm = request.getParameter("inm");
		age = request.getParameter("age");

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos que los parametros no sean nulos
		if ((cli != null || age != null) && inm != null) {
			if (avo != null) {
				codigoEliminar = (String) sesion.getAttribute("codigoEliminar");
				codigoInsertado = request.getParameter("eliminar");

				// Comprobamos que el codigo introducido equivalga al de eliminacion
				if (codigoInsertado.equals(codigoEliminar)) {

					cidao = new CitaDAO();

					// Comprobamos que la cita sea eliminada
					if (cidao.eliminarCita("cita.codigoinmueble= '" + inm + "' AND cita.idcliente= "
							+ Integer.parseInt(cli) + " AND cita.idagente= " + (int) avo.getId())) {
						cdao = new ClienteDAO();

						cvo = cdao.consultaGenerica("id= " + Long.parseLong(cli));

						idao = new InmuebleDAO();

						ivo = idao.consultaGenerica("codigo= '" + inm + "'");

						co = new Correo();

						// Enviamos correos de confirmacion de eliminacion de la cita
						co.enviar(cvo.getEmail(), "Cita cancelada", "La cita que solicito para " + ivo.getTnombre()
								+ ", " + ivo.getStnombre() + "\nen la dirección " + ivo.getDireccion() + ", "
								+ ivo.getPoblacion() + ", " + ivo.getProvincia() + "\nha sido cancelada por el agente "
								+ avo.getApellido() + ", " + avo.getNombre()
								+ ".\nSi esto fue inesperado, por favor póngase en contacto con el agente.\nTelefono: "
								+ avo.getTelefono() + " Correo: " + avo.getEmail());

						co.enviar(avo.getEmail(), "Confirmación de cita cancelada",
								"Este correo confirma que ha cancelado la cita con " + cvo.getApellido() + ", "
										+ cvo.getNombre() + "\npara el inmueble de Código: " + ivo.getCodigo()
										+ " Dirección: " + ivo.getDireccion() + ", " + ivo.getPoblacion() + ", "
										+ ivo.getProvincia()
										+ ".\nSi usted no ha realizado dicha acción, póngase en contacto con el administrador del sistema.");

						response.sendRedirect("Citas");
					} else {
						sesion.setAttribute("errorCancelarCita", "Ocurrió un error al intentar cancelar la cita.");
						response.sendRedirect("CancelarCita?cli=" + cli + "&inm=" + inm);
					}
				} else {
					sesion.setAttribute("errorCancelarCita", "Código equivocado");
					response.sendRedirect("CancelarCita?cli=" + cli + "&inm=" + inm);
				}
			} else {
				if (cvo != null) {
					codigoEliminar = (String) sesion.getAttribute("codigoEliminar");
					codigoInsertado = request.getParameter("eliminar");

					// Comprobamos que el codigo introducido equivalga al de eliminacion
					if (codigoInsertado.equals(codigoEliminar)) {

						cidao = new CitaDAO();

						// Comprobamos que la cita sea eliminada
						if (cidao.eliminarCita("cita.codigoinmueble= '" + inm + "' AND cita.idcliente= "
								+ (int) cvo.getId() + " AND cita.idagente= " + Integer.parseInt(age))) {
							adao = new AgenteDAO();

							avo = adao.consultaGenerica("id= " + Long.parseLong(age));

							idao = new InmuebleDAO();

							ivo = idao.consultaGenerica("codigo= '" + inm + "'");

							co = new Correo();

							// Enviamos correos de confirmacion de eliminacion de la cita
							co.enviar(avo.getEmail(), "Cita cancelada", "La cita solicitada por el usuario "
									+ cvo.getApellido() + ", " + cvo.getNombre() + "\npara el inmueble de Código: "
									+ ivo.getCodigo() + " Dirección: " + ivo.getDireccion() + ", " + ivo.getPoblacion()
									+ ", " + ivo.getProvincia()
									+ ".\nha sido cancelada. Si esta acción fue sospechosa, póngase en contacto con el usuario.\nTelefono: "
									+ cvo.getTelefono() + " Correo: " + cvo.getEmail());

							co.enviar(cvo.getEmail(), "Confirmación de cita cancelada",
									"Usted ha cancelado la cita que solicito para " + ivo.getTnombre() + ", "
											+ ivo.getStnombre() + "\nen la dirección " + ivo.getDireccion() + ", "
											+ ivo.getPoblacion() + ", " + ivo.getProvincia() + " con el agente "
											+ avo.getApellido() + ", " + avo.getNombre()
											+ ".\nSi usted no ha realizado dicha acción, póngase en contacto con el administrador del sistema.");

							response.sendRedirect("Citas");
						} else {
							sesion.setAttribute("errorCancelarCita", "Ocurrió un error al intentar cancelar la cita.");
							response.sendRedirect("CancelarCita?age=" + age + "&inm=" + inm);
						}
					} else {
						sesion.setAttribute("errorCancelarCita", "Código equivocado");
						response.sendRedirect("CancelarCita?age=" + age + "&inm=" + inm);
					}
				}
			}
		} else {
			response.sendRedirect("Citas");
		}

	}

}
