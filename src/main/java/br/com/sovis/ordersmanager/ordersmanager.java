package br.com.sovis.ordersmanager;
import totalcross.ui.MainWindow;
import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.view.forms.LoginView;
import totalcross.sys.Settings;
public class ordersmanager extends MainWindow {
    
    public ordersmanager() {
        super();
        setUIStyle(Settings.MATERIAL_UI);
    }

    @Override
    public void initUI() {
        setRect(0, 0, 320, 480);
        try {
            Database.open();
            Database.createTables();
            Database.insertAdminUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginView loginView = new LoginView();
        add(loginView, LEFT, TOP, FILL, FILL);
    }
}
