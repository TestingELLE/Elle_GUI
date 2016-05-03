
package com.elle.elle_gui.admissions;

import com.elle.elle_gui.presentation.*;

/**
 * Developer
 * The developer access level configuration
 * @author Carlos Igreja
 * @since  Mar 1, 2016
 */
public class Developer extends Administrator{

    @Override
    public void setComponent(ELLE_GUI_Frame window) {
        super.setComponent(window);
    }

    @Override
    public void setComponent(EditDatabaseWindow window) {
        super.setComponent(window);
    }

    @Override
    public void setComponent(LogWindow window) {
        super.setComponent(window);
    }

    @Override
    public void setComponent(LoginWindow window) {
        super.setComponent(window);
    }

    @Override
    public void setComponent(ViewATradeWindow window) {
        super.setComponent(window);
    }

}
