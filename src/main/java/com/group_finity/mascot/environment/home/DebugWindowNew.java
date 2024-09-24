package com.group_finity.mascot.environment.home;

import com.group_finity.mascot.Main;

import javax.swing.*;

public class DebugWindowNew extends JFrame{
    private JLabel lblBehaviour;
    private JLabel lblBehaviourValue;
    private JLabel lblEnvironmentHeightValue;
    private JLabel lblEnvironmentWidthValue;
    private JLabel lblWindowYValue;
    private JLabel lblWindowXValue;
    private JLabel lblActiveIEValue;
    private JLabel lblShimejiYValue;
    private JLabel lblShimejiXValue;
    private JLabel lblFocusedValue;
    private JLabel lblShimejiX;
    private JLabel lblShimejiY;
    private JLabel lblActiveIE;
    private JLabel lblFocused;
    private JLabel lblWindowX;
    private JLabel lblWindowY;
    private JLabel lblEnvironmentWidth;
    private JLabel lblEnvironmentHeight;
    private JPanel contentPane;

    public void setBehaviour(String text) {
        lblBehaviourValue.setText(text);
    }

    public void setShimejiX(int x) {
        lblShimejiXValue.setText(String.format("%d", x));
    }

    public void setShimejiY(int y) {
        lblShimejiYValue.setText(String.format("%d", y));
    }

    public void setWindowTitle(String title) {
        lblActiveIEValue.setText(title);
    }

    public void setFocusedValue(String title) {
        lblFocusedValue.setText(title);
    }

    public void setWindowX(int x) {
        lblWindowXValue.setText(String.format("%d", x));
    }

    public void setWindowY(int y) {
        lblWindowYValue.setText(String.format("%d", y));
    }

    public void setEnvironmentWidth(int width) {
        lblEnvironmentWidthValue.setText(String.format("%d", width));
    }

    public void setEnvironmentHeight(int height) {
        lblEnvironmentHeightValue.setText(String.format("%d", height));
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            lblBehaviour.setText(Main.getInstance().getLanguageBundle().getString("Behaviour"));
            lblShimejiX.setText(Main.getInstance().getLanguageBundle().getString("ShimejiX"));
            lblShimejiY.setText(Main.getInstance().getLanguageBundle().getString("ShimejiY"));
            lblActiveIE.setText(Main.getInstance().getLanguageBundle().getString("ActiveIE"));
            lblWindowX.setText(Main.getInstance().getLanguageBundle().getString("WindowX"));
            lblWindowY.setText(Main.getInstance().getLanguageBundle().getString("WindowY"));
            lblEnvironmentWidth.setText(Main.getInstance().getLanguageBundle().getString("EnvironmentWidth"));
            lblEnvironmentHeight.setText(Main.getInstance().getLanguageBundle().getString("EnvironmentHeight"));
        }
        super.setVisible(b);
    }
}
