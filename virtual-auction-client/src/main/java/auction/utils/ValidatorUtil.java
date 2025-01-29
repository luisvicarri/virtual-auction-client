package auction.utils;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ValidatorUtil {

//    Checks if the password meets the following criteria:
//      - Contains at least one lowercase letter.
//      - Contains at least one uppercase letter.
//      - Contains at least one number.
//      - Contains at least one special character from the following: @$!%*?&.
//      - Is 8 or more characters long.
    private final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public boolean areFieldsEmpty(JComponent... components) {
        for (JComponent component : components) {
            if (component instanceof JTextField jTextField) {
                if (jTextField.getText().trim().isEmpty()) {
                    return true; // Campo vazio
                }
            } else if (component instanceof JPasswordField jPasswordField) {
                if (new String(jPasswordField.getPassword()).trim().isEmpty()) {
                    return true; // Campo vazio
                }
            }
        }
        return false; // Nenhum campo vazio
    }

}
