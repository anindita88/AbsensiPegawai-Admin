/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ModelPresensi;

/**
 *
 * @author Anindita
 */
public class ManajemenPresensi {
    
    public boolean cek_pegawai(ModelPresensi karyawan) throws SQLException {
        
        String cek = "SELECT * from tblpegawai where idpegawai = ?";
        PreparedStatement pst = KoneksiDB.getKoneksi().prepareStatement(cek);
        pst.setString(1, karyawan.getNIP());
        ResultSet rs;
        rs = pst.executeQuery();
        
        if(rs.next()){
            return true;
        }
        return false;
        
    }
    
        public void masuk(String nip) throws SQLException {
        
            
        String hadir = "INSERT INTO tblkehadiran VALUES( ?, CURDATE(), CURTIME(), 0)";
        PreparedStatement pst = KoneksiDB.getKoneksi().prepareStatement(hadir);
        pst.setString(1, nip);
        pst.executeUpdate();     
        
    }
        
        public void keluar(String nip) throws SQLException {
        
        String cek = "UPDATE tblkehadiran SET jkeluar=CURTIME() WHERE idpegawai=? AND tanggal=CURDATE()";
        PreparedStatement pst = KoneksiDB.getKoneksi().prepareStatement(cek);
        pst.setString(1, nip);       
        pst.executeUpdate();       
        
    }
    
}
