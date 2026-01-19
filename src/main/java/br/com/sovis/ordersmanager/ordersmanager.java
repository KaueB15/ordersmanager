package br.com.sovis.ordersmanager;
import totalcross.ui.MainWindow;
import totalcross.ui.Label;
import totalcross.sys.Settings;
public class ordersmanager extends MainWindow {
    
    public ordersmanager() {
        setUIStyle(Settings.MATERIAL_UI);
    }

    @Override
    public void initUI() {
        Label helloWord = new Label("Hello World!");
        add(helloWord, CENTER, CENTER);
    }
}
