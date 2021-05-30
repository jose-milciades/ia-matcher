package com.edi.ia.ner.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.edi.ia.ner.modelo.ModeloNerVO;
import com.edi.ia.ner.modelo.ModelosNerVO;
import com.edi.ia.ner.modelo.ParametrosEntidadVO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;

public class Archivo {

	private final static Logger LOGGER = Logger.getLogger("com.solera.audamedic.estadocuentasap.util.Archivo ");

	public ModelosNerVO leerParametrosEntidades(String ruta, String modelo) throws IOException, JsonSyntaxException {
		Gson gson = new Gson();
		// ParametrosEntidadesVO parametrosEntidadesVO;
		ModelosNerVO modelosNerVO;
		String archivoConfiguracion = "";
		String nombreArchivo = this.leerIndiceModelos(ruta + VariablesGlobales.NOMBRE_ARCHIVO_INDICE_MODELOS)
				.get(modelo);
		try (Stream<String> stream = Files.lines(Paths.get(ruta + nombreArchivo))) {
			Iterator<String> lineas = stream.iterator();
			while (lineas.hasNext()) {
				archivoConfiguracion += lineas.next().toString().trim();
			}
			modelosNerVO = gson.fromJson(archivoConfiguracion, ModelosNerVO.class);
		}

		for (ModeloNerVO modeloNerVO : modelosNerVO.getModelos()) {
			Map<String, ParametrosEntidadVO> map = new TreeMap<String, ParametrosEntidadVO>();
			for (ParametrosEntidadVO parametrosEntidadVO : modeloNerVO.getParametrosEntidades()) {
				for (String entidad : parametrosEntidadVO.getEntidades()) {
					map.put(entidad, parametrosEntidadVO);
				}
			}
			modeloNerVO.setMap(map);
			// System.out.println(map);
		}

		return modelosNerVO;
	}

	public Map<String, String> leerIndiceModelos(String ruta) throws IOException, JsonSyntaxException {

		JsonReader getLocalJsonFile = new JsonReader(new FileReader(ruta));
		Type mapTokenType = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> jsonMap = new Gson().fromJson(getLocalJsonFile, mapTokenType);
		System.out.println(jsonMap);
		return jsonMap;
	}

	/**
	 * public Map<String, ArrayList<EpisodioVO>> leerEpisodios(String ruta) throws
	 * IOException {
	 * 
	 * Map<String, ArrayList<EpisodioVO>> map = new TreeMap<String,
	 * ArrayList<EpisodioVO>>();
	 * 
	 * try (Stream<String> stream = Files.lines(Paths.get(ruta))) { Iterator<String>
	 * lineas = stream.iterator(); while (lineas.hasNext()) { ArrayList<EpisodioVO>
	 * list = new ArrayList<EpisodioVO>(); String linea = lineas.next().toString();
	 * try { EpisodioVO episodio = EpisodioVO.buildFromArray(linea.split(","));
	 * 
	 * list.add(episodio); list = map.putIfAbsent(episodio.getProveedor(), list);
	 * 
	 * if (list != null) { list.add(episodio); } } catch
	 * (ArrayIndexOutOfBoundsException e) {
	 * 
	 * //Utilidad.listaEpisodioError.add(linea + ",Error al leer episodio"); } }
	 * 
	 * } return map; }
	 **/
	public void crearDirectorio(String ruta, String nombreCarpeta) {
		File directorio = new File(ruta + nombreCarpeta);
		if (!directorio.exists()) {
			if (directorio.mkdirs()) {

			} else {

				LOGGER.log(Level.SEVERE, "Error al crear directorio " + ruta + nombreCarpeta);
			}
		} else {
			LOGGER.log(Level.INFO, "El directorio " + ruta + nombreCarpeta + "  ya existe");
		}
	}

	public boolean copiarUltimoArchivo(String nombreArchivo, String tipoArchivoEsperado, String rutaOrigen,
			String rutaDestino) throws IOException {

		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES };
		String nombreInicialArchivo = "";

