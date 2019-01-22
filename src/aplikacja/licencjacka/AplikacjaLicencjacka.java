/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikacja.licencjacka;

import java.awt.Dimension;
import java.awt.Toolkit;
import widok.mainFrame;

/**
 *
 * @author karol
 */
public class AplikacjaLicencjacka {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        mainFrame mf = new  mainFrame();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mf.setLocation(dim.width/2-mf.getSize().width/2, dim.height/2-mf.getSize().height/2);
        mf.setVisible(true);
        
       
    }
    
}
