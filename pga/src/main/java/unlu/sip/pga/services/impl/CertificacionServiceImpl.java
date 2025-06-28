package unlu.sip.pga.services.impl;

import unlu.sip.pga.entities.Certificacion;
import unlu.sip.pga.services.CertificacionService;
import unlu.sip.pga.repositories.CertificacionRepository;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class CertificacionServiceImpl implements CertificacionService {
    @Autowired
    private CertificacionRepository certificacionRepository;

    @Override
    public Certificacion crearCertificacion(Certificacion c) {
        return certificacionRepository.save(c);
    }

    @Override
    public Optional<Certificacion> obtenerCertificacionPorId(Integer id) {
        return certificacionRepository.findById(id);
    }

    @Override
    public List<Certificacion> listarCertificacionesPorUsuario(String idUsuario) {
        return certificacionRepository.findByUsuarioId(idUsuario);
    }

    @Override
    public Certificacion actualizarCertificacion(Certificacion c) {
        return certificacionRepository.save(c);
    }

    @Override
    public void eliminarCertificacion(Integer id) {
        certificacionRepository.deleteById(id);
    }

    @Override
    public byte[] generarPdf(Certificacion certificacion) {
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage(PDRectangle.A4);
            documento.addPage(pagina);

            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                PDType1Font fuenteTitulo = PDType1Font.HELVETICA_BOLD;
                PDType1Font fuenteTexto = PDType1Font.HELVETICA;

                String nombreUsuario = certificacion.getUsuario().getNombre();
                String nombreCurso = certificacion.getCurso().getTitulo();
                String fecha = new SimpleDateFormat("dd/MM/yyyy").format(certificacion.getFechaEmision());

                contenido.beginText();
                contenido.setFont(fuenteTitulo, 22);
                contenido.newLineAtOffset(70, 750);
                contenido.showText("Certificado de Finalización");
                contenido.endText();

                contenido.beginText();
                contenido.setFont(fuenteTexto, 14);
                contenido.newLineAtOffset(70, 700);
                contenido.showText("Se certifica que " + nombreUsuario);
                contenido.endText();

                contenido.beginText();
                contenido.setFont(fuenteTexto, 14);
                contenido.newLineAtOffset(70, 680);
                contenido.showText("ha aprobado satisfactoriamente el curso: " + nombreCurso);
                contenido.endText();

                contenido.beginText();
                contenido.setFont(fuenteTexto, 12);
                contenido.newLineAtOffset(70, 650);
                contenido.showText("Fecha de emisión: " + fecha);
                contenido.endText();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            documento.save(baos);
            return baos.toByteArray();

        } catch (IOException e) {
            // Loguear el error o manejarlo según convenga
            throw new RuntimeException("Error generando PDF", e);
        }
    }
}