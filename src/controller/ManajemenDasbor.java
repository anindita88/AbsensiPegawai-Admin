/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anindita
 */
public class ManajemenDasbor {
    
    PreparedStatement pst;
    ResultSet rs;
    String sql;
    
    public void loadDataDasbor(DefaultTableModel namatabel) throws SQLException{

        namatabel.getDataVector().removeAllElements();
        namatabel.fireTableDataChanged();
            
        sql = "SELECT tblkehadiran.tanggal, tblpegawai.nama, tbljabatan.namajabatan, tblkehadiran.jmasuk, tblkehadiran.jkeluar "
                    + "FROM tblpegawai "
                    + "INNER JOIN tbljabatan ON tblpegawai.jabatan=tbljabatan.idjabatan "
                    + "INNER JOIN tblkehadiran ON tblpegawai.idpegawai=tblkehadiran.idpegawai "
                    + "GROUP BY tblpegawai.idpegawai "
                    + "ORDER BY tblkehadiran.tanggal desc;";
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        
        rs = pst.executeQuery(sql);
            
            while(rs.next()){
                //lakukan penelusuran baris
                Object[] o = new Object[5];
                o[0] = rs.getDate("tanggal");
                o[1] = rs.getString("nama");
                o[2] = rs.getString("namajabatan");
                o[3] = rs.getTime("jmasuk");
                o[4] = rs.getTime("jkeluar");
 
                namatabel.addRow(o);
            }
            rs.close();
            pst.close();

     } 
    
    public int getJumlahPegawai() throws SQLException{
        sql = "SELECT Count(*) as jumlah_pegawai "
                + "from tblpegawai;";
        
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        rs = pst.executeQuery(sql);
        
        int jumlah;
            while(rs.next()){
                jumlah = rs.getInt("jumlah_pegawai");
                return jumlah;
            }
            return 0;
    }
    
    public int getTotalHadir() throws SQLException{
        sql = "SELECT count(*) as total_hadir "
                + "from tblkehadiran;";
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        rs = pst.executeQuery(sql);
        
        int jumlah_hadir;
            while(rs.next()){
                jumlah_hadir = rs.getInt("total_hadir");
                return jumlah_hadir;
            }
            return 0;
    }
    public int getTotalHari() throws SQLException{
        sql = "SELECT tanggal as tanggal_kerja "
                + "from tblkehadiran "
                + "group by tanggal;";
        pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        rs = pst.executeQuery(sql);
        
        int jumlah_hari = 0;
            while(rs.next()){
                jumlah_hari +=1;                   
            }
            return jumlah_hari; 
    }
}
    
