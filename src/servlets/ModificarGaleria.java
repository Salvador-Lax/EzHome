package servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import miBibliotecaGeneral.ConversionArchivo;
import miModelo.AgenteVO;
import miModelo.ImagenDAO;
import miModelo.ImagenVO;

/**
 * Servlet implementation class ModificarGaleria
 */
@WebServlet("/ModificarGaleria")
public class ModificarGaleria extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
	ImagenDAO imdao;
	String codInmueble;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AgenteVO avo;
		ArrayList<ImagenVO> galeria;

		sesion = request.getSession();

		avo = (AgenteVO) sesion.getAttribute("agente");

		if (avo != null) {
			codInmueble = request.getParameter("inm");

			if (codInmueble != null) {

				imdao = new ImagenDAO();

				galeria = new ArrayList<ImagenVO>();

				galeria = imdao.consultaGaleria(codInmueble);

				if (!galeria.isEmpty()) {
					sesion.setAttribute("galeria", galeria);
				} else {
					sesion.removeAttribute("galeria");
					sesion.setAttribute("errorModificarGaleria", "Esta Galería no contiene imágenes o no existe.");
				}

			} else {
				sesion.setAttribute("errorModificarGaleria", "Error, no se especifico ningún inmueble");
			}

			request.getRequestDispatcher("WEB-INF/modificarGaleria.jsp").forward(request, response);
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
		ImagenVO imvo;
		ConversionArchivo ca;

		ArrayList<File> imagenes;

		File archivo;
		int tamanioMaximoArchivo;
		int tamanioMaximoMemoria;
		String ruta;
		String rutaArchivo;
		String nombreArchivo;
		ServletFileUpload sfu;
		String tipoContenido;
		FileItem objetoArchivo;
		List<FileItem> listaArchivo;
		DiskFileItemFactory dfif;

		sesion = request.getSession();

		imagenes = new ArrayList<File>();

		response.getWriter();

		ruta = this.getServletContext().getRealPath("/WEB-INF/data/");

		rutaArchivo = ruta;

		tipoContenido = request.getContentType();

		// Recorremos el formulario y guardamos la informacion obtenida en las variables
		if (tipoContenido.indexOf("multipart/form-data") >= 0) {
			dfif = new DiskFileItemFactory();
			tamanioMaximoMemoria = 5000 * 1024;
			dfif.setSizeThreshold(tamanioMaximoMemoria);
			dfif.setRepository(new File("."));

			sfu = new ServletFileUpload(dfif);

			tamanioMaximoArchivo = 5000 * 1024;
			sfu.setSizeMax(tamanioMaximoArchivo);

			try {
				listaArchivo = sfu.parseRequest(request);
				Iterator<FileItem> i = listaArchivo.iterator();

				while (i.hasNext()) {
					objetoArchivo = i.next();

					if (!objetoArchivo.isFormField()) {
						nombreArchivo = objetoArchivo.getName();

						if (nombreArchivo.lastIndexOf("/") >= 0) {
							archivo = new File(rutaArchivo + nombreArchivo.substring(nombreArchivo.lastIndexOf("/")));
						} else {
							archivo = new File(
									rutaArchivo + "/" + nombreArchivo.substring(nombreArchivo.lastIndexOf("/") + 1));
						}

						objetoArchivo.write(archivo);

						imagenes.add(archivo);
					}
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}

		imdao = new ImagenDAO();
		codInmueble = request.getParameter("inm");

		// Comprobamos si la galeria esta vacia y cargamos las imagenes del formulario
		if (!imagenes.isEmpty()) {
			for (int i = 0; i < imagenes.size(); i++) {
				ca = new ConversionArchivo();

				imvo = new ImagenVO(FilenameUtils.getBaseName(imagenes.get(i).getName()),
						ca.convertirArchivoABytes(imagenes.get(i)));

				imdao.crearImagen(imvo, codInmueble);

				imagenes.get(i).delete();
			}

			response.sendRedirect("ModificarGaleria?inm=" + codInmueble);
		} else {
			sesion.setAttribute("errorNuevaGaleria", "Ocurrió un error al cargar las imágenes");
			response.sendRedirect("ModificarGaleria?inm=" + codInmueble);
		}

	}

}
