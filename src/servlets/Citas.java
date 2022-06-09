package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.CitasBean;
import miModelo.AgenteDAO;
import miModelo.AgenteVO;
import miModelo.CitaDAO;
import miModelo.CitaVO;
import miModelo.ClienteDAO;
import miModelo.ClienteVO;
import miModelo.InmuebleDAO;
import miModelo.InmuebleVO;

/**
 * Servlet implementation class Citas
 */
@WebServlet("/Citas")
public class Citas extends HttpServlet {
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
		ClienteDAO cdao;
		AgenteVO avo;
		AgenteDAO adao;
		InmuebleVO ivo;
		InmuebleDAO idao;
		ArrayList<CitaVO> civo;
		CitaDAO cidao;
		CitasBean cb;
		ArrayList<CitasBean> cbs;

		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos que el usuario este logueado
		if (avo != null) {
			cidao = new CitaDAO();
			civo = new ArrayList<CitaVO>();

			civo = cidao.consultaTotal("idagente= " + avo.getId());

			// Si no existen citas mostraremos un mensaje, en caso contrario enviaremos una
			// lista de citas a la pagina para mostrarlas
			if (civo.isEmpty()) {
				sesion.setAttribute("mensajeCita", "Actualmente no ha recibido ninguna solicitud");
			} else {
				cbs = new ArrayList<CitasBean>();

				cdao = new ClienteDAO();
				idao = new InmuebleDAO();

				for (CitaVO cita : civo) {
					cvo = cdao.consultaGenerica("id= " + cita.getIdcliente());
					ivo = idao.consultaGenerica("codigo= '" + cita.getCodigoinmueble() + "'");

					cb = new CitasBean(ivo.getTnombre(), ivo.getStnombre(), ivo.getProvincia(), ivo.getPoblacion(),
							ivo.getDireccion(), cita.getFechayhora(), (cvo.getApellido() + ", " + cvo.getNombre()),
							cvo.getTelefono(), cvo.getEmail(), cvo.getId(), avo.getId(), ivo.getCodigo());

					cbs.add(cb);
				}
				sesion.setAttribute("citas", cbs);
			}
			request.getRequestDispatcher("WEB-INF/citas.jsp").forward(request, response);
		} else {
			if (cvo != null && cvo.getCodigoVerificar().equals("0")) {
				cidao = new CitaDAO();
				civo = new ArrayList<CitaVO>();

				civo = cidao.consultaTotal("idcliente= " + cvo.getId());

				// Si no existen citas mostraremos un mensaje, en caso contrario enviaremos una
				// lista de citas a la pagina para mostrarlas
				if (civo.isEmpty()) {
					sesion.setAttribute("mensajeCita", "Actualmente no ha solicitado ninguna cita");
				} else {
					cbs = new ArrayList<CitasBean>();

					adao = new AgenteDAO();
					idao = new InmuebleDAO();

					for (CitaVO cita : civo) {
						avo = adao.consultaGenerica("id= " + cita.getIdagente());
						ivo = idao.consultaGenerica("codigo= '" + cita.getCodigoinmueble() + "'");

						cb = new CitasBean(ivo.getTnombre(), ivo.getStnombre(), ivo.getProvincia(), ivo.getPoblacion(),
								ivo.getDireccion(), cita.getFechayhora(), (avo.getApellido() + ", " + avo.getNombre()),
								avo.getTelefono(), avo.getEmail(), cvo.getId(), avo.getId(), ivo.getCodigo());

						cbs.add(cb);
					}
					sesion.setAttribute("citas", cbs);
				}
				request.getRequestDispatcher("WEB-INF/citas.jsp").forward(request, response);
			} else {
				response.sendRedirect("Login");
			}
		}
	}
}
