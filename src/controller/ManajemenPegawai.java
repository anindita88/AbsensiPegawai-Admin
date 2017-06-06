/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelPegawai;
import view.Admin;

/**
 *
 * @author Anindita
 */
public class ManajemenPegawai {
    PreparedStatement pst;
    ResultSet rs;
    String sql;
    
    public void loadDataPegawai(DefaultTableModel namatabel, String modf) throws SQLException{
          
        namatabel.getDataVector().removeAllElements();
        namatabel.fireTableDataChanged();
        
        if(modf == null){
           sql = "SELECT * FROM tblpegawai as p, tbljabatan as j "
                    + "where p.jabatan = j.idjabatan";
           }else{
            sql = "SELECT * FROM tblpegawai as p, tbljabatan as j "
                    + "where p.jabatan = j.idjabatan or "
                    + "p.nama is like '%?%'";
            
            pst.setString(1, modf.toString());
            
        }
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        
        rs = pst.executeQuery(sql);
            
            while(rs.next()){
                //lakukan penelusuran baris
                Object[] o = new Object[5];
                o[0] = rs.getString("idpegawai");
                o[1] = rs.getString("nama");
                o[2] = rs.getString("namajabatan");
                o[3] = rs.getString("foto");
                o[4] = rs.getString("id_jam");
 
                namatabel.addRow(o);
            }
            rs.close();
            pst.close();
        

    }
    public boolean cekPegawai(ModelPegawai modp) throws SQLException {
        sql = "SELECT * from tblpegawai where idpegawai = ?";
                pst = KoneksiDB.getKoneksi().prepareStatement(sql);
                pst.setString(1, modp.getIdpegawai());
                rs = pst.executeQuery();

                if(rs.next()){
                   return true;
                }
                else {
            return false;
        }
}
    
    public void updatePegawai(ModelPegawai modp) throws SQLException {
        
        sql = "UPDATE tblpegawai SET nama = ?, jabatan = ?, foto=? , id_jam=? "
                            + "WHERE idpegawai=?";
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        pst.setString(1, modp.getNama());
        pst.setString(2, modp.getJabatan());
        pst.setString(3, modp.getFoto());
        pst.setString(4, modp.getIdpegawai());
        pst.setString(5, modp.getShift());

        pst.executeUpdate();
        pst.close();
    
    }
    public void tambahPegawai(ModelPegawai modp) throws SQLException {
        sql = "INSERT INTO tblpegawai values(?, ?, ?, ?, ?)";
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        
        pst.setString(1, modp.getIdpegawai());
        pst.setString(2, modp.getNama());
        pst.setString(3, modp.getJabatan());
        pst.setString(4, modp.getFoto());
        pst.setString(5, modp.getShift());

        pst.executeUpdate();
        pst.close();
        
    }
    
    public void hapusPegawai(ModelPegawai modp) throws SQLException {
    
        sql = "DELETE FROM tblpegawai WHERE idpegawai=? ";
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        pst.setString(1, modp.getIdpegawai());
        
        pst.executeUpdate();
        pst.close();
    
    }
    
}
