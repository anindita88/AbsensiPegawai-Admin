/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import model.ModelAbsensi;
import model.ModelPegawai;

/**
 *
 * @author Anindita
 */
public class ManajemenAbsensi {
    
    PreparedStatement pst;
    ResultSet rs;
    String sql;
    
    public void loadDataAbsen(DefaultTableModel namatabel) throws SQLException{
        
        namatabel.getDataVector().removeAllElements();
        namatabel.fireTableDataChanged();
    
        sql = "SELECT tblabsen.no, tblabsen.tanggal, tblabsen.idpegawai, tblpegawai.nama, tblpegawai.jabatan,"
                + " tblabsen.izin, tblabsen.keterangan, tblpegawai.foto "
                + "FROM tblabsen INNER JOIN tblpegawai where tblabsen.idpegawai=tblpegawai.idpegawai "
                + "ORDER BY tblabsen.no desc;";
        
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        rs = pst.executeQuery(sql);
            
            while(rs.next()){
                //lakukan penelusuran baris
                Object[] o = new Object[8];
                o[0] = rs.getInt("no");
                o[1] = rs.getDate("tanggal");
                o[2] = rs.getString("idpegawai");
                o[3] = rs.getString("nama");
                o[4] = rs.getString("jabatan");
                o[5] = rs.getString("izin");
                o[6] = rs.getString("keterangan");
                o[7] = rs.getString("foto");
 
                namatabel.addRow(o);
            }
            rs.close();
            pst.close();

     }
    
    
    public boolean cekPegawai(ModelAbsensi moda) throws SQLException {
        sql = "SELECT * from tblpegawai where idpegawai = ?";
                pst = KoneksiDB.getKoneksi().prepareStatement(sql);
                pst.setString(1, moda.getNip());
                rs = pst.executeQuery();

                if(rs.next()){
                   return true;
                }
                else {
            return false;
        }
}
    
    public boolean cekDuplikasi(ModelAbsensi moda) throws SQLException {
        sql = "SELECT * from tblabsen where idpegawai = ? and tanggal = ?";
                pst = KoneksiDB.getKoneksi().prepareStatement(sql);
                pst.setString(1, moda.getNip());
                pst.setString(2, moda.getTanggal());
                rs = pst.executeQuery();

                if(rs.next()){
                   return true;
                }
                else {
            return false;
        }
}
    
    public void ubahAbsen(ModelAbsensi moda) throws SQLException {
        
        sql = "UPDATE tblabsen SET idpegawai = ?, tanggal = ?, izin=?, keterangan=?,  "
                            + "WHERE no=?";
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        pst.setString(1, moda.getNip());
        pst.setDate(2, java.sql.Date.valueOf(moda.getTanggal()));
        pst.setString(3, moda.getAbsen());
        pst.setString(4, moda.getKeterangan());
        pst.setInt(5, moda.getNo());
        

        pst.executeUpdate();
        pst.close();
    
    }
    public void tambahAbsen(ModelAbsensi moda) throws SQLException {
        sql = "INSERT INTO tblabsen (idpegawai, izin, tanggal, keterangan) VALUES( ?, ?, ?, ?)";
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        pst.setString(1, moda.getNip());
        pst.setString(2, moda.getAbsen());
        pst.setDate(3, java.sql.Date.valueOf(moda.getTanggal()));
        pst.setString(4, moda.getKeterangan());

        pst.executeUpdate();
        pst.close();
        
        
    }
    
    public void hapusPegawai(ModelAbsensi moda) throws SQLException {
    
        sql = "DELETE FROM tblabsen where no=?";
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        pst.setInt(1, moda.getNo());

        pst.executeUpdate();
        pst.close();
    }
    
}
