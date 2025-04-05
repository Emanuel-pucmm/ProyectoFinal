package visual;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class FondoPanel extends JPanel {
    private Image imagenFondo;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1) Intentar obtener la URL del recurso
        URL recurso = getClass().getResource("/resources/SerieNacional.jpg");

        if (recurso == null) {
            // 2) Si es null, significa que NO se encontró la imagen
            // Puedes mostrar un mensaje en consola o un JOptionPane,
            // o incluso dibujar un texto en el panel.
            System.err.println("Error: No se encontró la imagen en /resources/SerieNacional.jpg");
            // Aquí podrías opcionalmente dibujar un mensaje en el panel:
            g.drawString("Imagen no disponible - Verifique la ruta", 10, 20);
            return;
        }

        // 3) Si sí se encontró, cargar la imagen si aún no está cargada
        if (imagenFondo == null) {
            imagenFondo = new ImageIcon(recurso).getImage();
        }

        // 4) Dibujar la imagen escalada a todo el panel
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);

        // Si no queremos opacar (para que los controles encima se vean), hacemos:
        setOpaque(false);
    }
}
