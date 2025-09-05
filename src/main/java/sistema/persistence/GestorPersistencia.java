package sistema.persistence;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestor de persistencia genérico para el sistema médico usando JAXB
 */
public class GestorPersistencia {

    private final String directorioBase;
    private final Map<Class<?>, JAXBContext> contextos;

    public GestorPersistencia(String directorioBase) {
        this.directorioBase = directorioBase;
        this.contextos = new HashMap<>();

        // Crear directorio si no existe
        File dir = new File(directorioBase);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("Directorio de datos creado: " + directorioBase);
            }
        }
    }


    private JAXBContext obtenerContexto(Class<?> claseEntidad) throws JAXBException {
        if (!contextos.containsKey(claseEntidad)) {
            JAXBContext context = JAXBContext.newInstance(claseEntidad);
            contextos.put(claseEntidad, context);
        }
        return contextos.get(claseEntidad);
    }

    /**
     * Guarda una entidad de forma genérica
     */
    public <T> void guardar(T entidad, String nombreArchivo) {
        try {
            Class<?> claseEntidad = entidad.getClass();
            JAXBContext context = obtenerContexto(claseEntidad);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            String rutaCompleta = directorioBase + File.separator + nombreArchivo;
            FileOutputStream fos = new FileOutputStream(rutaCompleta);
            marshaller.marshal(entidad, fos);
            fos.close();

            System.out.println("Guardado: " + claseEntidad.getSimpleName() + " en " + rutaCompleta);

        } catch (JAXBException | IOException e) {
            System.err.println("Error al guardar " + entidad.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga una entidad de forma genérica
     */
    public <T> T cargar(Class<T> claseEntidad, String nombreArchivo) {
        try {
            String rutaCompleta = directorioBase + File.separator + nombreArchivo;
            File archivo = new File(rutaCompleta);

            if (!archivo.exists()) {
                System.out.println("Archivo no existe: " + rutaCompleta);
                return null;
            }

            JAXBContext context = obtenerContexto(claseEntidad);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            FileInputStream fis = new FileInputStream(archivo);
            @SuppressWarnings("unchecked")
            T entidad = (T) unmarshaller.unmarshal(fis);
            fis.close();

            System.out.println("Cargado: " + claseEntidad.getSimpleName() + " desde " + rutaCompleta);
            return entidad;

        } catch (JAXBException | IOException e) {
            System.err.println("Error al cargar " + claseEntidad.getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica si existe un archivo
     */
    public boolean existeArchivo(String nombreArchivo) {
        String rutaCompleta = directorioBase + File.separator + nombreArchivo;
        return new File(rutaCompleta).exists();
    }

    /**
     * Elimina un archivo
     */
    public boolean eliminarArchivo(String nombreArchivo) {
        String rutaCompleta = directorioBase + File.separator + nombreArchivo;
        File archivo = new File(rutaCompleta);
        if (archivo.exists()) {
            boolean eliminado = archivo.delete();
            if (eliminado) {
                System.out.println("Archivo eliminado: " + rutaCompleta);
            }
            return eliminado;
        }
        return false;
    }

    /**
     * Lista todos los archivos XML en el directorio
     */
    public String[] listarArchivos() {
        File dir = new File(directorioBase);
        String[] archivos = dir.list((dir1, name) -> name.toLowerCase().endsWith(".xml"));

        if (archivos != null && archivos.length > 0) {
            System.out.println("Archivos encontrados en " + directorioBase + ":");
            for (String archivo : archivos) {
                System.out.println("- " + archivo);
            }
        } else {
            System.out.println("No se encontraron archivos XML en " + directorioBase);
        }

        return archivos;
    }

    /**
     * Obtiene el tamaño de un archivo
     */
    public long obtenerTamanoArchivo(String nombreArchivo) {
        String rutaCompleta = directorioBase + File.separator + nombreArchivo;
        File archivo = new File(rutaCompleta);
        return archivo.exists() ? archivo.length() : -1;
    }

    /**
     * Crea una copia de seguridad de un archivo
     */
    public boolean crearBackup(String nombreArchivo) {
        try {
            String rutaOriginal = directorioBase + File.separator + nombreArchivo;
            String rutaBackup = directorioBase + File.separator + "backup_" +
                    System.currentTimeMillis() + "_" + nombreArchivo;

            File original = new File(rutaOriginal);
            File backup = new File(rutaBackup);

            if (!original.exists()) {
                System.out.println("Archivo original no existe: " + rutaOriginal);
                return false;
            }

            // Copiar archivo
            FileInputStream fis = new FileInputStream(original);
            FileOutputStream fos = new FileOutputStream(backup);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            fis.close();
            fos.close();

            System.out.println("Backup creado: " + rutaBackup);
            return true;

        } catch (IOException e) {
            System.err.println("Error al crear backup: " + e.getMessage());
            return false;
        }
    }

    /**
     * Limpia el directorio de datos (usar con precaución)
     */
    public void limpiarDirectorio() {
        File dir = new File(directorioBase);
        File[] archivos = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".xml"));

        if (archivos != null) {
            int eliminados = 0;
            for (File archivo : archivos) {
                if (archivo.delete()) {
                    eliminados++;
                }
            }
            System.out.println("Archivos eliminados: " + eliminados + " de " + archivos.length);
        }
    }

    /**
     * Valida la integridad de un archivo XML
     */
    public boolean validarArchivo(Class<?> claseEntidad, String nombreArchivo) {
        try {
            Object entidad = cargar(claseEntidad, nombreArchivo);
            return entidad != null;
        } catch (Exception e) {
            System.err.println("Archivo corrupto: " + nombreArchivo + " - " + e.getMessage());
            return false;
        }
    }
}