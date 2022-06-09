package servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miModelo.AgenteVO;
import miModelo.ClienteVO;

/**
 * Servlet implementation class CargarImagenAgente
 */
@WebServlet("/CargarImagenPerfil")
public class CargarImagenPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion;
		AgenteVO avo;
		ClienteVO cvo;
		byte[] buf = null;
		byte[] imagen;
		OutputStream out;

		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		imagen = null;

		if (avo != null) {
			imagen = avo.getImagen();
		}

		if (cvo != null) {
			imagen = cvo.getImagen();
		}

		buf = imagen;

		response.setContentType("image /");

		out = response.getOutputStream();

		if (buf != null) {
			out.write(buf);
		}

		out.flush();

		out.close();
	}

}
