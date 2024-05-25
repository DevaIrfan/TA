import javax.swing.text.*;

public class NumbersOnly extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null) {
            for (int i = 0; i < string.length(); i++) {
                if (!Character.isDigit(string.charAt(i)) || string.charAt(i) == '0') {
                    return;
                }
            }
        }
        super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null) {
            for (int i = 0; i < text.length(); i++) {
                if (!Character.isDigit(text.charAt(i)) || text.charAt(i) == '0') {
                    return;
                }
            }
        }
        super.replace(fb, offset, length, text, attrs);
    }
}

