/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Kuisma Kuusniemi
 */
public class NewProjectPanel extends JPanel 
{
    private JTextField nimi;
    private JCheckBox active;
    
    public NewProjectPanel() 
    {
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        nimi = new JTextField(30);
        nimi.setBorder(BorderFactory.createTitledBorder("Uuden projektin nimi"));
        // kai se käyttäjän kannalta on loogista, että se uus projekti aukee aina
        // aktiiviseks eli näkyväks? - julius
        active = new JCheckBox("Asetetaanko uusi projekti aktiiviseksi projektiksi?");
        this.add(nimi);
        this.add(active);
        this.setPreferredSize(new Dimension(360, 100));
    }
    
    public String getProjectName()
    {
        if (nimi.getText() != null)
        {
            return nimi.getText();
        }
        else
        {
            return "no name";
        }
    }
    
    public boolean getActivity()
    {
        if (active.isSelected()) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
