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
import miModelo.ClienteDAO;
import miModelo.ClienteVO;

/**
 * Servlet implementation class ContactarCita
 */
@WebServlet("/ContactarCita")
public class ContactarCita extends HttpServlet {
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

		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		if ((cvo != null && cvo.getCodigoVerificar().equals("0")) || avo != null) {
			request.getRequestDispatcher("WEB-INF/contactarCita.jsp").forward(request, response);
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
		String cli;
		String age;
		String asunto;
		String mensaje;
		Correo co;

		sesion = request.getSession();

		cli = request.getParameter("cli");
		age = request.getParameter("age");

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos que los parametros cargadosno sean nulos y si estamos logueados
		if (cli != null || age != null) {
			if (avo != null) {
				asunto = request.getParameter("asunto");
				mensaje = request.getParameter("mensaje");

				// Comprobamos que el asunto y el mensaje no esten vacios
				if (!asunto.isEmpty() && !mensaje.isEmpty()) {
					cdao = new ClienteDAO();

					cvo = cdao.consultaGenerica("id = " + Long.parseLong(cli));

					co = new Correo();

					// Enviamos correo con el contenido introducido en la pagina
					co.enviar(cvo.getEmail(), asunto,
							mensaje + "\nMensaje enviado por Agente: " + avo.getApellido() + ", " + avo.getNombre()
									+ "\nCorreo: " + avo.getEmail() + "\nTeléfono: " + avo.getTelefono());

					response.sendRedirect("Citas");
				} else {
					sesion.setAttribute("errorContactarCita",
							"Debe especificar el asunto y y el contenido del correo.");
					response.sendRedirect("ContactarCita?cli=" + cli);
				}
			} else {
				if (cvo != null) {
					asunto = request.getParameter("asunto");
					mensaje = request.getParameter("mensaje");

					// Comprobamos que el asunto y el mensaje no esten vacios
					if (!asunto.isEmpty() && !mensaje.isEmpty()) {
						adao = new AgenteDAO();

						avo = adao.consultaGenerica("id = " + Long.parseLong(age));

						co = new Correo();

						// Enviamos correo con el contenido introducido en la pagina
						co.enviar(avo.getEmail(), asunto,
								mensaje + "\nMensaje enviado por Cliente: " + cvo.getApellido() + ", " + cvo.getNombre()
										+ "\nCorreo: " + cvo.getEmail() + "\nTeléfono: " + cvo.getTelefono());

						response.sendRedirect("Citas");
					} else {
						sesion.setAttribute("errorContactarCita",
								"Debe especificar el asunto y y el contenido del correo.");
						response.sendRedirect("ContactarCita?age=" + age);
					}
				}
			}
		} else {
			response.sendRedirect("Citas");
		}
	}

}
