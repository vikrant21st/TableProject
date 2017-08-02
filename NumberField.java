import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumberField extends JTextField {

    /**
     * NumberField accept only numerical inputs. You can use it for both integer and floating point numbers.
     * Also you can set whether the number should be signed or not.
     * @param columns Number of columns of NumberField
     * @param haveFloatingPoint If set, accepts floating point numbers, otherwise integers
     * @param isSigned If set, accepts negative numbers, otherwise positive only
     */
    public NumberField(int columns, boolean haveFloatingPoint, boolean isSigned){
        super(10);

        if(haveFloatingPoint && isSigned)
            addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char ch = e.getKeyChar();

                    if (!isNumber(ch) && !isValidSignal(ch) && !validatePoint(ch)  && ch != '\b') {
                        e.consume();
                    }
                }
            });
        else if(haveFloatingPoint)
            addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char ch = e.getKeyChar();

                    if (!isNumber(ch) && !validatePoint(ch)  && ch != '\b') {
                        e.consume();
                    }
                }
            });
        else if(isSigned)
            addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char ch = e.getKeyChar();

                    if (!isNumber(ch) && !isValidSignal(ch)  && ch != '\b') {
                        e.consume();
                    }
                }
            });
        else
            addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char ch = e.getKeyChar();

                    if (!isNumber(ch) && ch != '\b') {
                        e.consume();
                    }
                }
            });
    }

    private boolean isNumber(char ch){
        return ch >= '0' && ch <= '9';
    }

    private boolean isValidSignal(char ch){
        if( (getText() == null || "".equals(getText().trim()) ) && ch == '-'){
            return true;
        }

        return false;
    }

    private boolean validatePoint(char ch){
        if(ch != '.'){
            return false;
        }

        if(getText() == null || "".equals(getText().trim())){
            setText("0.");
            return false;
        }else if("-".equals(getText())){
            setText("-0.");
        }

        return true;
    }
    @Override
    public String getText() {
        return super.getText();
    }
}
