package servlets;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import miBibliotecaGeneral.Correo;
import miModelo.AgenteVO;
import miModelo.CitaDAO;
import miModelo.CitaVO;
import miModelo.ClienteDAO;
import miModelo.ClienteVO;
import miModelo.InmuebleDAO;
import miModelo.InmuebleVO;

/**
 * Servlet implementation class ModificarCita
 */
@WebServlet("/ModificarCita")
public class ModificarCita extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
	AgenteVO avo;
	String idCliente;
	String codigoInmueble;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sesion = request.getSession();

		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos que el usuario este logueado y que los parametros existan
		if (avo != null) {
			idCliente = request.getParameter("cli");
			codigoInmueble = request.getParameter("inm");

			if (idCliente != null || codigoInmueble != null) {
				request.getRequestDispatcher("WEB-INF/modificarCita.jsp").forward(request, response);
			} else {
				response.sendRedirect("Citas");
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
		ClienteVO cvo;
		ClienteDAO cdao;
		InmuebleVO ivo;
		InmuebleDAO idao;
		CitaVO civo;
		CitaDAO cidao;
		Correo co;

		String fh;
		Timestamp fechayhora;

		sesion = request.getSession();

		// Cargamos la fecha y hora y la parseamos
		fh = request.getParameter("fechayhora");

		if (StringUtils.countMatches(fh, ":") == 1) {
			fh += ":00";
		}

		fechayhora = Timestamp.valueOf(fh.replace("T", " "));

		// Si la fecha no es nula, cargaremos los datos de la cita para modificarla
		if (fechayhora != null) {
			idCliente = request.getParameter("cli");
			codigoInmueble = request.getParameter("inm");
			avo = (AgenteVO) sesion.getAttribute("agente");

			cidao = new CitaDAO();

			civo = cidao.consultaGenerica("idcliente= " + Integer.parseInt(idCliente) + " AND idagente= "
					+ (int) avo.getId() + " AND codigoinmueble='" + codigoInmueble + "'");

			if (civo != null) {
				civo.setFechayhora(fechayhora);

				if (cidao.modificarCita(civo)) {
					cdao = new ClienteDAO();
					cvo = cdao.consultaGenerica("id= " + Long.parseLong(idCliente));

					idao = new InmuebleDAO();
					ivo = idao.consultaGenerica("codigo= '" + codigoInmueble + "'");

					co = new Correo();

					// Enviamos un correo confirmando que se ha establecido o cambiado la fecha y
					// hora de la cita
					co.enviar(cvo.getEmail(), "Cita concertada",
							cvo.getNombre() + ", tu solicitud de citación para la propiedad: " + ivo.getTnombre() + ", "
									+ ivo.getStnombre() + "\nen " + ivo.getDireccion() + ", " + ivo.getPoblacion()
									+ ", " + ivo.getProvincia()
									+ "\nha sido determinado para ser visitado a fecha y hora: " + civo.getFechayhora()
									+ "\npor el Agente: " + avo.getApellido() + ", " + avo.getNombre());

					response.sendRedirect("Citas");
				} else {
					sesion.setAttribute("errorCita", "Ocurrió un error al modificar la fecha y hora de la Cita");
					response.sendRedirect("ModificarCita?cli=" + idCliente + "&inm=" + codigoInmueble);
				}
			} else {
				sesion.setAttribute("errorCita", "Cita a modificar invalida");
				response.sendRedirect("ModificarCita?cli=" + idCliente + "&inm=" + codigoInmueble);
			}

		} else {
			sesion.setAttribute("errorCita", "Error al modificar la fecha y hora");
			response.sendRedirect("ModificarCita?cli=" + idCliente + "&inm=" + codigoInmueble);
		}

	}

}
