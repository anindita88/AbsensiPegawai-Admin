/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ModelLogin;

/**
 *
 * @author Anindita
 */
public class ManajemenUserAdmin {
       /**
     *
     * @param usr
     * @retursn
     * @throws SQLException
     */
    public boolean cekLogin(ModelLogin usr) throws SQLException {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        PreparedStatement pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        pst.setString(1, usr.getUsername());
        pst.setString(2, usr.getPassword());
        ResultSet rs;
        rs = pst.executeQuery();
        if (rs.next()) { 
            return true;
        }
        return false;
    }
}
