package br.com.sovis.ordersmanager;
import totalcross.ui.MainWindow;
import br.com.sovis.ordersmanager.view.LoginView;
import totalcross.sys.Settings;
public class ordersmanager extends MainWindow {
    
    public ordersmanager() {
        setUIStyle(Settings.MATERIAL_UI);
    }

    @Override
    public void initUI() {
        LoginView loginView = new LoginView();
        add(loginView, LEFT, TOP, PARENTSIZE, PARENTSIZE);
    }
}
