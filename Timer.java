import javax.swing.*;
import java.awt.*;

public class Timer extends JLabel {
    private boolean go;
    private Runnable task;

    public Timer() {
        super("0", SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                while (go) {
                    setText(String.valueOf(Integer.valueOf(getText()) + 1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }

                }
            }
        };
        go = false;
    }
    public void start() {
        go = true;
        new Thread(task).start();
    }
    public void stop() {
        go = false;
    }

    public void reset() {
        go = false;
        setText("0");
    }
}
