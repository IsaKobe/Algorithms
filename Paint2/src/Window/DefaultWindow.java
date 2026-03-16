package Window;

import Input.Input;
import Input.InputMode;
import rasterizers.*;
import rasters.RasterBufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.IconUIResource;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DefaultWindow {

    boolean editingOutline;

    public DefaultWindow(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());

        frame.setTitle("Delta : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        RasterBufferedImage raster = new RasterBufferedImage(width, height);


        JPanel panel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                raster.repaint(g);
            }

        };
        panel.setPreferredSize(new Dimension(width, height));

        JMenuBar bar = new JMenuBar();
        bar.setLayout(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup group = new ButtonGroup();
        ArrayList<JToggleButton> buttons = new ArrayList();

        Input input = new Input(panel, raster, bar);
        for (InputMode value : InputMode.values()) {
            JToggleButton radioButtonMenuItem = createToggleButton(value, input);

            group.add(radioButtonMenuItem);
            bar.add(radioButtonMenuItem);
            buttons.add(radioButtonMenuItem);
        }
        buttons.getFirst().setSelected(true);
        frame.add(bar, BorderLayout.NORTH);

        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        panel.requestFocus();
        panel.requestFocusInWindow();

        input.SetButtons(buttons);


        JColorChooser colorChooser = new JColorChooser(Color.red);
        colorChooser.setPreviewPanel(new JPanel());
        colorChooser.getSelectionModel().addChangeListener(e -> {
            Color newColor = colorChooser.getColor();

            if(editingOutline)
                System.out.println("Outline Color: " + newColor);
            else{
                System.out.println("Fill Color: " + newColor);
            }
            input.SetColor(newColor, editingOutline);
        });

        JDialog d = new JDialog(frame, "Color Dialog", true);
        d.setLayout(new FlowLayout());
        d.add(colorChooser);
        d.setSize(600,300);
        d.setResizable(false);
        d.setVisible(false);

        JLayeredPane colorPanel = new JLayeredPane(){
            @Override
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };
        colorPanel.setPreferredSize(new Dimension(50, 50));
        colorPanel.add(createColorButton(colorChooser, d, false));
        colorPanel.add(createColorButton(colorChooser, d, true));
        bar.add(colorPanel);

        JSlider widthSlider = createSlider(bar, "width", 1, 1);
        widthSlider.addChangeListener(e-> input.setWidth(widthSlider.getValue()));

//        JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem();
//        bar.add(checkBoxMenuItem);

        JSlider spacingSlider = createSlider(bar, "spacing", 0, 0);
        spacingSlider.addChangeListener(e-> input.setSpacing(spacingSlider.getValue()));
    }
    JSlider createSlider(JMenuBar bar, String titleText, int minVal, int baseVal){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBackground(new Color(0,0,0,0));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(titleText);
        panel.add(title);
        JPanel sliderPanel = new JPanel();
        sliderPanel.setOpaque(false);
        sliderPanel.setBackground(new Color(0,0,0,0));

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(25, 30));
        label.setMaximumSize(new Dimension(25, 30));

        JSlider slider = new JSlider();
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(1);
        slider.setMaximum(10);
        slider.setMinimum(minVal);
        slider.addChangeListener(e -> {
            label.setText(Integer.toString(slider.getValue()));
        });
        slider.setValue(baseVal);
        slider.setFocusable(false);

        sliderPanel.add(label);
        sliderPanel.add(slider);

        panel.add(sliderPanel);
        bar.add(panel);
        return slider;
    }

    JButton createColorButton(JColorChooser colorChooser, JDialog d, boolean outline){
        JButton colorButton = new JButton();
        colorButton.setFocusable(false);
        colorButton.setBackground(outline? Color.RED : Color.GREEN);
        colorButton.addActionListener(e -> {
            editingOutline = outline;
            d.setVisible(true);
        });
        if(outline)
            colorButton.setBounds(0,0,50,50);
        else
            colorButton.setBounds( 12, 12, 26, 26);

        colorChooser.getSelectionModel().addChangeListener(e->{
            if(editingOutline == outline)
                colorButton.setBackground(colorChooser.getColor());
        });
        return  colorButton;
    }

    private JToggleButton createToggleButton(InputMode state, Input input) {

        Icon icon;
        try{
            icon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("icons/" + state.getValue() + ".png")));
        }
        catch (IOException e){
            return null;
        }

        // Create button with Label and Key shortcut
        JToggleButton btn = new JToggleButton(icon);
        btn.setPreferredSize(new Dimension(50,50));

        // Logic: When clicked, update system
        btn.addActionListener(e -> input.switchMode(state));

        // Keyboard Shortcut Mapping (Numbers 1-7)
        String mapKey = "press" + state.name();
        btn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(Integer.toString(state.getValue())), mapKey);

        System.out.println(Integer.toString(state.getValue()));
        btn.getActionMap().put(mapKey, new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btn.doClick(); // Visually toggles and triggers listener
            }
        });

        btn.setFocusable(false);
        return btn;
    }
}
