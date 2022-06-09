package servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import miModelo.ImagenDAO;
import miModelo.ImagenVO;

/**
 * Servlet implementation class CargarImagenInmueble
 */
@WebServlet("/CargarImagenInmueble")
public class CargarImagenInmueble extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ImagenVO imgvo;
		ImagenDAO imgdao;
		byte[] buf = null;
		OutputStream out;
		String inmCod;
		String imgId;

		inmCod = request.getParameter("inm");
		imgId = request.getParameter("img");

		imgdao = new ImagenDAO();

		imgvo = null;

		if (inmCod != null) {
			imgvo = imgdao.consultaGenerica("codigoinmueble= '" + inmCod + "'");
		}

		if (imgId != null) {
			imgvo = imgdao.consultaGenerica("id= " + imgId);
		}

		buf = imgvo.getImg();

		response.setContentType("imagen /");

		out = response.getOutputStream();

		if (buf != null) {
			out.write(buf);
		}

		out.flush();

		out.close();
	}

}
