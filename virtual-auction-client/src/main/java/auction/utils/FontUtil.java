package auction.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JComponent;

public class FontUtil {

    public Font loadFont(String fontPath, float size) {
        try (InputStream fontStream = FontUtil.class.getClassLoader().getResourceAsStream(fontPath)) {
            if (fontStream == null) {
                throw new IOException("Arquivo de fonte não encontrado: " + fontPath);
            }

            // Carrega a fonte a partir do InputStream
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            font = font.deriveFont(size);

            // Registra a fonte no sistema gráfico
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            return font;
        } catch (IOException | FontFormatException e) {
            System.err.println("Erro ao carregar a fonte: " + e.getMessage());
            return null;
        }
    }

    public void applyFont(JComponent component, Font font) {
        if (font != null) {
            component.setFont(font);
        } else {
            System.err.println("A fonte fornecida é nula. O componente não foi modificado.");
        }
    }
}