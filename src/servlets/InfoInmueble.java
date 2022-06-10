package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miModelo.CitaDAO;
import miModelo.CitaVO;
import miModelo.ClienteVO;
import miModelo.ImagenDAO;
import miModelo.ImagenVO;
import miModelo.InmuebleDAO;
import miModelo.InmuebleVO;

/**
 * Servlet implementation class InfoInmueble
 */
@WebServlet("/InfoInmueble")
public class InfoInmueble extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion;
		InmuebleVO ivo;
		InmuebleDAO idao;
		ClienteVO cvo;
		CitaVO civo;
		CitaDAO cidao;
		ArrayList<ImagenVO> galeria;
		ImagenDAO imdao;

		sesion = request.getSession();

		idao = new InmuebleDAO();
		ivo = idao.consultaGenerica("codigo= '" + request.getParameter("inm") + "'");

		// Comprobamos que el inmueble exista
		if (ivo != null) {
			sesion.setAttribute("infoinmueble", ivo);

			imdao = new ImagenDAO();

			galeria = new ArrayList<ImagenVO>();

			galeria = imdao.consultaGaleria(ivo.getCodigo());

			// Comprobamos que tenga una galeria y la cargamos
			if (!galeria.isEmpty()) {
				sesion.setAttribute("galeria", galeria);
			} else {
				sesion.removeAttribute("galeria");
				sesion.setAttribute("errorGaleria", "No se encontraron imágenes en la galería");
			}

			cvo = (ClienteVO) sesion.getAttribute("cliente");

			// Comprobamos si el usuario tiene una cita existente con este inmueble
			if (cvo != null && cvo.getCodigoVerificar().equals("0")) {
				cidao = new CitaDAO();
				civo = cidao.consultaGenerica(
						"idcliente= " + cvo.getId() + " AND codigoinmueble= '" + ivo.getCodigo() + "'");
				if (civo != null) {
					sesion.setAttribute("citaExiste", true);
				} else {
					sesion.setAttribute("citaExiste", false);
				}
			}
		} else {
			sesion.setAttribute("errorInfo", "El Inmueble buscado no se encuentra o no existe");
		}

		request.getRequestDispatcher("WEB-INF/infoInmueble.jsp").forward(request, response);
	}

}