		boolean archivoCopiadoExitosamente = false;

		try (Stream<Path> stream = Files.list(Paths.get(rutaOrigen))) {

			Iterator<Path> archivos = stream.iterator();

			String extencion;
			if (archivos.hasNext()) {
				Path path = archivos.next();
				nombreInicialArchivo = path.getFileName().toString();

				if (nombreInicialArchivo.split("\\.").length > 0) {

					extencion = nombreInicialArchivo.split("\\.")[nombreInicialArchivo.split("\\.").length - 1];

					if (tipoArchivoEsperado.contains(extencion)) {

						Files.copy(path, Paths.get(rutaDestino + "/" + nombreArchivo + "." + extencion), options);
						archivoCopiadoExitosamente = true;
					}
				}
			}

		}
		return archivoCopiadoExitosamente;
	}

	public String codificarBase64(String tipoArchivoEsperado, String rutaOrigen) throws IOException {

		String nombreInicialArchivo = "";
		String encodedFile = null;

		try (Stream<Path> stream = Files.list(Paths.get(rutaOrigen))) {
			Iterator<Path> archivos = stream.iterator();
			String extencion;
			if (archivos.hasNext()) {
				Path path = archivos.next();
				nombreInicialArchivo = path.getFileName().toString();
				if (nombreInicialArchivo.split("\\.").length > 0) {
					System.out.println("nombreInicialArchivo: " + nombreInicialArchivo);
					extencion = nombreInicialArchivo.split("\\.")[nombreInicialArchivo.split("\\.").length - 1];
					if (tipoArchivoEsperado.contains(extencion)) {
						File file = path.toFile();
						byte[] fileArray = new byte[(int) file.length()];
						try (InputStream inputStream = new FileInputStream(file)) {
							inputStream.read(fileArray);
							encodedFile = Base64.getEncoder().encodeToString(fileArray);
						}

					}
				}
			}

		}
		return encodedFile;
	}

	public Map<String, String> leerConfigProveedorSFTP(String ruta) throws IOException {

		Map<String, String> map = new HashMap<String, String>();
		try (Stream<String> stream = Files.lines(Paths.get(ruta))) {
			Iterator<String> lineas = stream.iterator();
			String linea = "";
			while (lineas.hasNext()) {
				linea = lineas.next().toString();
				map.put(linea.split(",")[0], linea.split(",")[1]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			LOGGER.log(Level.SEVERE, Utilidad.stackTraceToString(e));
		}
		return map;

	}

	public void crerarArchivoEpisodioError(List<String> listaEpisodioError, String ruta, String nombreArchivo)
			throws IOException {

		Path path = Paths.get(ruta + "/" + nombreArchivo);
		try (BufferedWriter br = Files.newBufferedWriter(path, Charset.defaultCharset(), StandardOpenOption.CREATE)) {
			listaEpisodioError.forEach((s) -> {

				try {
					br.write(s);
					br.newLine();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, Utilidad.stackTraceToString(e));
					throw new UncheckedIOException(e);
				}

			});
		}

	}

	public void leerPdf() {

		PDDocument document;
		try {
			document = PDDocument.load(new File("/opt/escritura.pdf"));

			if (!document.isEncrypted()) {
				PDFTextStripper stripper = new PDFTextStripper();
				stripper.setStartPage(25);
				stripper.setEndPage(25);
				String text = stripper.getText(document);
				System.out.println("Text:" + text);
			}
			document.close();

		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void limpiarDirectorio(Path path) throws IOException {
		try (Stream<Path> stream = Files.list(path)) {
			Iterator<Path> archivos = stream.iterator();
			while (archivos.hasNext()) {
				Files.delete(archivos.next());
			}
		}

	}

	public void borrarDirectorio(Path path) throws IOException {
		if (!Files.deleteIfExists(path)) {
			LOGGER.log(Level.SEVERE, "Error al borrar el directorio " + path.getFileName());
		}

	}

}
