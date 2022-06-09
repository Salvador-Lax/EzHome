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

import miBibliotecaGeneral.CadenaAleatoria;
import miBibliotecaGeneral.ConversionArchivo;
import miModelo.AgenteVO;
import miModelo.ImagenDAO;
import miModelo.ImagenVO;
import miModelo.InmuebleDAO;
import miModelo.InmuebleVO;
import miModelo.TipoDAO;
import miModelo.TipoVO;

/**
 * Servlet implementation class AnunciarInmueble
 */
@WebServlet("/AnunciarInmueble")
public class AnunciarInmueble extends HttpServlet {
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

		sesion = request.getSession();

		avo = (AgenteVO) sesion.getAttribute("agente");

		if (avo != null) {
			request.getRequestDispatcher("WEB-INF/anunciarInmueble.jsp").forward(request, response);

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
		InmuebleVO ivo;
		InmuebleDAO idao;
		TipoVO tvo;
		TipoDAO tdao;
		ImagenVO imvo;
		ImagenDAO imdao;
		CadenaAleatoria codigo;
		ConversionArchivo ca;

		String codigoInmueble;
		String tipo;
		String subTipo;
		String descripcion;
		Double precio;
		Double metroCuadrado;
		int numHabitacion;
		String provincia;
		String poblacion;
		int codigoPostal;
		String direccion;
		String coordenada;
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

		codigoInmueble = "";
		tipo = "";
		subTipo = "";
		descripcion = "";
		precio = 0.0;
		metroCuadrado = 0.0;
		numHabitacion = 0;
		provincia = "";
		poblacion = "";
		codigoPostal = 0;
		direccion = "";
		coordenada = "";
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

					if (objetoArchivo.getFieldName().equals("tipo")) {
						tipo = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("subtipo")) {
						subTipo = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("descripcion")) {
						descripcion = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("precio")) {
						precio = Double.parseDouble(objetoArchivo.getString("UTF-8"));
					}
					if (objetoArchivo.getFieldName().equals("metrocuadrado")) {
						metroCuadrado = Double.parseDouble(objetoArchivo.getString("UTF-8"));
					}
					if (objetoArchivo.getFieldName().equals("numhabitacion")) {
						numHabitacion = Integer.parseInt(objetoArchivo.getString("UTF-8"));
					}
					if (objetoArchivo.getFieldName().equals("provincia")) {
						provincia = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("poblacion")) {
						poblacion = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("codigopostal")) {
						codigoPostal = Integer.parseInt(objetoArchivo.getString("UTF-8"));
					}
					if (objetoArchivo.getFieldName().equals("direccion")) {
						direccion = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("coordenada")) {
						coordenada = objetoArchivo.getString("UTF-8");
					}

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

		// Generamos un codigo para el inmueble y creamos un objeto
		codigo = new CadenaAleatoria();

		codigoInmueble = codigo.generar();

		ivo = new InmuebleVO(codigoInmueble, descripcion, precio, metroCuadrado, numHabitacion, provincia, poblacion,
				codigoPostal, direccion, coordenada);

		tdao = new TipoDAO();

		tvo = tdao.consultaGenerica("tipo.nombre= '" + tipo + "' AND subtipo.nombre= '" + subTipo + "'");

		idao = new InmuebleDAO();

		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos que el mueble se cree correctamente, de ser asi, almacenamos las
		// imagenes en la galeria
		if (idao.crearInmueble(ivo, (int) avo.getId(), (int) tvo.getId())) {
			imdao = new ImagenDAO();

			for (int i = 0; i < imagenes.size(); i++) {
				ca = new ConversionArchivo();

				imvo = new ImagenVO(FilenameUtils.getBaseName(imagenes.get(i).getName()),
						ca.convertirArchivoABytes(imagenes.get(i)));

				imdao.crearImagen(imvo, codigoInmueble);

				imagenes.get(i).delete();
			}

			response.sendRedirect("GestionInmueble");
		} else {
			sesion.setAttribute("errorAnunciarInmueble", "Error al anunciar el Inmueble");
			response.sendRedirect("AnunciarInmueble");
		}

	}

}
