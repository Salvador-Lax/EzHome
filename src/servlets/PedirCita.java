package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miBibliotecaGeneral.Correo;
import miModelo.AgenteDAO;
import miModelo.AgenteVO;
import miModelo.CitaDAO;
import miModelo.ClienteVO;
import miModelo.InmuebleVO;

/**
 * Servlet implementation class PedirCita
 */
@WebServlet("/PedirCita")
public class PedirCita extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion;
		ClienteVO cvo;
		InmuebleVO ivo;
		AgenteVO avo;
		AgenteDAO adao;
		CitaDAO cdao;
		Correo co;

		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		ivo = (InmuebleVO) sesion.getAttribute("infoinmueble");

		cdao = new CitaDAO();

		// Comprobamos que la creacion de cita funcione con los parametros establecidos
		if (cdao.crearCita((int) cvo.getId(), ivo.getIdagente(), ivo.getCodigo())) {
			adao = new AgenteDAO();
			avo = adao.consultaGenerica("id= " + ivo.getIdagente());

			co = new Correo();

			// Enviamos un correo al usuario y al agente encargado del producto para
			// notificar de la cita pedida
			co.enviar(avo.getEmail(), "Nueva solicitud de citación",
					avo.getNombre() + ", ha recibido una nueva solicitud de visita a la propiedad:\n" + ivo.getTnombre()
							+ ", " + ivo.getStnombre() + ".en " + ivo.getDireccion() + ", " + ivo.getPoblacion() + ", "
							+ ivo.getProvincia() + ".\nPor el Usuario: " + cvo.getApellido() + ", " + cvo.getNombre()
							+ ". Correo y Teléfono: " + cvo.getEmail() + " " + cvo.getTelefono()
							+ ".\nDetermine una fecha y hora para la cita lo antes posible.");

			co.enviar(cvo.getEmail(), "Su solicitud de citación " + cvo.getNombre(),
					"Este correo confirma que ha hecho una solicitud de citación de la propiedad:\n" + ivo.getTnombre()
							+ ", " + ivo.getStnombre() + "en " + ivo.getDireccion() + ", " + ivo.getPoblacion() + ", "
							+ ivo.getProvincia()
							+ ".\nPronto recibirá información sobre la fecha y hora de parte del Agente: "
							+ avo.getApellido() + ", " + avo.getNombre() + ".\nCorreo y Teléfono: " + avo.getEmail()
							+ " " + avo.getTelefono() + ".");

			sesion.setAttribute("alertaSolicitud",
					"Cita solicitada con éxito, espere a una respuesta del agente a cargo,</br>"
							+ "\n podrá ver el estado de su cita en el apartado Citas de su perfil.");
			response.sendRedirect("InfoInmueble?inm=" + ivo.getCodigo());
		} else {
			sesion.setAttribute("alertaSolicitud",
					"Error al solicitar cita, puede que ya haya solicitado una cita para este Inmueble");
			response.sendRedirect("InfoInmueble?inm=" + ivo.getCodigo());
		}
	}
}
