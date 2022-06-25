/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Connect.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.sql.*;
import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;
import java.lang.Object;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author ASUS
 */
public class mainView extends javax.swing.JFrame {
    private  DefaultTableModel model1,model2,model3,model4;
    String u2;
public String kodeObat;
Connection con;
    /**
     * Creates new form NewJFrame
     */
    public mainView() {
        initComponents();
         setLocationRelativeTo(null);
       Tampil_Tanggal();
        Tampil_Jam();
        java.util.Date date = new java.util.Date();
        SimpleDateFormat s =new SimpleDateFormat("yyyy-MM-dd");
       
        TanggalKas.setText(s.format(date));
        txTanggal.setText(s.format(date));
        txTotalBayar.setText("0");
        txBayar.setText("0");
        txKembalian.setText("0");
        txID_Barang.requestFocus();
        tanggalBeli.setText(s.format(date));
    }
    public void Login(){
          String user = txtUser.getText();
        String pass = String.valueOf(txtPassword.getPassword());
        try {
            Connection c = (Connection) connect.getConnection();
            java.sql.Statement state = c.createStatement();
            @SuppressWarnings("deprecation")
            String query = "SELECT * FROM tb_user WHERE  Username= '" + user + "'";
            ResultSet rs = state.executeQuery(query);
            if (rs.next()) {
                String pass_db = rs.getString("password");
                if (pass_db.equalsIgnoreCase(Encryp.MD5(pass))) {
                    //go to home
                    String admin="Admin";
                    String karyawan="Karyawan";
                    data_user.jabatan = rs.getString("Level");
                    JOptionPane.showMessageDialog(rootPane, "Login Berhasil");
                    u2=rs.getString("id_user");
                    tampilkan_user();
                     tampilkan_lapBrg();
        tampilkan_lapJual();
        tampilkan_lapBeli();
        tampilkan_pembelian();
        tampilkan_Supp();
        tampilkan_KAS();
        loadDataKas();
        tampilkan_brg();
        tampilkan_jenis();
        tampilkan_satuan();
        tampilkan_kyw();
       tampilan_User();
        loadData();
          utama();
        tampilkan_UtangPiutang();
                    if (rs.getString("Level").equalsIgnoreCase(admin)) {
                        jPanel1.removeAll();
                        jPanel1.repaint();
                        jPanel1.revalidate();
        
                        jPanel1.add(Panel);
                        jPanel1.repaint();
                        jPanel1.revalidate();
                        UserKas.setText(rs.getString("id_user"));
                 txtId_user.setText(rs.getString("id_user"));
                 User_beli.setText(rs.getString("id_user"));
                LabelUSer.setText(rs.getString("Username"));
                txtuser.setText(rs.getString("Username"));
                    }
                    else if(rs.getString("Level").equalsIgnoreCase(karyawan)){                        
                        jPanel1.removeAll();
                        jPanel1.repaint();
                        jPanel1.revalidate();
        
                        jPanel1.add(Panel);
                        jPanel1.repaint();
                        jPanel1.revalidate();
                        buttonKyw_home.setEnabled(false);
                        buttonLap_home.setEnabled(false);
                        buttonSupplier_home.setEnabled(false);
                        UserKas.setText(rs.getString("id_user"));
                 txtId_user.setText(rs.getString("id_user"));
                 User_beli.setText(rs.getString("id_user"));
                LabelUSer.setText(rs.getString("Username"));
                txtuser.setText(rs.getString("Username"));
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Password Salah");
                    txtPassword.setText("");
                    txtUser.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Username Salah");
                txtUser.setText("");
                txtPassword.setText("");
                txtUser.requestFocus();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void autonumberBrg(){
        try{
            Connection c = connect.getConnection();
            Statement stat = c.createStatement();
            String sql = "SELECT MAX(right(kd_obat,2))AS kodeobat FROM tb_barang";
            
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                if(rs.first()==false){
                    kd_obat.setText("P001");
                }else{
                    rs.last();
                    int kode_obat = rs.getInt(1)+1;
                    String no = String.valueOf(kode_obat);
                    int no_next = no.length();
                    for (int a=0; a<2-no_next; a++){
                        no = "00"+no;
                    }
                    kd_obat.setText("P0"+no);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
     public void autonumberJenis(){
        try{
            Connection c = connect.getConnection();
            Statement stat = c.createStatement();
            String sql = "SELECT MAX(right(id_jenis,2))AS jenis FROM tb_jenis";
            
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                if(rs.first()==false){
                    id_jenis.setText("01");
                }else{
                    rs.last();
                    int jenis = rs.getInt(1)+1;
                    String no = String.valueOf(jenis);
                    int no_next = no.length();
                    for (int a=0; a<2-no_next; a++){
                        no = "0"+no;
                    }
                    id_jenis.setText(no);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
       public void autonumberSatuan(){
        try{
            Connection c = connect.getConnection();
            Statement stat = c.createStatement();
            String sql = "SELECT MAX(right(id_satuan,2))AS satuan FROM tb_satuan";
            
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                if(rs.first()==false){
                    id_satuan.setText("01");
                }else{
                    rs.last();
                    int satuan = rs.getInt(1)+1;
                    String no = String.valueOf(satuan);
                    int no_next = no.length();
                    for (int a=0; a<2-no_next; a++){
                        no = "0"+no;
                    }
                    id_satuan.setText(no);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
        public void autonumberKyw(){
        try{
            Connection c = connect.getConnection();
            Statement stat = c.createStatement();
            String sql = "SELECT MAX(right(id_karyawan,2))AS satuan FROM tb_karyawan";
            
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                if(rs.first()==false){
                    id_karyawan.setText("K001");
                }else{
                    rs.last();
                    int satuan = rs.getInt(1)+1;
                    String no = String.valueOf(satuan);
                    int no_next = no.length();
                    for (int a=0; a<2-no_next; a++){
                        no = "00"+no;
                    }
                    id_karyawan.setText("K"+no);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
         public void autonumberUser(){
        try{
            Connection c = connect.getConnection();
            Statement stat = c.createStatement();
            String sql = "SELECT MAX(right(id_user,2))AS satuan FROM tb_user";
            
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                if(rs.first()==false){
                    id_user.setText("001");
                }else{
                    rs.last();
                    int satuan = rs.getInt(1)+1;
                    String no = String.valueOf(satuan);
                    int no_next = no.length();
                    for (int a=0; a<2-no_next; a++){
                        no = "00"+no;
                    }
                    id_user.setText(no);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
         public void autonumbertr(){
        try{
            Connection c = connect.getConnection();
            Statement stat = c.createStatement();
            String sql = "SELECT MAX(right(kd_transaksi,3))AS KodeTransaksi FROM tb_transaksi";
            
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                if(rs.first()==false){
                    txNoTransaksi.setText("TR001");
                }else{
                    rs.last();
                    int no_tr = rs.getInt(1)+1;
                    String no = String.valueOf(no_tr);
                    int no_next = no.length();
                    for (int a=0; a<2-no_next; a++){
                        no = "00"+no;
                    }
                    txNoTransaksi.setText("TR0"+no);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
     }
          public void autonumberBeli(){
        try{
            Connection c = connect.getConnection();
            Statement stat = c.createStatement();
            String sql = "SELECT MAX(right(id_pembelian,3))AS KodeTransaksi FROM tb_pembelian";
            
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                if(rs.first()==false){
                    txNoTransaksi.setText("PB001");
                }else{
                    rs.last();
                    int no_tr = rs.getInt(1)+1;
                    String no = String.valueOf(no_tr);
                    int no_next = no.length();
                    for (int a=0; a<2-no_next; a++){
                        no = "00"+no;
                    }
                    kd_pembelian.setText("PB"+no);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
     }
           public void autonumberSupp(){
        try{
            Connection c = connect.getConnection();
            Statement stat = c.createStatement();
            String sql = "SELECT MAX(right(id_supplier,3))AS KodeTransaksi FROM tb_supplier";
            
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                if(rs.first()==false){
                    id_supp.setText("001");
                }else{
                    rs.last();
                    int no_tr = rs.getInt(1)+1;
                    String no = String.valueOf(no_tr);
                    int no_next = no.length();
                    for (int a=0; a<2-no_next; a++){
                        no = "00"+no;
                    }
                    id_supp.setText(no);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
     }
           public void autonumberKas(){
        try{
            Connection c = connect.getConnection();
            Statement stat = c.createStatement();
            String sql = "SELECT MAX(right(Id_bukukas,2))AS idbukuKas FROM tb_bukukas";
            
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                if(rs.first()==false){
                    idKas.setText("BK001");
                }else{
                    rs.last();
                    int kode_obat = rs.getInt(1)+1;
                    String no = String.valueOf(kode_obat);
                    int no_next = no.length();
                    for (int a=0; a<2-no_next; a++){
                        no = "00"+no;
                    }
                    idKas.setText("BK"+no);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
public void Tampil_Jam(){
        ActionListener taskPerformer = new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent evt) {
            String nol_jam = "", nol_menit = "",nol_detik = "";
 
            java.util.Date dateTime = new java.util.Date();
            int nilai_jam = dateTime.getHours();
            int nilai_menit = dateTime.getMinutes();
            int nilai_detik = dateTime.getSeconds();
 
            if(nilai_jam <= 9) nol_jam= "0";
            if(nilai_menit <= 9) nol_menit= "0";
            if(nilai_detik <= 9) nol_detik= "0";
 
            String jam = nol_jam + Integer.toString(nilai_jam);
            String menit = nol_menit + Integer.toString(nilai_menit);
            String detik = nol_detik + Integer.toString(nilai_detik);
 
            Label_jam.setText(jam+":"+menit+":"+detik+"");
            }
        };
    new Timer(1000, taskPerformer).start();
    }   
 
public void Tampil_Tanggal() {
    java.util.Date tglsekarang = new java.util.Date();
    SimpleDateFormat smpdtfmt = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
    String tanggal = smpdtfmt.format(tglsekarang);
    Label_tgl.setText(tanggal);
}
 
public void User() {
    String user2 = txtUser.getText();
        try {
            Connection c = connect.getConnection();
            Statement state =c.createStatement();
            String SQL = "SELECT * FROM tb_user WHERE  Username= '" + user2 + "'";
            ResultSet res = state.executeQuery(SQL);   
            if(res.next()){
                UserKas.setText(res.getString("id_user"));
                 txtId_user.setText(res.getString("id_user"));
                 User_beli.setText(res.getString("id_user"));
                LabelUSer.setText(res.getString("Username"));
                txtuser.setText(res.getString("Username"));
                
            }
        }
       catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
} 

private void tampilkan_brg() {
        
            String [] judul1={"KODE OBAT","NAMA OBAT","JENIS","SATUAN","STOK","HARGA BELI","HARGA JUAL"};
            model1 = new DefaultTableModel(judul1,0);
            tabelBarang.setModel(model1);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_barang ");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)};
                model1.addRow(data);
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
private void cariBrg(String key){
    try{
        String [] judul1={"KODE OBAT","NAMA OBAT","SATUAN","JENIS","HARGA JUAL","HARGA BELI","STOK"};
        model1 = new DefaultTableModel(judul1,0);
        tabelBarang.setModel(model1);
    Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_barang WHERE kd_obat"
                    + " LIKE '%"+key+"%' OR nama_obat LIKE '%"+key+"%' OR satuan LIKE '%"+key+"%' "
                    + "OR jenis LIKE '%"+key+"%'");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)};
                model1.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
private void tampilkan_jenis() {
        
            String [] judul1={"ID JENIS","JENIS"};
            model2 = new DefaultTableModel(judul1,0);
            tableJenis.setModel(model2);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_jenis");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2)};
                model2.addRow(data);
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
private void tampilkan_satuan() {
        
            String [] judul1={"ID SATUAN","SATUAN"};
            model3 = new DefaultTableModel(judul1,0);
            tableSatuan.setModel(model3);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_satuan");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2)};
                model3.addRow(data);
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
private void tampilkan_kyw() {
     
        try {
        model4 = new DefaultTableModel();
        
        String[] judul2 = {"Id Karyawan","Id User","Nama Karyawan","Jenis Kelamin","Status","Email","No Hp","Kewarganegaran","Pendidikan Terakhir","Alamat","Gaji Pokok"};
        model4 = new DefaultTableModel(judul2, 0);
        tabelKyw.setModel(model4);
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_karyawan");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)};
                model4.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 private void  carikyw(String key){
 try{
        String [] judul2 = {"Id Karyawan","Id User","Nama Karyawan","Jenis Kelamin","Email","Status","No Hp","Kewarganegaran","Pendidikan Terakhir","Alamat"};
        model4 = new DefaultTableModel(judul2, 0);
        tabelKyw.setModel(model4);
    Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_karyawan WHERE id_karyawan"
                    + " LIKE '%"+key+"%' OR user LIKE '%"+key+"%' OR nama_karyawan LIKE '%"+key+"%' "
                    + "OR JK LIKE '%"+key+"%'OR status LIKE '%"+key+"%'OR pendidikan LIKE '%"+key+"%'OR alamat LIKE '%"+key+"%'OR gaji_pokok LIKE '%"+key+"%'");
          while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)};
                model4.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
 private void tampilkan_user() {
     
        try {
        model4 = new DefaultTableModel();
        
        String[] judul2 = {"ID USER","USERNAME","PASSWORD","LEVEL"};
        model4 = new DefaultTableModel(judul2, 0);
        tableUser.setModel(model4);
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_user");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
                model4.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void loadDataBrg() {
        String id_satuan;
        String id_jenis;
         ArrayList<String> item_id_jenis = new ArrayList<String>();
        ArrayList<String> item_jenis = new ArrayList<String>();
        ArrayList<String> item_id_satuan = new ArrayList<String>();
        ArrayList<String> item_satuan = new ArrayList<String>();

        try {
            Connection c = connect.getConnection();
            Statement s = c.createStatement();
            cbJenis.removeAllItems();
           String sql = "SELECT * FROM tb_jenis";
            ResultSet r = s.executeQuery(sql);
            
            while (r.next()) {
             item_id_jenis.add(r.getString("id_jenis"));
              item_jenis.add(r.getString("jenis"));
            }
            for (int i = 0; i < item_jenis.size(); i++) {
              
                cbJenis.addItem(item_id_jenis.get(i));
                
                
            }
            cbSatuan.removeAllItems();
            sql = "SELECT * FROM tb_satuan";
            r = s.executeQuery(sql);
            while (r.next()) {
                item_id_satuan.add(r.getString("id_satuan"));
                item_satuan.add(r.getString("satuan"));
            }
            for (int i = 0; i < item_satuan.size(); i++) {
                cbSatuan.addItem(item_id_satuan.get(i));
            }
            r.close();
            s.close();
        } 
    catch (Exception e) {
            System.out.println("terjadi kesalahan");
        }
    }
    public void loadDataKyw() {
        String id_user;
         ArrayList<String> item_id_user = new ArrayList<String>();

        try {
            Connection c = connect.getConnection();
            Statement s = c.createStatement();
            cbUser.removeAllItems();
           String sql = "SELECT * FROM tb_user";
            ResultSet r = s.executeQuery(sql);
            
            while (r.next()) {
             item_id_user.add(r.getString("id_user"));
            
            }
            for (int i = 0; i < item_id_user.size(); i++) {
              
               cbUser.addItem(item_id_user.get(i));
            }}
    catch (Exception e) {
            System.out.println("terjadi kesalahan");
        }
    }
     public void loadData(){
         model1 = new DefaultTableModel();
        
        String[] judul2 = {"No Transaksi","Tanggal","Id User","Kode Obat","Harga","Jumlah","Total Bayar"};
       model1 = new DefaultTableModel(judul2, 0);
        tabelKasir.setModel(model1);
        model1.addRow(new Object[]{
            txNoTransaksi.getText(),
            txTanggal.getText(),
            txtId_user.getText(),
            txID_Barang.getText(),
            txHarga.getText(),
            txJumlah.getText(),
            txTotalBayar.getText(),
        });
    }
      public void clear(){
        txTotalBayar.setText("0");
        txBayar.setText("0");
        txKembalian.setText("0");
        txTampil.setText("0");
    }
    
    public void clear2(){
        txID_Barang.setText("");
        txNamaObat.setText("");
        txHarga.setText("");
        txJumlah.setText("");
    }
     public void totalBiaya(){
        int jumlahBaris = tabelKasir.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        for (int i=0;i < jumlahBaris; i++) {
           jumlahBarang = Integer.parseInt(tabelKasir.getValueAt (i, 5).toString());
           hargaBarang = Integer.parseInt(tabelKasir.getValueAt (i, 4).toString());
           totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
        }
        txTotalBayar.setText(String.valueOf(totalBiaya));
        txTampil.setText("Rp "+ totalBiaya +",00");
    }
     
      public void tambahTransaksi(){
         System.out.println("transaksi");
         int jumlah, harga, total;

         jumlah = Integer.valueOf(txJumlah.getText());
         harga = Integer.valueOf(txHarga.getText());
         total = jumlah * harga;
         txTotalBayar.setText(String.valueOf(total));
         loadData();
         totalBiaya();
         
     }
      public void kosong(){
        DefaultTableModel model =(DefaultTableModel) tabelKasir.getModel();
        
        while (model.getRowCount()>0){
            model.removeRow(0);
        }
    }
    
    public void utama() {
        txNoTransaksi.setText("");
        txID_Barang.setText("");
        txNamaObat.setText("");
        txHarga.setText("");
        txJumlah.setText("");
    }
    public void tampilan_User(){
         try {
            Connection c = connect.getConnection();
            Statement s = c.createStatement();
         String SQL = "SELECT * FROM tb_karyawan WHERE user='" + u2 + "'";
            ResultSet res = s.executeQuery(SQL);   
            if(res.next()){
                txtIdUser.setText(res.getString("user"));
                txtIdKaryawan.setText(res.getString("id_karyawan"));
                txtNama.setText(res.getString("nama_karyawan"));
                txtJK.setText(res.getString("JK"));
                txtEmail.setText(res.getString("email"));
                txtNotlp.setText(res.getString("no_tlp"));
                txtWarga.setText(res.getString("kewarganegaraan"));
                txtStatus.setText(res.getString("status"));
                txtPendidikan.setText(res.getString("pendidikan"));
                txtAlamat.setText(res.getString("alamat"));
            }
        }
       catch (SQLException e) {
            System.out.println(e.getMessage());
       }
    }
    private void tampilkan_lapBrg() {
        
            String [] judul1={"KODE OBAT","NAMA OBAT","JENIS","SATUAN","HARGA BELI","HARGA JUAL","STOK"};
            model1 = new DefaultTableModel(judul1,0);
            LapBrg.setModel(model1);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_barang ");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)};
                model1.addRow(data);
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
private void cariLapBrg(String key){
    try{
        String [] judul1={"KODE OBAT","NAMA OBAT","SATUAN","JENIS","HARGA JUAL","HARGA BELI","STOK"};
        model1 = new DefaultTableModel(judul1,0);
        LapBrg.setModel(model1);
    Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_barang WHERE kd_obat"
                    + " LIKE '%"+key+"%' OR nama_obat LIKE '%"+key+"%' OR satuan LIKE '%"+key+"%' "
                    + "OR jenis LIKE '%"+key+"%' OR stok LIKE '%"+key+"%' OR harga_beli LIKE '%"+key+"%' OR harga_jual LIKE '%"+key+"%'");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)};
                model1.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
private void tampilkan_lapJual() {
        
            String [] judul1={"KODE TRANSAKSI","USER","TANGGAL","KODE OBAT","NAMA OBAT","JENIS","SATUAN","HARGA JUAL","JUMLAH","TOTAL HARGA"};
            model1 = new DefaultTableModel(judul1,0);
            LapPen.setModel(model1);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_transaksi TRA "
                    + "LEFT JOIN tb_barang BRG ON TRA.obat= BRG.kd_obat "
                    + "LEFT JOIN tb_user USR ON USR.id_user=TRA.user ");
            while (rs.next()) {
                model1.addRow(new Object[]{
                    rs.getString("kd_transaksi"),
                    rs.getString("Username"),
                    rs.getString("Tanggal"),
                    rs.getString("kd_obat"),
                    rs.getString("nama_obat"),
                    rs.getString("jenis"),
                    rs.getString("satuan"),
                    rs.getString("harga_jual"),
                    rs.getString("jumlah"),
                    rs.getString("total_harga")
                });
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
  private void cariLapJual(String key){
    try{
         String [] judul1={"KODE TRANSAKSI","USER","TANGGAL","KODE OBAT","NAMA OBAT","JENIS","SATUAN","HARGA JUAL","JUMLAH","TOTAL HARGA"};
            model1 = new DefaultTableModel(judul1,0);
            LapPen.setModel(model1);
    Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_transaksi TRA "
                    + "LEFT JOIN tb_barang BRG ON TRA.obat= BRG.kd_obat "
                    + "LEFT JOIN tb_user USR ON USR.id_user=TRA.user WHERE kd_transaksi"
                    + " LIKE '%"+key+"%' OR Username LIKE '%"+key+"%' OR Tanggal LIKE '%"+key+"%' "
                    + "OR kd_obat LIKE '%"+key+"%' OR nama_obat LIKE '%"+key+"%' OR jenis LIKE '%"+key+"%' OR satuan LIKE '%"+key+"%'"
                    + "OR harga_jual LIKE '%"+key+"%' OR jumlah LIKE '%"+key+"%' OR total_harga LIKE '%"+key+"%'");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)};
                model1.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    private void clear3(){
        txID_Kyw.setText("");
        txNama_kyw.setText("");
        txStatus_Kyw.setText("");
        txGaji_Pokok.setText("");
        txJumlahLembur.setText("");
        txMasaKerja_Kyw.setText("");
        txAsuransi.setText("");
        txBonus.setText("");
        txGajiBersih.setText("");
        txPemotongan.setText("");
        
    }
    private void cariLapBeli(String key){
    try{
        String [] judul1={"KODE PEMBELIAN","TANGGAL","USER","SUPPLIER","KODE OBAT","NAMA OBAT","JENIS","SATUAN","HARGA BELI","JUMLAH","TOTAL HARGA"};
            model1 = new DefaultTableModel(judul1,0);
            LapBeli.setModel(model1);
    Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_pembelian PEM "
                    + "LEFT JOIN tb_barang BRG ON PEM.kd_obat= BRG.kd_obat "
                    + "LEFT JOIN tb_supplier SUP ON SUP.id_supplier=PEM.id_supplier "
                    + "LEFT JOIN tb_user USR ON USR.id_user=PEM.id_user WHERE id_pembelian"
                    + " LIKE '%"+key+"%' OR Username LIKE '%"+key+"%' OR Tanggal LIKE '%"+key+"%' OR nama_pt LIKE '%"+key+"%' "
                    + "OR kd_obat LIKE '%"+key+"%' OR nama_obat LIKE '%"+key+"%' OR jenis LIKE '%"+key+"%' OR satuan LIKE '%"+key+"%'"
                    + "OR harga_beli LIKE '%"+key+"%' OR jumlah LIKE '%"+key+"%' OR total_harga LIKE '%"+key+"%'");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)};
                model1.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    private void tampilkan_lapBeli() {
        
            String [] judul1={"KODE PEMBELIAN","TANGGAL","USER","SUPPLIER","KODE OBAT","NAMA OBAT","JENIS","SATUAN","HARGA BELI","JUMLAH","TOTAL HARGA"};
            model1 = new DefaultTableModel(judul1,0);
            LapBeli.setModel(model1);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_pembelian PEM "
                    + "LEFT JOIN tb_barang BRG ON PEM.kd_obat= BRG.kd_obat "
                    + "LEFT JOIN tb_supplier SUP ON SUP.id_supplier=PEM.id_supplier "
                    + "LEFT JOIN tb_user USR ON USR.id_user=PEM.id_user ");
            while (rs.next()) {
                model1.addRow(new Object[]{
                    rs.getString("id_pembelian"),
                    rs.getString("Tanggal"),
                    rs.getString("Username"),
                    rs.getString("nama_pt"),
                    rs.getString("kd_obat"),
                    rs.getString("nama_obat"),
                    rs.getString("jenis"),
                    rs.getString("satuan"),
                    rs.getString("harga_beli"),
                    rs.getString("jumlah"),
                    rs.getString("total_harga")
                });
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
        private void tampilkan_pembelian() {
        
            String [] judul1={"KODE PEMBELIAN","TANGGAL","USER","SUPPLIER","KODE OBAT","HARGA BELI","JUMLAH","TOTAL HARGA"};
            model1 = new DefaultTableModel(judul1,0);
            LapBeli1.setModel(model1);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_pembelian");
            while (rs.next()) {
                model1.addRow(new Object[]{
                    rs.getString("id_pembelian"),
                    rs.getString("Tanggal"),
                    rs.getString("id_user"),
                    rs.getString("id_supplier"),
                    rs.getString("kd_obat"),
                    rs.getString("harga_beli"),
                    rs.getString("jumlah"),
                    rs.getString("total_harga")
                });
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
         private void cariPembelian(String key){
    try{
         String [] judul1={"KODE PEMBELIAN","TANGGAL","USER","SUPPLIER","KODE OBAT","HARGA BELI","JUMLAH","TOTAL HARGA"};
            model1 = new DefaultTableModel(judul1,0);
            LapBeli1.setModel(model1);
    Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_pembelian WHERE id_pembelian"
                    + " LIKE '%"+key+"%' OR id_user LIKE '%"+key+"%' OR Tanggal LIKE '%"+key+"%' OR id_supplier LIKE '%"+key+"%' "
                    + "OR kd_obat LIKE '%"+key+"%' OR harga_beli LIKE '%"+key+"%' OR jumlah LIKE '%"+key+"%' OR total_harga LIKE '%"+key+"%'");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7),rs.getString(8)};
                model1.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
         public void loadDataBeli() {
        String id_Supplier;
        String id_Barang;
        ArrayList<String> item_Supplier = new ArrayList<String>();
        ArrayList<String> item_Barang = new ArrayList<String>();
      
        try {
            Connection c = connect.getConnection();
            Statement s = c.createStatement();
            cbSuppBeli.removeAllItems();
           String sql = "SELECT * FROM tb_supplier";
            ResultSet r = s.executeQuery(sql);
            
            while (r.next()) {
             item_Supplier.add(r.getString("id_supplier"));
            }
            for (int i = 0; i < item_Supplier.size(); i++) {
              
                cbSuppBeli.addItem(item_Supplier.get(i));
                
                
            }
            cbObatBeli.removeAllItems();
            sql = "SELECT * FROM tb_barang";
            r = s.executeQuery(sql);
            while (r.next()) {
                item_Barang.add(r.getString("kd_obat"));
            }
            for (int i = 0; i < item_Barang.size(); i++) {
                cbObatBeli.addItem(item_Barang.get(i));
            }
            r.close();
            s.close();
        } 
    catch (Exception e) {
            System.out.println("terjadi kesalahan");
        }
    }
          private void tampilkan_Supp() {
        
            String [] judul1={"ID SUPPLIER","NAMA PERUSAHAAN","ALAMAT","EMAIL","NO TELEPON"};
            model1 = new DefaultTableModel(judul1,0);
            tableSupp.setModel(model1);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_supplier");
            while (rs.next()) {
                model1.addRow(new Object[]{
                    rs.getString("id_supplier"),
                    rs.getString("nama_pt"),
                    rs.getString("alamat"),
                    rs.getString("email"),
                    rs.getString("no_tlp")
                });
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
          private void tampilkan_KAS() {
        
            String [] judul1={"ID KAS","TANGGAL","KODE AKUN","KATEGORI","KODE USER","USERNAME","DEBIT","KREDIT","SALDO","KETERANGAN"};
            model1 = new DefaultTableModel(judul1,0);
            LapKas.setModel(model1);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_bukukas KAS "
                    + "LEFT JOIN tb_akun AKN ON KAS.kategori= AKN.kd_akun "
                    + "LEFT JOIN tb_user USR ON USR.id_user=KAS.User ");
            while (rs.next()) {
                model1.addRow(new Object[]{
                    rs.getString("Id_bukukas"),
                    rs.getString("Tanggal"),
                    rs.getString("Kategori"),
                    rs.getString("nama_akun"),
                    rs.getString("User"),
                    rs.getString("Username"),
                    rs.getString("Debit"),
                    rs.getString("Kredit"),
                    rs.getString("Saldo"),
                    rs.getString("Keterangan")
                });
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
            private void cariLapKas(String key){
    try{
        String [] judul1={"ID KAS","TANGGAL","KODE AKUN","KATEGORI","KODE USER","USERNAME","DEBIT","KREDIT","SALDO","KETERANGAN"};
            model1 = new DefaultTableModel(judul1,0);
            LapKas.setModel(model1);
    Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_bukukas KAS "
                    + "LEFT JOIN tb_akun AKN ON KAS.kategori= AKN.kd_akun "
                    + "LEFT JOIN tb_user USR ON USR.id_user=KAS.User WHERE id_bukukas"
                    + " LIKE '%"+key+"%' OR Username LIKE '%"+key+"%' OR Tanggal LIKE '%"+key+"%' OR id_user LIKE '%"+key+"%' OR Debit LIKE '%"+key+"%' "
                    + "OR Kredit LIKE '%"+key+"%' OR Kategori LIKE '%"+key+"%' OR nama_akun LIKE '%"+key+"%' OR Saldo LIKE '%"+key+"%'"
                    + "OR Keterangan LIKE '%"+key+"%'");
            while (rs.next()) {
                String data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)};
                model1.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
             public void loadDataKas() {
        String id_kategori;
        String id_User;
         ArrayList<String> item_id_Kat = new ArrayList<String>();
       
        try {
            Connection c = connect.getConnection();
            Statement s = c.createStatement();
            cbKategori.removeAllItems();
           String sql = "SELECT * FROM tb_akun";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
             item_id_Kat.add(r.getString("kd_akun"));
            }
            for (int i = 0; i < item_id_Kat.size(); i++) {
              
                cbKategori.addItem(item_id_Kat.get(i));
            }
        }
    catch (Exception e) {
            System.out.println("terjadi kesalahan");
        }
             }
          private void tampilkan_UtangPiutang() {
        
            String [] judul1={"ID KAS","TANGGAL","KATEGORI","USERNAME","DEBIT","KREDIT","KETERANGAN"};
            model1 = new DefaultTableModel(judul1,0);
            LapUtangPiutang.setModel(model1);
            try {
            Connection en = connect.getConnection();
            ResultSet rs = en.createStatement().executeQuery("SELECT * FROM tb_bukukas KAS "
                    + "LEFT JOIN tb_akun AKN ON KAS.kategori= AKN.kd_akun "
                    + "LEFT JOIN tb_user USR ON USR.id_user=KAS.User WHERE AKN.kd_akun=103 OR AKN.kd_akun=202");
            while (rs.next()) {
                model1.addRow(new Object[]{
                    rs.getString("Id_bukukas"),
                    rs.getString("Tanggal"),
                    rs.getString("nama_akun"),
                    rs.getString("Username"),
                    rs.getString("Debit"),
                    rs.getString("Kredit"),
                    rs.getString("Keterangan")
                });
            }
        } 
            catch (SQLException ex) {
            Logger.getLogger(mainView.class.getName()).log(Level.SEVERE, null, ex);
        }
}
          public static void setAndGetData(String kodeObat){
        try {
            Connection c = connect.getConnection();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM tb_barang WHERE kd_obat = '" + kodeObat + "'";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                txID_Barang.setText(r.getString("kd_obat"));
                txNamaObat.setText(r.getString("nama_obat"));
                txHarga.setText(r.getString("harga_jual"));
            }
            r.close();
            s.close();
        } catch (Exception e) {
            System.out.println("terjadi kesalahan");
        }
    }
    
     
  /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
   
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Login = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        ButtonLogin = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        Panel = new javax.swing.JPanel();
        panelnama = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        Home = new javax.swing.JPanel();
        menuHome = new javax.swing.JPanel();
        buttonHome_home = new javax.swing.JButton();
        buttonBrg_home = new javax.swing.JButton();
        buttonKyw_home = new javax.swing.JButton();
        buttonKasir_home = new javax.swing.JButton();
        buttonLap_home = new javax.swing.JButton();
        buttonUser_home = new javax.swing.JButton();
        buttonSupplier_home = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Label_tgl = new javax.swing.JLabel();
        Label_jam = new javax.swing.JLabel();
        LabelUSer = new javax.swing.JLabel();
        labelGambar = new javax.swing.JLabel();
        dataBarang = new javax.swing.JPanel();
        menuHome3 = new javax.swing.JPanel();
        buttonBrg_brg = new javax.swing.JButton();
        buttonInsert_brg = new javax.swing.JButton();
        buttonJenis_brg = new javax.swing.JButton();
        buttonSatuan_brg = new javax.swing.JButton();
        buttonHome_brg = new javax.swing.JButton();
        mainPanelBrg = new javax.swing.JPanel();
        Barang = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBarang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        buttonHapus_brg = new javax.swing.JButton();
        txtCariBrg = new javax.swing.JTextField();
        buttonCari_Brg = new javax.swing.JButton();
        Insert = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nama_obat = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        kd_obat = new javax.swing.JTextField();
        harga = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        stok = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbSatuan = new javax.swing.JComboBox();
        buttonSimpan = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        hargaJual = new javax.swing.JTextField();
        cbJenis = new javax.swing.JComboBox();
        ManageJenis = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableJenis = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        id_jenis = new javax.swing.JTextField();
        jenis = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        buttonTambahJenis = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        buttonHapus_jns = new javax.swing.JButton();
        ManageSatuan = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSatuan = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        id_satuan = new javax.swing.JTextField();
        satuan = new javax.swing.JTextField();
        buttonTambah_Satuan = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        buttonHapus_satuan = new javax.swing.JButton();
        dataKaryawan = new javax.swing.JPanel();
        menuHome4 = new javax.swing.JPanel();
        buttonKyw_kyw = new javax.swing.JButton();
        buttonManageAkun_kyw = new javax.swing.JButton();
        buttonInsert_kyw = new javax.swing.JButton();
        buttonHome_kyw = new javax.swing.JButton();
        buttonGaji_kyw = new javax.swing.JButton();
        mainPanelKyw = new javax.swing.JPanel();
        Karyawan = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelKyw = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        buttonHapus_kyw = new javax.swing.JButton();
        txtCari_kyw = new javax.swing.JTextField();
        buttonCari_kyw = new javax.swing.JButton();
        BuatAkun = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        id_user = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        buttonTambah_akun = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        buttonHapus_akun = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        level = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        InsertKaryawan = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        id_karyawan = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        cbUser = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        nama_karyawan = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        perempuan = new javax.swing.JRadioButton();
        laki = new javax.swing.JRadioButton();
        jLabel30 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        no_tlp = new javax.swing.JTextField();
        kewarganegaraan = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        pendidikan_terakhir = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        alamat = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        status = new javax.swing.JTextField();
        gaji_pokok = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        Gaji = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txID_Kyw = new javax.swing.JTextField();
        buttonCariGajiKyw = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        txNama_kyw = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txStatus_Kyw = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txGaji_Pokok = new javax.swing.JTextField();
        txMasaKerja_Kyw = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txJumlahLembur = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txAsuransi = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txBonus = new javax.swing.JTextField();
        buttonHitungGaji = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        txGajiBersih = new javax.swing.JTextField();
        buttonSimpanGajiBersih = new javax.swing.JButton();
        txPemotongan = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        Kasir = new javax.swing.JPanel();
        menuHome5 = new javax.swing.JPanel();
        buttonTransaksi_tr = new javax.swing.JButton();
        buttonHome_tr = new javax.swing.JButton();
        mainPanelTr = new javax.swing.JPanel();
        Transaksi = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        txNoTransaksi = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        txTanggal = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        enter = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        txNamaObat = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        txHarga = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        txJumlah = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelKasir = new javax.swing.JTable();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        txTotalBayar = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        txBayar = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        txKembalian = new javax.swing.JTextField();
        txTampil = new javax.swing.JTextField();
        buttonSimpan_Kasir = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        txtuser = new javax.swing.JTextField();
        txID_Barang = new javax.swing.JTextField();
        txtId_user = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        Laporan = new javax.swing.JPanel();
        menuHome8 = new javax.swing.JPanel();
        buttonLapBrg_lap = new javax.swing.JButton();
        buttonLapJual_lap = new javax.swing.JButton();
        buttonHome_lap = new javax.swing.JButton();
        buttonLapBeli_lap = new javax.swing.JButton();
        buttonKas_lap = new javax.swing.JButton();
        buttonLapUtang_lap = new javax.swing.JButton();
        mainPanelLap = new javax.swing.JPanel();
        LapPenjualan = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        LapPen = new javax.swing.JTable();
        txtCariLapJual = new javax.swing.JTextField();
        buttonCariLapJual = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        jButton48 = new javax.swing.JButton();
        LapPembelian = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        txCariLapBeli = new javax.swing.JTextField();
        buttonCariLapBeli = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        LapBeli = new javax.swing.JTable();
        jButton50 = new javax.swing.JButton();
        LapUtang = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        LapUtangPiutang = new javax.swing.JTable();
        jButton52 = new javax.swing.JButton();
        KasBesar = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        CariLapKas = new javax.swing.JTextField();
        jButton61 = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        LapKas = new javax.swing.JTable();
        jButton62 = new javax.swing.JButton();
        buttonTambahKas = new javax.swing.JButton();
        TambahKas = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        idKas = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        cbKategori = new javax.swing.JComboBox();
        jLabel104 = new javax.swing.JLabel();
        TanggalKas = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        kreditKas = new javax.swing.JTextField();
        debitKas = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        saldoKas = new javax.swing.JTextField();
        keteranganKas = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jLabel113 = new javax.swing.JLabel();
        UserKas = new javax.swing.JTextField();
        LapBarang = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        txCariLapBrg = new javax.swing.JTextField();
        jButton59 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        LapBrg = new javax.swing.JTable();
        jButton60 = new javax.swing.JButton();
        Supplier = new javax.swing.JPanel();
        menuHome7 = new javax.swing.JPanel();
        buttonDataBeli_supp = new javax.swing.JButton();
        buttonHome_supp = new javax.swing.JButton();
        buttonTambahBeli_supp = new javax.swing.JButton();
        buttonManage_supp = new javax.swing.JButton();
        mainPanelSupplier = new javax.swing.JPanel();
        Pembelian = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        LapBeli1 = new javax.swing.JTable();
        jLabel75 = new javax.swing.JLabel();
        txtCariPembelian = new javax.swing.JTextField();
        buttonCariPembelian = new javax.swing.JButton();
        buttonEdit_Pembelian = new javax.swing.JButton();
        buttonHapus_Pembelian = new javax.swing.JButton();
        TambahPembelian = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        totalHargaBeli = new javax.swing.JTextField();
        JumlahBeli = new javax.swing.JTextField();
        hargaBeli = new javax.swing.JTextField();
        cbSuppBeli = new javax.swing.JComboBox();
        tanggalBeli = new javax.swing.JTextField();
        kd_pembelian = new javax.swing.JTextField();
        buttonSimpanBeli = new javax.swing.JButton();
        jLabel98 = new javax.swing.JLabel();
        cbObatBeli = new javax.swing.JComboBox();
        User_beli = new javax.swing.JTextField();
        ManageSupplier = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        nama_pt = new javax.swing.JTextField();
        id_supp = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableSupp = new javax.swing.JTable();
        buttonTambahSupp = new javax.swing.JButton();
        buttonHapus_Supp = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        alamatsupp = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        emailsupp = new javax.swing.JTextField();
        telpsupp = new javax.swing.JTextField();
        User = new javax.swing.JPanel();
        menuHome6 = new javax.swing.JPanel();
        buttonData_user = new javax.swing.JButton();
        buttonUbahPass_user = new javax.swing.JButton();
        buttonHome_user = new javax.swing.JButton();
        mainPanelUser = new javax.swing.JPanel();
        BioUser = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        txtIdUser = new javax.swing.JTextField();
        txtIdKaryawan = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtJK = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtNotlp = new javax.swing.JTextField();
        txtWarga = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtPendidikan = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        UbahPass = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        txtPassLama = new javax.swing.JPasswordField();
        txtPassBaru = new javax.swing.JPasswordField();
        txtPassBaru2 = new javax.swing.JPasswordField();
        jLabel92 = new javax.swing.JLabel();
        buttonSimpan_pass = new javax.swing.JButton();

        jTextField3.setText("jTextField3");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        jLabel31.setText("STATUS");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel10.setBackground(new java.awt.Color(224, 216, 176));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel71.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel71.setText("Halaman Login");

        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel72.setText("USERNAME:");

        jLabel73.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel73.setText("PASSWORD:");

        ButtonLogin.setBackground(new java.awt.Color(222, 160, 87));
        ButtonLogin.setText("LOGIN");
        ButtonLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonLoginMouseClicked(evt);
            }
        });
        ButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(261, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(ButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(293, 293, 293))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addGap(260, 260, 260))))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(289, 289, 289)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel71)
                .addGap(41, 41, 41)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel73)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(ButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );

        javax.swing.GroupLayout LoginLayout = new javax.swing.GroupLayout(Login);
        Login.setLayout(LoginLayout);
        LoginLayout.setHorizontalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        LoginLayout.setVerticalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(Login, "card3");

        panelnama.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 1, 36)); // NOI18N
        jLabel7.setText("APOTEK GUARDIAN");

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/farmasi.png"))); // NOI18N

        javax.swing.GroupLayout panelnamaLayout = new javax.swing.GroupLayout(panelnama);
        panelnama.setLayout(panelnamaLayout);
        panelnamaLayout.setHorizontalGroup(
            panelnamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelnamaLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(539, Short.MAX_VALUE))
        );
        panelnamaLayout.setVerticalGroup(
            panelnamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelnamaLayout.createSequentialGroup()
                .addGroup(panelnamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelnamaLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelnamaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelnamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addContainerGap())
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new java.awt.CardLayout());

        Home.setBackground(new java.awt.Color(224, 216, 176));

        menuHome.setBackground(new java.awt.Color(222, 160, 87));

        buttonHome_home.setBackground(new java.awt.Color(224, 216, 176));
        buttonHome_home.setText("Home");
        buttonHome_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHome_homeActionPerformed(evt);
            }
        });

        buttonBrg_home.setBackground(new java.awt.Color(224, 216, 176));
        buttonBrg_home.setText("Data Barang");
        buttonBrg_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBrg_homeActionPerformed(evt);
            }
        });

        buttonKyw_home.setBackground(new java.awt.Color(224, 216, 176));
        buttonKyw_home.setText("Data Karyawan");
        buttonKyw_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKyw_homeActionPerformed(evt);
            }
        });

        buttonKasir_home.setBackground(new java.awt.Color(224, 216, 176));
        buttonKasir_home.setText("Kasir");
        buttonKasir_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKasir_homeActionPerformed(evt);
            }
        });

        buttonLap_home.setBackground(new java.awt.Color(224, 216, 176));
        buttonLap_home.setText("Laporan ");
        buttonLap_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLap_homeActionPerformed(evt);
            }
        });

        buttonUser_home.setBackground(new java.awt.Color(224, 216, 176));
        buttonUser_home.setText("User");
        buttonUser_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUser_homeActionPerformed(evt);
            }
        });

        buttonSupplier_home.setBackground(new java.awt.Color(224, 216, 176));
        buttonSupplier_home.setText("Supplier");
        buttonSupplier_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSupplier_homeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuHomeLayout = new javax.swing.GroupLayout(menuHome);
        menuHome.setLayout(menuHomeLayout);
        menuHomeLayout.setHorizontalGroup(
            menuHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuHomeLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(menuHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonBrg_home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuHomeLayout.createSequentialGroup()
                        .addComponent(buttonHome_home, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(buttonKyw_home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonKasir_home, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonLap_home, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSupplier_home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonUser_home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        menuHomeLayout.setVerticalGroup(
            menuHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuHomeLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(buttonHome_home, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(buttonBrg_home, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(buttonKyw_home, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(buttonKasir_home, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(buttonLap_home, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(buttonSupplier_home, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(buttonUser_home, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(222, 160, 87));

        Label_tgl.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        Label_tgl.setText("Tanggal");

        Label_jam.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        Label_jam.setText("Pukul");

        LabelUSer.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        LabelUSer.setText("USer");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(Label_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(Label_jam, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 263, Short.MAX_VALUE)
                .addComponent(LabelUSer, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_jam, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelUSer, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        labelGambar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/obat2.jpg"))); // NOI18N

        javax.swing.GroupLayout HomeLayout = new javax.swing.GroupLayout(Home);
        Home.setLayout(HomeLayout);
        HomeLayout.setHorizontalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addComponent(menuHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(HomeLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(labelGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        HomeLayout.setVerticalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(HomeLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(labelGambar)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        mainPanel.add(Home, "card2");

        dataBarang.setBackground(new java.awt.Color(224, 216, 176));

        menuHome3.setBackground(new java.awt.Color(222, 160, 87));

        buttonBrg_brg.setBackground(new java.awt.Color(224, 216, 176));
        buttonBrg_brg.setText("Data Barang");
        buttonBrg_brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBrg_brgActionPerformed(evt);
            }
        });

        buttonInsert_brg.setBackground(new java.awt.Color(224, 216, 176));
        buttonInsert_brg.setText("Insert");
        buttonInsert_brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInsert_brgActionPerformed(evt);
            }
        });

        buttonJenis_brg.setBackground(new java.awt.Color(224, 216, 176));
        buttonJenis_brg.setText("Manage Jenis");
        buttonJenis_brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonJenis_brgActionPerformed(evt);
            }
        });

        buttonSatuan_brg.setBackground(new java.awt.Color(224, 216, 176));
        buttonSatuan_brg.setText("Manage Satuan");
        buttonSatuan_brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSatuan_brgActionPerformed(evt);
            }
        });

        buttonHome_brg.setBackground(new java.awt.Color(224, 216, 176));
        buttonHome_brg.setText("Home");
        buttonHome_brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHome_brgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuHome3Layout = new javax.swing.GroupLayout(menuHome3);
        menuHome3.setLayout(menuHome3Layout);
        menuHome3Layout.setHorizontalGroup(
            menuHome3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuHome3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(menuHome3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonBrg_brg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonInsert_brg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonJenis_brg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSatuan_brg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(buttonHome_brg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        menuHome3Layout.setVerticalGroup(
            menuHome3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuHome3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(buttonHome_brg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(buttonBrg_brg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(buttonInsert_brg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(buttonJenis_brg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(buttonSatuan_brg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanelBrg.setLayout(new java.awt.CardLayout());

        Barang.setBackground(new java.awt.Color(224, 216, 176));

        tabelBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelBarang);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel2.setText("Data Barang");

        jButton19.setBackground(new java.awt.Color(222, 160, 87));
        jButton19.setText("EDIT");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        buttonHapus_brg.setBackground(new java.awt.Color(222, 160, 87));
        buttonHapus_brg.setText("HAPUS");
        buttonHapus_brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapus_brgActionPerformed(evt);
            }
        });

        txtCariBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariBrgActionPerformed(evt);
            }
        });

        buttonCari_Brg.setBackground(new java.awt.Color(222, 160, 87));
        buttonCari_Brg.setText("Cari");
        buttonCari_Brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCari_BrgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BarangLayout = new javax.swing.GroupLayout(Barang);
        Barang.setLayout(BarangLayout);
        BarangLayout.setHorizontalGroup(
            BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCariBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(buttonCari_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(BarangLayout.createSequentialGroup()
                .addGroup(BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BarangLayout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BarangLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BarangLayout.createSequentialGroup()
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(buttonHapus_brg, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        BarangLayout.setVerticalGroup(
            BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarangLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCari_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(BarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonHapus_brg, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 87, Short.MAX_VALUE))
        );

        mainPanelBrg.add(Barang, "card2");

        Insert.setBackground(new java.awt.Color(224, 216, 176));

        jLabel6.setText("HARGA BELI");

        jLabel8.setText("STOK");

        jLabel3.setText("NAMA OBAT");

        jLabel4.setText("SATUAN");

        jLabel5.setText("KODE OBAT");

        harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaActionPerformed(evt);
            }
        });

        jLabel9.setText("JENIS");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel10.setText("Data Barang");

        cbSatuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSatuanActionPerformed(evt);
            }
        });

        buttonSimpan.setBackground(new java.awt.Color(222, 160, 87));
        buttonSimpan.setText("Simpan");
        buttonSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSimpanActionPerformed(evt);
            }
        });

        jLabel18.setText("HARGA JUAL");

        hargaJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaJualActionPerformed(evt);
            }
        });

        cbJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbJenisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout InsertLayout = new javax.swing.GroupLayout(Insert);
        Insert.setLayout(InsertLayout);
        InsertLayout.setHorizontalGroup(
            InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InsertLayout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InsertLayout.createSequentialGroup()
                        .addComponent(kd_obat, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(216, 216, 216))
                    .addGroup(InsertLayout.createSequentialGroup()
                        .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nama_obat, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(stok, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InsertLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InsertLayout.createSequentialGroup()
                        .addComponent(buttonSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InsertLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(331, 331, 331))))
        );
        InsertLayout.setVerticalGroup(
            InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InsertLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kd_obat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nama_obat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(25, 25, 25)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(31, 31, 31)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(InsertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(stok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(buttonSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        mainPanelBrg.add(Insert, "card3");

        ManageJenis.setBackground(new java.awt.Color(224, 216, 176));

        jLabel12.setText("ID Jenis:");

        tableJenis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableJenis);

        jLabel13.setText("Jenis:");

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel11.setText("Manage Jenis");

        buttonTambahJenis.setBackground(new java.awt.Color(222, 160, 87));
        buttonTambahJenis.setText("Tambah");
        buttonTambahJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambahJenisActionPerformed(evt);
            }
        });

        jButton28.setBackground(new java.awt.Color(222, 160, 87));
        jButton28.setText("Edit");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        buttonHapus_jns.setBackground(new java.awt.Color(222, 160, 87));
        buttonHapus_jns.setText("Hapus");
        buttonHapus_jns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapus_jnsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ManageJenisLayout = new javax.swing.GroupLayout(ManageJenis);
        ManageJenis.setLayout(ManageJenisLayout);
        ManageJenisLayout.setHorizontalGroup(
            ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageJenisLayout.createSequentialGroup()
                .addGroup(ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageJenisLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addGroup(ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(ManageJenisLayout.createSequentialGroup()
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(ManageJenisLayout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(55, 55, 55)
                                    .addComponent(id_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(84, 84, 84)
                        .addComponent(buttonTambahJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ManageJenisLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonHapus_jns, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        ManageJenisLayout.setVerticalGroup(
            ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageJenisLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(id_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonTambahJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(67, 67, 67)
                .addGroup(ManageJenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageJenisLayout.createSequentialGroup()
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(buttonHapus_jns, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(151, Short.MAX_VALUE))
        );

        mainPanelBrg.add(ManageJenis, "card4");

        ManageSatuan.setBackground(new java.awt.Color(224, 216, 176));

        tableSatuan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tableSatuan);

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel14.setText("Manage Satuan");

        jLabel15.setText("Id Satuan:");

        jLabel16.setText("Satuan:");

        buttonTambah_Satuan.setBackground(new java.awt.Color(222, 160, 87));
        buttonTambah_Satuan.setText("Tambah");
        buttonTambah_Satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambah_SatuanActionPerformed(evt);
            }
        });

        jButton31.setBackground(new java.awt.Color(222, 160, 87));
        jButton31.setText("Edit");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        buttonHapus_satuan.setBackground(new java.awt.Color(222, 160, 87));
        buttonHapus_satuan.setText("Hapus");
        buttonHapus_satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapus_satuanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ManageSatuanLayout = new javax.swing.GroupLayout(ManageSatuan);
        ManageSatuan.setLayout(ManageSatuanLayout);
        ManageSatuanLayout.setHorizontalGroup(
            ManageSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManageSatuanLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonTambah_Satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(301, 301, 301))
            .addGroup(ManageSatuanLayout.createSequentialGroup()
                .addGroup(ManageSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageSatuanLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addGroup(ManageSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(57, 57, 57)
                        .addGroup(ManageSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ManageSatuanLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(ManageSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonHapus_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ManageSatuanLayout.createSequentialGroup()
                        .addGap(287, 287, 287)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        ManageSatuanLayout.setVerticalGroup(
            ManageSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageSatuanLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(ManageSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(id_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ManageSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGroup(ManageSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ManageSatuanLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonHapus_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(ManageSatuanLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(buttonTambah_Satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        mainPanelBrg.add(ManageSatuan, "card5");

        javax.swing.GroupLayout dataBarangLayout = new javax.swing.GroupLayout(dataBarang);
        dataBarang.setLayout(dataBarangLayout);
        dataBarangLayout.setHorizontalGroup(
            dataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataBarangLayout.createSequentialGroup()
                .addComponent(menuHome3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanelBrg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dataBarangLayout.setVerticalGroup(
            dataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanelBrg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuHome3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainPanel.add(dataBarang, "card3");

        dataKaryawan.setBackground(new java.awt.Color(224, 216, 176));

        menuHome4.setBackground(new java.awt.Color(222, 160, 87));

        buttonKyw_kyw.setBackground(new java.awt.Color(224, 216, 176));
        buttonKyw_kyw.setText("Data Karyawan");
        buttonKyw_kyw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKyw_kywActionPerformed(evt);
            }
        });

        buttonManageAkun_kyw.setBackground(new java.awt.Color(224, 216, 176));
        buttonManageAkun_kyw.setText("Manage Akun");
        buttonManageAkun_kyw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonManageAkun_kywActionPerformed(evt);
            }
        });

        buttonInsert_kyw.setBackground(new java.awt.Color(224, 216, 176));
        buttonInsert_kyw.setText("Insert Karyawan");
        buttonInsert_kyw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInsert_kywActionPerformed(evt);
            }
        });

        buttonHome_kyw.setBackground(new java.awt.Color(224, 216, 176));
        buttonHome_kyw.setText("Home");
        buttonHome_kyw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHome_kywActionPerformed(evt);
            }
        });

        buttonGaji_kyw.setBackground(new java.awt.Color(224, 216, 176));
        buttonGaji_kyw.setText("Gaji Karyawan");
        buttonGaji_kyw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGaji_kywActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuHome4Layout = new javax.swing.GroupLayout(menuHome4);
        menuHome4.setLayout(menuHome4Layout);
        menuHome4Layout.setHorizontalGroup(
            menuHome4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuHome4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(menuHome4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonKyw_kyw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonManageAkun_kyw, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(buttonInsert_kyw, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonHome_kyw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonGaji_kyw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        menuHome4Layout.setVerticalGroup(
            menuHome4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuHome4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(buttonHome_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(buttonKyw_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(buttonManageAkun_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(buttonInsert_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(buttonGaji_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanelKyw.setLayout(new java.awt.CardLayout());

        Karyawan.setBackground(new java.awt.Color(224, 216, 176));

        tabelKyw.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tabelKyw);

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel17.setText("Data Karyawan");

        jButton7.setBackground(new java.awt.Color(222, 160, 87));
        jButton7.setText("Edit");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        buttonHapus_kyw.setBackground(new java.awt.Color(222, 160, 87));
        buttonHapus_kyw.setText("Hapus");
        buttonHapus_kyw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapus_kywActionPerformed(evt);
            }
        });

        buttonCari_kyw.setBackground(new java.awt.Color(222, 160, 87));
        buttonCari_kyw.setText("Cari");
        buttonCari_kyw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCari_kywActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout KaryawanLayout = new javax.swing.GroupLayout(Karyawan);
        Karyawan.setLayout(KaryawanLayout);
        KaryawanLayout.setHorizontalGroup(
            KaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KaryawanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(KaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KaryawanLayout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(buttonHapus_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KaryawanLayout.createSequentialGroup()
                        .addComponent(txtCari_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(buttonCari_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(128, 128, 128))
            .addGroup(KaryawanLayout.createSequentialGroup()
                .addGroup(KaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(KaryawanLayout.createSequentialGroup()
                        .addGap(281, 281, 281)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(KaryawanLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        KaryawanLayout.setVerticalGroup(
            KaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KaryawanLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(KaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCari_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCari_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(KaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonHapus_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        mainPanelKyw.add(Karyawan, "card2");

        BuatAkun.setBackground(new java.awt.Color(224, 216, 176));

        tableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tableUser);

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel21.setText("Buat Akun");

        jLabel22.setText("Id User:");

        jLabel23.setText("Username:");

        buttonTambah_akun.setBackground(new java.awt.Color(222, 160, 87));
        buttonTambah_akun.setText("Tambah");
        buttonTambah_akun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambah_akunActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(222, 160, 87));
        jButton13.setText("Edit");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        buttonHapus_akun.setBackground(new java.awt.Color(222, 160, 87));
        buttonHapus_akun.setText("Hapus");
        buttonHapus_akun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapus_akunActionPerformed(evt);
            }
        });

        jLabel24.setText("Password:");

        jLabel74.setText("Level:");

        javax.swing.GroupLayout BuatAkunLayout = new javax.swing.GroupLayout(BuatAkun);
        BuatAkun.setLayout(BuatAkunLayout);
        BuatAkunLayout.setHorizontalGroup(
            BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuatAkunLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BuatAkunLayout.createSequentialGroup()
                        .addGroup(BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(BuatAkunLayout.createSequentialGroup()
                                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, BuatAkunLayout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(BuatAkunLayout.createSequentialGroup()
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(BuatAkunLayout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(id_user, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(96, 96, 96)
                        .addComponent(buttonTambah_akun, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BuatAkunLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonHapus_akun, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(133, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BuatAkunLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(313, 313, 313))
        );
        BuatAkunLayout.setVerticalGroup(
            BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuatAkunLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BuatAkunLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BuatAkunLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(id_user, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(BuatAkunLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(BuatAkunLayout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(BuatAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35))
                            .addGroup(BuatAkunLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
                                .addComponent(buttonTambah_akun, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(103, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BuatAkunLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonHapus_akun, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151))))
        );

        mainPanelKyw.add(BuatAkun, "card4");

        InsertKaryawan.setBackground(new java.awt.Color(224, 216, 176));

        jLabel25.setText("ID KARYAWAN:");

        jLabel26.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel26.setText("Tambah Karyawan");

        id_karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_karyawanActionPerformed(evt);
            }
        });

        jLabel27.setText("ID USER:");

        cbUser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel28.setText("NAMA KARYAWAN:");

        jLabel29.setText("JENIS KELAMIN:");

        perempuan.setText("Perempuan");
        perempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                perempuanActionPerformed(evt);
            }
        });

        laki.setText("Laki - Laki");
        laki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lakiActionPerformed(evt);
            }
        });

        jLabel30.setText("EMAIL:");

        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });

        jLabel32.setText("STATUS:");

        jLabel33.setText("NO TELEPON:");

        jLabel34.setText("KEWARGANEGARAAN:");

        no_tlp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_tlpActionPerformed(evt);
            }
        });

        kewarganegaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kewarganegaraanActionPerformed(evt);
            }
        });

        jLabel35.setText("PENDIDIKAN TERAKHIR:");

        pendidikan_terakhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendidikan_terakhirActionPerformed(evt);
            }
        });

        jLabel36.setText("ALAMAT:");

        alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamatActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(222, 160, 87));
        jButton15.setText("TAMBAH");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });

        gaji_pokok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gaji_pokokActionPerformed(evt);
            }
        });

        jLabel97.setText("GAJI POKOK:");

        javax.swing.GroupLayout InsertKaryawanLayout = new javax.swing.GroupLayout(InsertKaryawan);
        InsertKaryawan.setLayout(InsertKaryawanLayout);
        InsertKaryawanLayout.setHorizontalGroup(
            InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InsertKaryawanLayout.createSequentialGroup()
                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InsertKaryawanLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel30)
                            .addComponent(jLabel29)
                            .addComponent(jLabel32))
                        .addGap(26, 26, 26)
                        .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(InsertKaryawanLayout.createSequentialGroup()
                                .addComponent(perempuan)
                                .addGap(18, 18, 18)
                                .addComponent(laki))
                            .addComponent(id_karyawan)
                            .addComponent(cbUser, 0, 174, Short.MAX_VALUE)
                            .addComponent(nama_karyawan)
                            .addComponent(email)
                            .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                        .addGap(41, 41, 41)
                        .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(InsertKaryawanLayout.createSequentialGroup()
                                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel34))
                                .addGap(18, 18, 18)
                                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(kewarganegaraan, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(no_tlp, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(InsertKaryawanLayout.createSequentialGroup()
                                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel97))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pendidikan_terakhir)
                                    .addComponent(alamat)
                                    .addComponent(gaji_pokok)))))
                    .addGroup(InsertKaryawanLayout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(180, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InsertKaryawanLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        InsertKaryawanLayout.setVerticalGroup(
            InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InsertKaryawanLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(id_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(no_tlp, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(cbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(kewarganegaraan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nama_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel35)
                    .addComponent(pendidikan_terakhir, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(perempuan)
                    .addComponent(jLabel29)
                    .addComponent(laki)
                    .addComponent(jLabel36)
                    .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InsertKaryawanLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(gaji_pokok, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(InsertKaryawanLayout.createSequentialGroup()
                        .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InsertKaryawanLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel97)))
                            .addGroup(InsertKaryawanLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(InsertKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addGap(217, 217, 217))))
        );

        mainPanelKyw.add(InsertKaryawan, "card5");

        Gaji.setBackground(new java.awt.Color(224, 216, 176));

        jLabel37.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel37.setText("Gaji Karyawan");

        jLabel38.setText("ID Karyawan:");

        txID_Kyw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txID_KywActionPerformed(evt);
            }
        });

        buttonCariGajiKyw.setBackground(new java.awt.Color(222, 160, 87));
        buttonCariGajiKyw.setText("Cari");
        buttonCariGajiKyw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCariGajiKywActionPerformed(evt);
            }
        });

        jLabel39.setText("Nama Karyawan");

        jLabel40.setText("Status");

        jLabel41.setText("Gaji Pokok");

        jLabel42.setText("Masa Kerja");

        jLabel43.setText("Bulan");

        jLabel44.setText("Jumlah Lembur");

        jLabel45.setText("Jam");

        jLabel46.setText("Asuransi");

        txAsuransi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txAsuransiActionPerformed(evt);
            }
        });

        jLabel47.setText("Bonus");

        buttonHitungGaji.setBackground(new java.awt.Color(222, 160, 87));
        buttonHitungGaji.setText("HITUNG");
        buttonHitungGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHitungGajiActionPerformed(evt);
            }
        });

        jLabel48.setText("GAJI BERSIH");

        buttonSimpanGajiBersih.setBackground(new java.awt.Color(222, 160, 87));
        buttonSimpanGajiBersih.setText("SIMPAN");
        buttonSimpanGajiBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSimpanGajiBersihActionPerformed(evt);
            }
        });

        txPemotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txPemotonganActionPerformed(evt);
            }
        });

        jLabel96.setText("Pemotongan");

        javax.swing.GroupLayout GajiLayout = new javax.swing.GroupLayout(Gaji);
        Gaji.setLayout(GajiLayout);
        GajiLayout.setHorizontalGroup(
            GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GajiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(287, 287, 287))
            .addGroup(GajiLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GajiLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(18, 18, 18)
                        .addComponent(txID_Kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(buttonCariGajiKyw, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(GajiLayout.createSequentialGroup()
                        .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txNama_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)
                            .addComponent(jLabel44)
                            .addGroup(GajiLayout.createSequentialGroup()
                                .addComponent(txJumlahLembur, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel45)))
                        .addGap(18, 18, 18)
                        .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txStatus_Kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40)
                            .addComponent(jLabel46)
                            .addComponent(txAsuransi, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GajiLayout.createSequentialGroup()
                                .addComponent(txMasaKerja_Kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel43))
                            .addComponent(jLabel42)
                            .addComponent(jLabel47)
                            .addComponent(txBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GajiLayout.createSequentialGroup()
                                .addComponent(txPemotongan, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(GajiLayout.createSequentialGroup()
                                .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel96)
                                    .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GajiLayout.createSequentialGroup()
                                            .addComponent(jLabel41)
                                            .addGap(144, 144, 144))
                                        .addGroup(GajiLayout.createSequentialGroup()
                                            .addComponent(txGaji_Pokok, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(GajiLayout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addGap(18, 18, 18)
                        .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GajiLayout.createSequentialGroup()
                                .addComponent(buttonSimpanGajiBersih, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(GajiLayout.createSequentialGroup()
                                .addComponent(txGajiBersih, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonHitungGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))))))
        );
        GajiLayout.setVerticalGroup(
            GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GajiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txID_Kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(buttonCariGajiKyw, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txNama_kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txStatus_Kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txGaji_Pokok, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txMasaKerja_Kyw, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGap(18, 18, 18)
                .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47)
                    .addComponent(jLabel96))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txJumlahLembur, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(txAsuransi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txPemotongan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GajiLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(GajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txGajiBersih, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48))
                        .addGap(18, 18, 18)
                        .addComponent(buttonSimpanGajiBersih, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(GajiLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(buttonHitungGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        mainPanelKyw.add(Gaji, "card6");

        javax.swing.GroupLayout dataKaryawanLayout = new javax.swing.GroupLayout(dataKaryawan);
        dataKaryawan.setLayout(dataKaryawanLayout);
        dataKaryawanLayout.setHorizontalGroup(
            dataKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataKaryawanLayout.createSequentialGroup()
                .addComponent(menuHome4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanelKyw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dataKaryawanLayout.setVerticalGroup(
            dataKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanelKyw, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
            .addComponent(menuHome4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainPanel.add(dataKaryawan, "card4");

        Kasir.setBackground(new java.awt.Color(224, 216, 176));

        menuHome5.setBackground(new java.awt.Color(222, 160, 87));

        buttonTransaksi_tr.setBackground(new java.awt.Color(224, 216, 176));
        buttonTransaksi_tr.setText("Transaksi");
        buttonTransaksi_tr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTransaksi_trActionPerformed(evt);
            }
        });

        buttonHome_tr.setBackground(new java.awt.Color(224, 216, 176));
        buttonHome_tr.setText("Home");
        buttonHome_tr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHome_trActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuHome5Layout = new javax.swing.GroupLayout(menuHome5);
        menuHome5.setLayout(menuHome5Layout);
        menuHome5Layout.setHorizontalGroup(
            menuHome5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuHome5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(menuHome5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonTransaksi_tr, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(buttonHome_tr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        menuHome5Layout.setVerticalGroup(
            menuHome5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuHome5Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(buttonHome_tr, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(buttonTransaksi_tr, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        mainPanelTr.setLayout(new java.awt.CardLayout());

        Transaksi.setBackground(new java.awt.Color(224, 216, 176));

        jLabel49.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel49.setText("Transaksi");

        jLabel50.setText("No Transaksi:");

        txNoTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNoTransaksiActionPerformed(evt);
            }
        });

        jLabel51.setText("ID User:");

        jLabel53.setText("Tanggal");

        txTanggal.setEnabled(false);

        jLabel54.setText("Kode Obat");

        enter.setBackground(new java.awt.Color(222, 160, 87));
        enter.setText("pilih");
        enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterActionPerformed(evt);
            }
        });

        jLabel56.setText("Nama Obat");

        txNamaObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNamaObatActionPerformed(evt);
            }
        });

        jLabel57.setText("Harga");

        txHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txHargaActionPerformed(evt);
            }
        });

        jLabel58.setText("Jumlah");

        txJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txJumlahActionPerformed(evt);
            }
        });

        tabelKasir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tabelKasir);

        jButton40.setBackground(new java.awt.Color(222, 160, 87));
        jButton40.setText("Tambah");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton41.setBackground(new java.awt.Color(222, 160, 87));
        jButton41.setText("Hapus");

        jLabel59.setText("Total Bayar");

        txTotalBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txTotalBayarActionPerformed(evt);
            }
        });

        jLabel60.setText("Bayar");

        txBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txBayarActionPerformed(evt);
            }
        });

        jLabel61.setText("Kembalian");

        txKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txKembalianActionPerformed(evt);
            }
        });

        txTampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txTampilActionPerformed(evt);
            }
        });

        buttonSimpan_Kasir.setBackground(new java.awt.Color(222, 160, 87));
        buttonSimpan_Kasir.setText("SIMPAN");
        buttonSimpan_Kasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSimpan_KasirActionPerformed(evt);
            }
        });

        jButton46.setBackground(new java.awt.Color(222, 160, 87));
        jButton46.setText("SCAN QRCODE");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });

        txtuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtuserActionPerformed(evt);
            }
        });

        txID_Barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txID_BarangActionPerformed(evt);
            }
        });

        txtId_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtId_userActionPerformed(evt);
            }
        });

        jLabel52.setText("Username:");

        javax.swing.GroupLayout TransaksiLayout = new javax.swing.GroupLayout(Transaksi);
        Transaksi.setLayout(TransaksiLayout);
        TransaksiLayout.setHorizontalGroup(
            TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransaksiLayout.createSequentialGroup()
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txTampil, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonSimpan_Kasir, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(112, 112, 112)
                                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(TransaksiLayout.createSequentialGroup()
                                        .addComponent(jLabel61)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(TransaksiLayout.createSequentialGroup()
                                        .addComponent(jLabel60)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(TransaksiLayout.createSequentialGroup()
                                        .addComponent(jLabel59)
                                        .addGap(26, 26, 26)
                                        .addComponent(txTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton40)
                                    .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
            .addGroup(TransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel50)
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel52))
                                .addGap(68, 68, 68))
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(TransaksiLayout.createSequentialGroup()
                                        .addComponent(txID_Barang, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(enter))
                                    .addComponent(jLabel54))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(120, 120, 120)
                                .addComponent(jLabel53)
                                .addGap(46, 46, 46)
                                .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtId_user, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(258, 258, 258))
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txNamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel56)))
                        .addGap(32, 32, 32)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58)
                            .addComponent(txJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        TransaksiLayout.setVerticalGroup(
            TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransaksiLayout.createSequentialGroup()
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(txtId_user, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TransaksiLayout.createSequentialGroup()
                                .addComponent(jLabel52)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel54))
                            .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txID_Barang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enter, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58)
                            .addComponent(jLabel57)
                            .addComponent(jLabel56))
                        .addGap(6, 6, 6)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txNamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel53))))
                .addGap(18, 18, 18)
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(TransaksiLayout.createSequentialGroup()
                        .addComponent(jButton40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton41)))
                .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, TransaksiLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel59)
                            .addComponent(txTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, TransaksiLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txTampil, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonSimpan_Kasir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        mainPanelTr.add(Transaksi, "card2");

        javax.swing.GroupLayout KasirLayout = new javax.swing.GroupLayout(Kasir);
        Kasir.setLayout(KasirLayout);
        KasirLayout.setHorizontalGroup(
            KasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KasirLayout.createSequentialGroup()
                .addComponent(menuHome5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanelTr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        KasirLayout.setVerticalGroup(
            KasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanelTr, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
            .addGroup(KasirLayout.createSequentialGroup()
                .addComponent(menuHome5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        mainPanel.add(Kasir, "card5");

        Laporan.setBackground(new java.awt.Color(224, 216, 176));

        menuHome8.setBackground(new java.awt.Color(222, 160, 87));

        buttonLapBrg_lap.setBackground(new java.awt.Color(224, 216, 176));
        buttonLapBrg_lap.setText("Laporan Barang");
        buttonLapBrg_lap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLapBrg_lapActionPerformed(evt);
            }
        });

        buttonLapJual_lap.setBackground(new java.awt.Color(224, 216, 176));
        buttonLapJual_lap.setText("Laporan Penjualan");
        buttonLapJual_lap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLapJual_lapActionPerformed(evt);
            }
        });

        buttonHome_lap.setBackground(new java.awt.Color(224, 216, 176));
        buttonHome_lap.setText("Home");
        buttonHome_lap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHome_lapActionPerformed(evt);
            }
        });

        buttonLapBeli_lap.setBackground(new java.awt.Color(224, 216, 176));
        buttonLapBeli_lap.setText("Laporan Pembelian");
        buttonLapBeli_lap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLapBeli_lapActionPerformed(evt);
            }
        });

        buttonKas_lap.setBackground(new java.awt.Color(224, 216, 176));
        buttonKas_lap.setText("Laporan KAS");
        buttonKas_lap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKas_lapActionPerformed(evt);
            }
        });

        buttonLapUtang_lap.setBackground(new java.awt.Color(224, 216, 176));
        buttonLapUtang_lap.setText("Laporan Utang Piutang");
        buttonLapUtang_lap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLapUtang_lapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuHome8Layout = new javax.swing.GroupLayout(menuHome8);
        menuHome8.setLayout(menuHome8Layout);
        menuHome8Layout.setHorizontalGroup(
            menuHome8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuHome8Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(menuHome8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(buttonLapUtang_lap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(buttonLapBeli_lap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(buttonLapJual_lap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonLapBrg_lap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonHome_lap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonKas_lap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        menuHome8Layout.setVerticalGroup(
            menuHome8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuHome8Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(buttonHome_lap, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(buttonLapBrg_lap, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(buttonLapJual_lap, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(buttonLapBeli_lap, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(buttonLapUtang_lap, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(buttonKas_lap, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        mainPanelLap.setBackground(new java.awt.Color(224, 216, 176));
        mainPanelLap.setLayout(new java.awt.CardLayout());

        LapPenjualan.setBackground(new java.awt.Color(224, 216, 176));

        LapPen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(LapPen);

        buttonCariLapJual.setBackground(new java.awt.Color(222, 160, 87));
        buttonCariLapJual.setText("Cari");
        buttonCariLapJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCariLapJualActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel64.setText("Laporan Penjualan");

        jButton48.setBackground(new java.awt.Color(222, 160, 87));
        jButton48.setText("CETAK");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LapPenjualanLayout = new javax.swing.GroupLayout(LapPenjualan);
        LapPenjualan.setLayout(LapPenjualanLayout);
        LapPenjualanLayout.setHorizontalGroup(
            LapPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LapPenjualanLayout.createSequentialGroup()
                .addGroup(LapPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LapPenjualanLayout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(LapPenjualanLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LapPenjualanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(LapPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LapPenjualanLayout.createSequentialGroup()
                        .addComponent(txtCariLapJual, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonCariLapJual, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LapPenjualanLayout.createSequentialGroup()
                        .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))))
        );
        LapPenjualanLayout.setVerticalGroup(
            LapPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LapPenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(LapPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariLapJual, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCariLapJual, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );

        mainPanelLap.add(LapPenjualan, "card3");

        LapPembelian.setBackground(new java.awt.Color(224, 216, 176));

        jLabel65.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel65.setText("Laporan Pembelian");

        buttonCariLapBeli.setBackground(new java.awt.Color(222, 160, 87));
        buttonCariLapBeli.setText("Cari");
        buttonCariLapBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCariLapBeliActionPerformed(evt);
            }
        });

        LapBeli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(LapBeli);

        jButton50.setBackground(new java.awt.Color(222, 160, 87));
        jButton50.setText("CETAK");
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LapPembelianLayout = new javax.swing.GroupLayout(LapPembelian);
        LapPembelian.setLayout(LapPembelianLayout);
        LapPembelianLayout.setHorizontalGroup(
            LapPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LapPembelianLayout.createSequentialGroup()
                .addGroup(LapPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LapPembelianLayout.createSequentialGroup()
                        .addGap(443, 443, 443)
                        .addGroup(LapPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(LapPembelianLayout.createSequentialGroup()
                                .addComponent(txCariLapBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(buttonCariLapBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                            .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(LapPembelianLayout.createSequentialGroup()
                        .addGap(267, 267, 267)
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(LapPembelianLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        LapPembelianLayout.setVerticalGroup(
            LapPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LapPembelianLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(LapPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txCariLapBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCariLapBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );

        mainPanelLap.add(LapPembelian, "card4");

        LapUtang.setBackground(new java.awt.Color(224, 216, 176));

        jLabel66.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel66.setText("Laporan Utang dan Piutang");

        LapUtangPiutang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(LapUtangPiutang);

        jButton52.setBackground(new java.awt.Color(222, 160, 87));
        jButton52.setText("CETAK");
        jButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton52ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LapUtangLayout = new javax.swing.GroupLayout(LapUtang);
        LapUtang.setLayout(LapUtangLayout);
        LapUtangLayout.setHorizontalGroup(
            LapUtangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LapUtangLayout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addGroup(LapUtangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LapUtangLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(251, 251, 251))
        );
        LapUtangLayout.setVerticalGroup(
            LapUtangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LapUtangLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel66)
                .addGap(87, 87, 87)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107))
        );

        mainPanelLap.add(LapUtang, "card5");

        KasBesar.setBackground(new java.awt.Color(224, 216, 176));

        jLabel67.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel67.setText("Laporan KAS ");

        jButton61.setBackground(new java.awt.Color(222, 160, 87));
        jButton61.setText("Cari");
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });

        LapKas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane13.setViewportView(LapKas);

        jButton62.setBackground(new java.awt.Color(222, 160, 87));
        jButton62.setText("CETAK");
        jButton62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton62ActionPerformed(evt);
            }
        });

        buttonTambahKas.setBackground(new java.awt.Color(222, 160, 87));
        buttonTambahKas.setText("Tambah ");
        buttonTambahKas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambahKasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout KasBesarLayout = new javax.swing.GroupLayout(KasBesar);
        KasBesar.setLayout(KasBesarLayout);
        KasBesarLayout.setHorizontalGroup(
            KasBesarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KasBesarLayout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(KasBesarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KasBesarLayout.createSequentialGroup()
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(251, 251, 251))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KasBesarLayout.createSequentialGroup()
                        .addComponent(CariLapKas, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButton61, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KasBesarLayout.createSequentialGroup()
                        .addGroup(KasBesarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(KasBesarLayout.createSequentialGroup()
                                .addComponent(buttonTambahKas, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48))))
        );
        KasBesarLayout.setVerticalGroup(
            KasBesarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KasBesarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(KasBesarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CariLapKas, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton61, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(KasBesarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonTambahKas, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(151, 151, 151))
        );

        mainPanelLap.add(KasBesar, "card6");

        jLabel102.setText("ID :");

        idKas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idKasActionPerformed(evt);
            }
        });

        jLabel103.setText("TANGGAL:");

        cbKategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel104.setText("KATEGORI:");

        jLabel105.setText("USER:");

        jLabel106.setText("DEBIT:");

        jLabel107.setText("KREDIT:");

        kreditKas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kreditKasActionPerformed(evt);
            }
        });

        debitKas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debitKasActionPerformed(evt);
            }
        });

        jLabel111.setText("KETERANGAN:");

        jLabel112.setText("SALDO:");

        saldoKas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saldoKasActionPerformed(evt);
            }
        });

        keteranganKas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keteranganKasActionPerformed(evt);
            }
        });

        jButton16.setText("TAMBAH");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel113.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel113.setText("Tambah KAS");

        javax.swing.GroupLayout TambahKasLayout = new javax.swing.GroupLayout(TambahKas);
        TambahKas.setLayout(TambahKasLayout);
        TambahKasLayout.setHorizontalGroup(
            TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TambahKasLayout.createSequentialGroup()
                .addContainerGap(334, Short.MAX_VALUE)
                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(294, 294, 294))
            .addGroup(TambahKasLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(TambahKasLayout.createSequentialGroup()
                        .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbKategori, javax.swing.GroupLayout.Alignment.LEADING, 0, 174, Short.MAX_VALUE)
                                .addComponent(idKas, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(TanggalKas, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UserKas, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel107)
                            .addComponent(jLabel111)
                            .addComponent(jLabel106)
                            .addComponent(jLabel112))
                        .addGap(68, 68, 68)
                        .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(debitKas, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(kreditKas, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(saldoKas)
                            .addComponent(keteranganKas))))
                .addContainerGap())
            .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TambahKasLayout.createSequentialGroup()
                    .addGap(33, 33, 33)
                    .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel102)
                        .addComponent(jLabel103)
                        .addComponent(jLabel104)
                        .addComponent(jLabel105))
                    .addContainerGap(752, Short.MAX_VALUE)))
        );
        TambahKasLayout.setVerticalGroup(
            TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TambahKasLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idKas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(debitKas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel106))
                .addGap(1, 1, 1)
                .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TambahKasLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TanggalKas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel107))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel112))
                        .addGap(13, 13, 13)
                        .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel111)
                            .addComponent(UserKas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(TambahKasLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(kreditKas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(saldoKas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(keteranganKas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(53, 53, 53)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
            .addGroup(TambahKasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TambahKasLayout.createSequentialGroup()
                    .addGap(144, 144, 144)
                    .addComponent(jLabel102)
                    .addGap(32, 32, 32)
                    .addComponent(jLabel103)
                    .addGap(31, 31, 31)
                    .addComponent(jLabel104)
                    .addGap(27, 27, 27)
                    .addComponent(jLabel105)
                    .addGap(247, 247, 247)))
        );

        mainPanelLap.add(TambahKas, "card7");

        LapBarang.setBackground(new java.awt.Color(224, 216, 176));

        jLabel63.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel63.setText("Laporan Barang");

        jButton59.setBackground(new java.awt.Color(222, 160, 87));
        jButton59.setText("Cari");
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });

        LapBrg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(LapBrg);

        jButton60.setBackground(new java.awt.Color(222, 160, 87));
        jButton60.setText("CETAK");
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LapBarangLayout = new javax.swing.GroupLayout(LapBarang);
        LapBarang.setLayout(LapBarangLayout);
        LapBarangLayout.setHorizontalGroup(
            LapBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LapBarangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txCariLapBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
            .addGroup(LapBarangLayout.createSequentialGroup()
                .addGroup(LapBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(LapBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(LapBarangLayout.createSequentialGroup()
                            .addGap(237, 237, 237)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(LapBarangLayout.createSequentialGroup()
                            .addGap(66, 66, 66)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        LapBarangLayout.setVerticalGroup(
            LapBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LapBarangLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(LapBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txCariLapBrg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        mainPanelLap.add(LapBarang, "card2");

        javax.swing.GroupLayout LaporanLayout = new javax.swing.GroupLayout(Laporan);
        Laporan.setLayout(LaporanLayout);
        LaporanLayout.setHorizontalGroup(
            LaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanLayout.createSequentialGroup()
                .addComponent(menuHome8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mainPanelLap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        LaporanLayout.setVerticalGroup(
            LaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuHome8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanelLap, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
        );

        mainPanel.add(Laporan, "card6");

        Supplier.setBackground(new java.awt.Color(224, 216, 176));

        menuHome7.setBackground(new java.awt.Color(222, 160, 87));

        buttonDataBeli_supp.setBackground(new java.awt.Color(224, 216, 176));
        buttonDataBeli_supp.setText("Data Pembelian");
        buttonDataBeli_supp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDataBeli_suppActionPerformed(evt);
            }
        });

        buttonHome_supp.setBackground(new java.awt.Color(224, 216, 176));
        buttonHome_supp.setText("Home");
        buttonHome_supp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHome_suppActionPerformed(evt);
            }
        });

        buttonTambahBeli_supp.setBackground(new java.awt.Color(224, 216, 176));
        buttonTambahBeli_supp.setText("Tambah Pembelian");
        buttonTambahBeli_supp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambahBeli_suppActionPerformed(evt);
            }
        });

        buttonManage_supp.setBackground(new java.awt.Color(224, 216, 176));
        buttonManage_supp.setText("Manage Supplier");
        buttonManage_supp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonManage_suppActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuHome7Layout = new javax.swing.GroupLayout(menuHome7);
        menuHome7.setLayout(menuHome7Layout);
        menuHome7Layout.setHorizontalGroup(
            menuHome7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuHome7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(menuHome7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonDataBeli_supp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonHome_supp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonTambahBeli_supp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(buttonManage_supp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        menuHome7Layout.setVerticalGroup(
            menuHome7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuHome7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(buttonHome_supp, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(buttonDataBeli_supp, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(buttonTambahBeli_supp, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(buttonManage_supp, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanelSupplier.setLayout(new java.awt.CardLayout());

        Pembelian.setBackground(new java.awt.Color(224, 216, 176));

        LapBeli1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane14.setViewportView(LapBeli1);

        jLabel75.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel75.setText(" Pembelian");

        buttonCariPembelian.setBackground(new java.awt.Color(222, 160, 87));
        buttonCariPembelian.setText("Cari");
        buttonCariPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCariPembelianActionPerformed(evt);
            }
        });

        buttonEdit_Pembelian.setBackground(new java.awt.Color(222, 160, 87));
        buttonEdit_Pembelian.setText("EDIT");
        buttonEdit_Pembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEdit_PembelianActionPerformed(evt);
            }
        });

        buttonHapus_Pembelian.setBackground(new java.awt.Color(222, 160, 87));
        buttonHapus_Pembelian.setText("HAPUS");

        javax.swing.GroupLayout PembelianLayout = new javax.swing.GroupLayout(Pembelian);
        Pembelian.setLayout(PembelianLayout);
        PembelianLayout.setHorizontalGroup(
            PembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PembelianLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(PembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PembelianLayout.createSequentialGroup()
                        .addComponent(txtCariPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonCariPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PembelianLayout.createSequentialGroup()
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PembelianLayout.createSequentialGroup()
                        .addComponent(buttonEdit_Pembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(buttonHapus_Pembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PembelianLayout.createSequentialGroup()
                        .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(278, 278, 278))))
        );
        PembelianLayout.setVerticalGroup(
            PembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PembelianLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCariPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(PembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonEdit_Pembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonHapus_Pembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94))
        );

        mainPanelSupplier.add(Pembelian, "card2");

        TambahPembelian.setBackground(new java.awt.Color(224, 216, 176));

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel19.setText("Tambah Pembelian");

        jLabel20.setText("KODE PEMBELIAN");

        jLabel55.setText("TANGGAL");

        jLabel76.setText("USER");

        jLabel77.setText("SUPPLIER");

        jLabel78.setText("HARGA BELI");

        jLabel79.setText("JUMLAH");

        jLabel85.setText("TOTAL HARGA");

        totalHargaBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalHargaBeliActionPerformed(evt);
            }
        });

        JumlahBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JumlahBeliActionPerformed(evt);
            }
        });

        hargaBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaBeliActionPerformed(evt);
            }
        });

        cbSuppBeli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSuppBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSuppBeliActionPerformed(evt);
            }
        });

        buttonSimpanBeli.setBackground(new java.awt.Color(222, 160, 87));
        buttonSimpanBeli.setText("Simpan");
        buttonSimpanBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSimpanBeliActionPerformed(evt);
            }
        });

        jLabel98.setText("OBAT");

        cbObatBeli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbObatBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbObatBeliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TambahPembelianLayout = new javax.swing.GroupLayout(TambahPembelian);
        TambahPembelian.setLayout(TambahPembelianLayout);
        TambahPembelianLayout.setHorizontalGroup(
            TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TambahPembelianLayout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TambahPembelianLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonSimpanBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
            .addGroup(TambahPembelianLayout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel76)
                    .addComponent(jLabel55)
                    .addComponent(jLabel20)
                    .addComponent(jLabel85)
                    .addComponent(jLabel78)
                    .addComponent(jLabel77)
                    .addComponent(jLabel79)
                    .addComponent(jLabel98))
                .addGap(248, 248, 248)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TambahPembelianLayout.createSequentialGroup()
                        .addComponent(cbObatBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(209, Short.MAX_VALUE))
                    .addGroup(TambahPembelianLayout.createSequentialGroup()
                        .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tanggalBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kd_pembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSuppBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(User_beli, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        TambahPembelianLayout.setVerticalGroup(
            TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TambahPembelianLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kd_pembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tanggalBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55))
                .addGap(30, 30, 30)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(User_beli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSuppBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbObatBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98))
                .addGap(18, 18, 18)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78))
                .addGap(18, 18, 18)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79))
                .addGap(18, 18, 18)
                .addGroup(TambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel85))
                .addGap(27, 27, 27)
                .addComponent(buttonSimpanBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        mainPanelSupplier.add(TambahPembelian, "card3");

        ManageSupplier.setBackground(new java.awt.Color(224, 216, 176));

        jLabel93.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel93.setText("Manage Supplier");

        jLabel94.setText("ID SUPPLIER:");

        jLabel95.setText("NAMA PT:");

        tableSupp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tableSupp);

        buttonTambahSupp.setBackground(new java.awt.Color(222, 160, 87));
        buttonTambahSupp.setText("Tambah");
        buttonTambahSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambahSuppActionPerformed(evt);
            }
        });

        buttonHapus_Supp.setBackground(new java.awt.Color(222, 160, 87));
        buttonHapus_Supp.setText("Hapus");
        buttonHapus_Supp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapus_SuppActionPerformed(evt);
            }
        });

        jButton29.setBackground(new java.awt.Color(222, 160, 87));
        jButton29.setText("Edit");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jLabel99.setText("ALAMAT:");

        jLabel100.setText("EMAIL:");

        jLabel101.setText("NO TELEPON:");

        javax.swing.GroupLayout ManageSupplierLayout = new javax.swing.GroupLayout(ManageSupplier);
        ManageSupplier.setLayout(ManageSupplierLayout);
        ManageSupplierLayout.setHorizontalGroup(
            ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageSupplierLayout.createSequentialGroup()
                .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageSupplierLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ManageSupplierLayout.createSequentialGroup()
                                .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManageSupplierLayout.createSequentialGroup()
                                        .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(239, 239, 239)))
                                .addGap(90, 90, 90)
                                .addComponent(buttonTambahSupp, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 42, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManageSupplierLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonHapus_Supp, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
            .addGroup(ManageSupplierLayout.createSequentialGroup()
                .addGap(285, 285, 285)
                .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id_supp, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nama_pt, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alamatsupp, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailsupp, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telpsupp, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        ManageSupplierLayout.setVerticalGroup(
            ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageSupplierLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageSupplierLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nama_pt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(alamatsupp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(id_supp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageSupplierLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ManageSupplierLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(emailsupp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageSupplierLayout.createSequentialGroup()
                        .addGap(0, 40, Short.MAX_VALUE)
                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(buttonHapus_Supp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113))
                    .addGroup(ManageSupplierLayout.createSequentialGroup()
                        .addGroup(ManageSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(telpsupp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonTambahSupp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        mainPanelSupplier.add(ManageSupplier, "card4");

        javax.swing.GroupLayout SupplierLayout = new javax.swing.GroupLayout(Supplier);
        Supplier.setLayout(SupplierLayout);
        SupplierLayout.setHorizontalGroup(
            SupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupplierLayout.createSequentialGroup()
                .addComponent(menuHome7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mainPanelSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SupplierLayout.setVerticalGroup(
            SupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanelSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuHome7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainPanel.add(Supplier, "card8");

        User.setBackground(new java.awt.Color(224, 216, 176));

        menuHome6.setBackground(new java.awt.Color(222, 160, 87));

        buttonData_user.setBackground(new java.awt.Color(224, 216, 176));
        buttonData_user.setText("Data User");
        buttonData_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonData_userActionPerformed(evt);
            }
        });

        buttonUbahPass_user.setBackground(new java.awt.Color(224, 216, 176));
        buttonUbahPass_user.setText("Ubah Password");
        buttonUbahPass_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUbahPass_userActionPerformed(evt);
            }
        });

        buttonHome_user.setBackground(new java.awt.Color(224, 216, 176));
        buttonHome_user.setText("Home");
        buttonHome_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHome_userActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuHome6Layout = new javax.swing.GroupLayout(menuHome6);
        menuHome6.setLayout(menuHome6Layout);
        menuHome6Layout.setHorizontalGroup(
            menuHome6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuHome6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(menuHome6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonData_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonUbahPass_user, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(buttonHome_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        menuHome6Layout.setVerticalGroup(
            menuHome6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuHome6Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(buttonHome_user, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(buttonData_user, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(buttonUbahPass_user, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanelUser.setLayout(new java.awt.CardLayout());

        BioUser.setBackground(new java.awt.Color(224, 216, 176));

        jLabel80.setText("No Telepon");

        jLabel81.setText("Nama");

        jLabel82.setText("Email");

        jLabel83.setText("Jenis Kelamin");

        jLabel84.setText("Id Karyawan");

        jLabel86.setText("Id User");

        jLabel87.setText("Kewarganegaraan");

        jLabel88.setText("Status");

        jLabel89.setText("Pendidikan Terakhir");

        jLabel90.setText("Alamat");

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        txtJK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJKActionPerformed(evt);
            }
        });

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        txtNotlp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotlpActionPerformed(evt);
            }
        });

        txtWarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWargaActionPerformed(evt);
            }
        });

        txtStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusActionPerformed(evt);
            }
        });

        txtPendidikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPendidikanActionPerformed(evt);
            }
        });

        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });

        jLabel91.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel91.setText("Biodata User");

        javax.swing.GroupLayout BioUserLayout = new javax.swing.GroupLayout(BioUser);
        BioUser.setLayout(BioUserLayout);
        BioUserLayout.setHorizontalGroup(
            BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BioUserLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203))
            .addGroup(BioUserLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(BioUserLayout.createSequentialGroup()
                        .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtJK, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BioUserLayout.createSequentialGroup()
                        .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BioUserLayout.createSequentialGroup()
                        .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BioUserLayout.createSequentialGroup()
                        .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BioUserLayout.createSequentialGroup()
                        .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNotlp, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWarga, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPendidikan, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        BioUserLayout.setVerticalGroup(
            BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BioUserLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel91)
                .addGap(35, 35, 35)
                .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNotlp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BioUserLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtWarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(BioUserLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BioUserLayout.createSequentialGroup()
                        .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BioUserLayout.createSequentialGroup()
                                .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPendidikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtJK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(BioUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(244, Short.MAX_VALUE))
        );

        mainPanelUser.add(BioUser, "card2");

        UbahPass.setBackground(new java.awt.Color(224, 216, 176));

        jLabel68.setText("Password Lama");

        jLabel69.setText("Password Baru");

        jLabel70.setText("Password Baru");

        txtPassBaru2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassBaru2ActionPerformed(evt);
            }
        });

        jLabel92.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel92.setText("Ubah Password Akun");

        buttonSimpan_pass.setBackground(new java.awt.Color(222, 160, 87));
        buttonSimpan_pass.setText("Simpan");
        buttonSimpan_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSimpan_passActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UbahPassLayout = new javax.swing.GroupLayout(UbahPass);
        UbahPass.setLayout(UbahPassLayout);
        UbahPassLayout.setHorizontalGroup(
            UbahPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UbahPassLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(UbahPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(UbahPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPassLama, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassBaru2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(275, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UbahPassLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(UbahPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UbahPassLayout.createSequentialGroup()
                        .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(256, 256, 256))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UbahPassLayout.createSequentialGroup()
                        .addComponent(buttonSimpan_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(196, 196, 196))))
        );
        UbahPassLayout.setVerticalGroup(
            UbahPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UbahPassLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel92)
                .addGap(54, 54, 54)
                .addGroup(UbahPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassLama, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(UbahPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(UbahPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassBaru2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(buttonSimpan_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
        );

        mainPanelUser.add(UbahPass, "card3");

        javax.swing.GroupLayout UserLayout = new javax.swing.GroupLayout(User);
        User.setLayout(UserLayout);
        UserLayout.setHorizontalGroup(
            UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserLayout.createSequentialGroup()
                .addComponent(menuHome6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mainPanelUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        UserLayout.setVerticalGroup(
            UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuHome6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanelUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainPanel.add(User, "card7");

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelnama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addComponent(panelnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(Panel, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonHome_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHome_homeActionPerformed
        // TODO add your handling code here:
         mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(Home);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonHome_homeActionPerformed

    private void buttonBrg_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBrg_homeActionPerformed
        // TODO add your handling code here:
         mainPanel.removeAll();
       mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(dataBarang);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonBrg_homeActionPerformed

    private void buttonKyw_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonKyw_homeActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(dataKaryawan);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonKyw_homeActionPerformed

    private void buttonKasir_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonKasir_homeActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(Kasir);
        mainPanel.repaint();
        mainPanel.revalidate();
        autonumbertr();
    }//GEN-LAST:event_buttonKasir_homeActionPerformed

    private void buttonLap_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLap_homeActionPerformed
        // TODO add your handling code here: Panel.removeAll();
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(Laporan);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonLap_homeActionPerformed

    private void buttonUser_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUser_homeActionPerformed
        // TODO add your handling code here: Panel.removeAll();
         mainPanel.removeAll();
       mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(User);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonUser_homeActionPerformed

    private void buttonBrg_brgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBrg_brgActionPerformed
        // TODO add your handling code here:
        mainPanelBrg.removeAll();
        mainPanelBrg.repaint();
        mainPanelBrg.revalidate();
        
       mainPanelBrg.add(Barang);
        mainPanelBrg.repaint();
        mainPanelBrg.revalidate();
    }//GEN-LAST:event_buttonBrg_brgActionPerformed

    private void buttonInsert_brgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsert_brgActionPerformed
        // TODO add your handling code here:
        mainPanelBrg.removeAll();
        mainPanelBrg.repaint();
        mainPanelBrg.revalidate();
        
       mainPanelBrg.add(Insert);
        mainPanelBrg.repaint();
        mainPanelBrg.revalidate();
        autonumberBrg();
        loadDataBrg();
    }//GEN-LAST:event_buttonInsert_brgActionPerformed

    private void buttonJenis_brgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonJenis_brgActionPerformed
        // TODO add your handling code here:
        mainPanelBrg.removeAll();
        mainPanelBrg.repaint();
        mainPanelBrg.revalidate();
        
       mainPanelBrg.add(ManageJenis);
        mainPanelBrg.repaint();
        mainPanelBrg.revalidate();
        autonumberJenis();
    }//GEN-LAST:event_buttonJenis_brgActionPerformed

    private void buttonSatuan_brgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSatuan_brgActionPerformed
        // TODO add your handling code here:
        mainPanelBrg.removeAll();
        mainPanelBrg.repaint();
        mainPanelBrg.revalidate();
        
       mainPanelBrg.add(ManageSatuan);
        mainPanelBrg.repaint();
        mainPanelBrg.revalidate();
        autonumberSatuan();
    }//GEN-LAST:event_buttonSatuan_brgActionPerformed

    private void buttonHome_brgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHome_brgActionPerformed
        // TODO add your handling code here:
         mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
       mainPanel.add(Home);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonHome_brgActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
    
        try {
            int baris = tabelBarang.getRowCount();
            
            for (int i = 0; i <baris ; i++){
            
            Connection c = connect.getConnection();
            String sql = "UPDATE tb_barang SET nama_obat = '"+ tabelBarang.getValueAt(i, 1) +"', jenis =  '"+ tabelBarang.getValueAt(i, 2) +"' , satuan =  '"+ tabelBarang.getValueAt(i, 3) +"', stok =  '"+ tabelBarang.getValueAt(i, 4) +"', harga_beli =  '"+ tabelBarang.getValueAt(i, 5) +"', harga_jual =  '"+ tabelBarang.getValueAt(i, 6) +"' WHERE tb_barang.kd_obat = '"+ tabelBarang.getValueAt(i, 0) +"'";
            
           PreparedStatement pst = c.prepareStatement(sql);
            pst.execute();
            pst.close();
            
            }JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {
            System.out.println("update error" + e);
        }finally{
            tampilkan_brg();
        }       
    }//GEN-LAST:event_jButton19ActionPerformed

    private void buttonHapus_brgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHapus_brgActionPerformed
        // TODO add your handling code here:
         int i = tabelBarang.getSelectedRow();
        if (i == -1) {
            return;
        }
        
        String kd_obat = (String) tabelBarang.getValueAt(i, 0);
        
        int pernyataan = JOptionPane.showConfirmDialog(null, "Yakin Data Akan Dihapus","Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (pernyataan== JOptionPane.OK_OPTION) {
            try {
                Connection en = connect.getConnection();
                String sql = "DELETE FROM tb_barang WHERE kd_obat = ?";
                PreparedStatement p = en.prepareStatement(sql);
                p.setString(1, kd_obat);
                p.executeUpdate();
                p.close();
                JOptionPane.showMessageDialog(null, "Data Terhapus");
            } catch (Exception e) {
                System.out.println("Terjadi Kesalahan");
            }finally{
                buttonHapus_brg.setEnabled(true);
                tampilkan_brg();
               
            }
        }
    }//GEN-LAST:event_buttonHapus_brgActionPerformed

    private void buttonKyw_kywActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonKyw_kywActionPerformed
        // TODO add your handling code here:
        mainPanelKyw.removeAll();
        mainPanelKyw.repaint();
        mainPanelKyw.revalidate();
        
       mainPanelKyw.add(Karyawan);
        mainPanelKyw.repaint();
        mainPanelKyw.revalidate();
    }//GEN-LAST:event_buttonKyw_kywActionPerformed

    private void buttonManageAkun_kywActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonManageAkun_kywActionPerformed
        // TODO add your handling code here:
        mainPanelKyw.removeAll();
        mainPanelKyw.repaint();
        mainPanelKyw.revalidate();
        
       mainPanelKyw.add(BuatAkun);
        mainPanelKyw.repaint();
        mainPanelKyw.revalidate();
        autonumberUser();
    }//GEN-LAST:event_buttonManageAkun_kywActionPerformed

    private void buttonInsert_kywActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsert_kywActionPerformed
        // TODO add your handling code here:
        mainPanelKyw.removeAll();
        mainPanelKyw.repaint();
        mainPanelKyw.revalidate();
        
       mainPanelKyw.add(InsertKaryawan);
        mainPanelKyw.repaint();
        mainPanelKyw.revalidate();
         autonumberKyw();
         loadDataKyw();
    }//GEN-LAST:event_buttonInsert_kywActionPerformed

    private void buttonHome_kywActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHome_kywActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
       mainPanel.add(Home);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonHome_kywActionPerformed

    private void buttonGaji_kywActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGaji_kywActionPerformed
        // TODO add your handling code here:mainPanelKyw.removeAll();
        mainPanelKyw.removeAll();
        mainPanelKyw.repaint();
        mainPanelKyw.revalidate();
        
       mainPanelKyw.add(Gaji);
        mainPanelKyw.repaint();
        mainPanelKyw.revalidate();
    }//GEN-LAST:event_buttonGaji_kywActionPerformed

    private void buttonHapus_kywActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHapus_kywActionPerformed
        // TODO add your handling code here:
         int i = tabelKyw.getSelectedRow();
        if (i == -1) {
            return;
        }
        
        String id_kyw= (String) tabelKyw.getValueAt(i, 0);
        
        int pernyataan = JOptionPane.showConfirmDialog(null, "Yakin Data Akan Dihapus","Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (pernyataan== JOptionPane.OK_OPTION) {
            try {
                Connection en = connect.getConnection();
                String sql = "DELETE FROM tb_karyawan WHERE id_karyawan = ?";
                PreparedStatement p = en.prepareStatement(sql);
                p.setString(1, id_kyw);
                p.executeUpdate();
                p.close();
                JOptionPane.showMessageDialog(null, "Data Terhapus");
            } catch (Exception e) {
                System.out.println("Terjadi Kesalahan");
            }finally{
                buttonHapus_kyw.setEnabled(true);
                tampilkan_kyw();
               
            }
        }
    }//GEN-LAST:event_buttonHapus_kywActionPerformed

    private void id_karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_karyawanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_karyawanActionPerformed

    private void perempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_perempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_perempuanActionPerformed

    private void lakiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lakiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lakiActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void no_tlpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_no_tlpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_no_tlpActionPerformed

    private void kewarganegaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kewarganegaraanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kewarganegaraanActionPerformed

    private void pendidikan_terakhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendidikan_terakhirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pendidikan_terakhirActionPerformed

    private void alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamatActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        try {
            String buttonGroup1 = null;
            if(perempuan.isSelected()){
                buttonGroup1="perempuan";
            }else if(laki.isSelected()){
                buttonGroup1="laki";
            }
          
            Connection cn = connect.getConnection();
            cn.createStatement().executeUpdate("insert into tb_karyawan values ('"
                +id_karyawan.getText()
                +"','"+cbUser.getSelectedItem()
                +"','"+nama_karyawan.getText()
                +"','"+buttonGroup1   
                +"','"+email.getText()
                +"','"+status.getText()
                +"','"+no_tlp.getText()
                +"','"+kewarganegaraan.getText()
                +"','"+pendidikan_terakhir.getText()
                +"','"+alamat.getText()
                +"','"+gaji_pokok.getText()+"')");
            
            JOptionPane.showMessageDialog(null,"Data Berhasil Ditambahkan");
            tampilkan_kyw();
            loadDataKyw();
            buttonGroup1=null;
            email.setText("");
            status.setText("");
            no_tlp.setText("");
            kewarganegaraan.setText("");
            alamat.setText("");
            gaji_pokok.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Data Gagal Ditambahkan");
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void txID_KywActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txID_KywActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txID_KywActionPerformed

    private void buttonHitungGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHitungGajiActionPerformed
        // TODO add your handling code here:
          int Gaji_pokok = Integer.valueOf(txGaji_Pokok.getText());
        int Lembur = Integer.valueOf(txJumlahLembur.getText());
        int Bonus = Integer.valueOf(txBonus.getText());
        int Asuransi = Integer.valueOf(txAsuransi.getText());
        int Pemotongan = Integer.valueOf(txPemotongan.getText());
        Lembur = 2*15000*Lembur;
        
        int Gaji_bersih = (Gaji_pokok+Lembur+Bonus)-(Asuransi+Pemotongan);
        txGajiBersih.setText(String.valueOf(Gaji_bersih));
        
    }//GEN-LAST:event_buttonHitungGajiActionPerformed

    private void buttonTransaksi_trActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTransaksi_trActionPerformed
        // TODO add your handling code here:
        mainPanelTr.removeAll();
        mainPanelTr.repaint();
        mainPanelTr.revalidate();
        
       mainPanelTr.add(Transaksi);
        mainPanelTr.repaint();
        mainPanelTr.revalidate();
        autonumbertr();
    }//GEN-LAST:event_buttonTransaksi_trActionPerformed

    private void buttonHome_trActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHome_trActionPerformed
        // TODO add your handling code here:
           mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
       mainPanel.add(Home);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonHome_trActionPerformed

    private void txNoTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txNoTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txNoTransaksiActionPerformed

    private void enterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterActionPerformed
        // TODO add your handling code here:  String KodeObat = txKode_obat.getText();
        
        try{
            Connection c = connect.getConnection();
            String query = "SELECT kd_obat, nama_obat, harga_jual FROM tb_barang where kd_obat='"+txID_Barang.getText()+"'";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()){
                txID_Barang.setText(rs.getString("kd_obat"));
                txNamaObat.setText(rs.getString("nama_obat"));
                txHarga.setText(rs.getString("harga_jual"));
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
             
      
    }//GEN-LAST:event_enterActionPerformed

    private void txHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txHargaActionPerformed

    private void txNamaObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txNamaObatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txNamaObatActionPerformed

    private void txJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txJumlahActionPerformed

    private void txTotalBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txTotalBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txTotalBayarActionPerformed

    private void txBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txBayarActionPerformed
        // TODO add your handling code here:
          int total, bayar, kembalian;
        total = Integer.valueOf(txTotalBayar.getText());
        bayar = Integer.valueOf(txBayar.getText());
        
        if(total > bayar){
            JOptionPane.showMessageDialog(null, "uang tidak cukup untuk melakukan pembayaran");
        } else {
            kembalian = bayar - total;
            txKembalian.setText(String.valueOf(kembalian));
        }
    }//GEN-LAST:event_txBayarActionPerformed

    private void txKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txKembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txKembalianActionPerformed

    private void txTampilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txTampilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txTampilActionPerformed

    private void buttonSimpan_KasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSimpan_KasirActionPerformed
        // TODO add your handling code here:
     
      try{
            Connection c = connect.getConnection();
            String sql = "INSERT INTO tb_transaksi VALUES(?,?,?,?,?,?,?)";
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1,txNoTransaksi.getText());
            p.setString(2, txTanggal.getText());
            p.setString(3, txtId_user.getText());
            p.setString(4, txID_Barang.getText());
            p.setString(5, txHarga.getText());
            p.setString(6, txJumlah.getText());
            p.setString(7, txTotalBayar.getText());
            p.executeUpdate();
            p.close();
            
           
            JOptionPane.showMessageDialog(null,"Transaksi Berhasil");
            
        }catch(Exception e){
            System.out.println(e);
        }
        clear();
        
            utama();
       
        autonumbertr();
        kosong();
        txTampil.setText("Rp 0");                                     

    }//GEN-LAST:event_buttonSimpan_KasirActionPerformed

    private void buttonLapBrg_lapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLapBrg_lapActionPerformed
        // TODO add your handling code here:
        mainPanelLap.removeAll();
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
        
       mainPanelLap.add(LapBarang);
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
    }//GEN-LAST:event_buttonLapBrg_lapActionPerformed

    private void buttonLapJual_lapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLapJual_lapActionPerformed
        // TODO add your handling code here:
        mainPanelLap.removeAll();
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
        
       mainPanelLap.add(LapPenjualan);
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
    }//GEN-LAST:event_buttonLapJual_lapActionPerformed

    private void buttonHome_lapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHome_lapActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
       mainPanel.add(Home);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonHome_lapActionPerformed

    private void buttonLapBeli_lapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLapBeli_lapActionPerformed
        // TODO add your handling code here:
        mainPanelLap.removeAll();
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
        
       mainPanelLap.add(LapPembelian);
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
    }//GEN-LAST:event_buttonLapBeli_lapActionPerformed

    private void buttonKas_lapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonKas_lapActionPerformed
        // TODO add your handling code here:
        mainPanelLap.removeAll();
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
        
       mainPanelLap.add(KasBesar);
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
    }//GEN-LAST:event_buttonKas_lapActionPerformed

    private void buttonLapUtang_lapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLapUtang_lapActionPerformed
        // TODO add your handling code here:
        mainPanelLap.removeAll();
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
        
       mainPanelLap.add(LapUtang);
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
    }//GEN-LAST:event_buttonLapUtang_lapActionPerformed

    private void buttonData_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonData_userActionPerformed
        // TODO add your handling code here:
         mainPanelUser.removeAll();
        mainPanelUser.repaint();
        mainPanelUser.revalidate();
        
       mainPanelUser.add(BioUser);
        mainPanelUser.repaint();
        mainPanelUser.revalidate();
    }//GEN-LAST:event_buttonData_userActionPerformed

    private void buttonUbahPass_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUbahPass_userActionPerformed
        // TODO add your handling code here:
        mainPanelUser.removeAll();
        mainPanelUser.repaint();
        mainPanelUser.revalidate();
        
       mainPanelUser.add(UbahPass);
        mainPanelUser.repaint();
        mainPanelUser.revalidate();
    }//GEN-LAST:event_buttonUbahPass_userActionPerformed

    private void buttonHome_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHome_userActionPerformed
        // TODO add your handling code here:
        
         mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
       mainPanel.add(Home);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonHome_userActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtJKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJKActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNotlpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotlpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNotlpActionPerformed

    private void txtWargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWargaActionPerformed

    private void txtStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusActionPerformed

    private void txtPendidikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPendidikanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPendidikanActionPerformed

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void txtPassBaru2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassBaru2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassBaru2ActionPerformed

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserActionPerformed

    private void ButtonLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonLoginMouseClicked
        // TODO add your handling code here:
       Login();
    }//GEN-LAST:event_ButtonLoginMouseClicked

    private void ButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLoginActionPerformed
        // TODO add your handling code here:

   }//GEN-LAST:event_ButtonLoginActionPerformed

    private void buttonCari_BrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCari_BrgActionPerformed
        // TODO add your handling code here:
        String key= txtCariBrg.getText();
    System.out.println(key);
    if(key!=""){
        cariBrg(key);
    }
    else{
        tampilkan_brg();
    }
    }//GEN-LAST:event_buttonCari_BrgActionPerformed

    private void txtCariBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariBrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariBrgActionPerformed

    public PreparedStatement pst;
    private void buttonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSimpanActionPerformed
        // TODO add your handling code here:
         try{
            Connection conn = connect.getConnection();
            String SQL ="insert into tb_barang values('"
                +kd_obat.getText()
                +"','"+nama_obat.getText()
                +"','"+ (String) cbJenis.getSelectedItem()
                +"','"+ (String) cbSatuan.getSelectedItem()
                +"','"+stok.getText()
                +"','"+harga.getText()
                +"','"+hargaJual.getText()+"')";
            String kdObat=kd_obat.getText();
            pst=conn.prepareStatement(SQL);
            pst.execute();
             JOptionPane.showMessageDialog(null,"Data Berhasil Ditambahkan");
             tampilkan_brg();
            createQRcode QR = new createQRcode(kdObat);
             nama_obat.setText("");
             loadDataBrg();
             stok.setText("");
             harga.setText("");
             hargaJual.setText("");
        }
             catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Data Gagal Ditambahkan");
    }                                            

    }//GEN-LAST:event_buttonSimpanActionPerformed

    private void hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaActionPerformed

    private void hargaJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaJualActionPerformed

    private void cbSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSatuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSatuanActionPerformed

    private void buttonTambah_SatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTambah_SatuanActionPerformed
        // TODO add your handling code here:
         try {
            
            Connection conn = connect.getConnection();
            String SQL ="insert into tb_satuan values('"
                 +id_satuan.getText()
                +"','"+satuan.getText()+"')";
            pst=conn.prepareStatement(SQL);
            pst.execute();
             JOptionPane.showMessageDialog(null,"Data Berhasil Ditambahkan");
             tampilkan_satuan();
             satuan.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Data Gagal Ditambahkan");
        }
    

    }//GEN-LAST:event_buttonTambah_SatuanActionPerformed

    private void buttonTambahJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTambahJenisActionPerformed
        // TODO add your handling code here:
         try {
            
            Connection conn = connect.getConnection();
            String SQL ="insert into tb_jenis values('"
                 +id_jenis.getText()
                +"','"+jenis.getText()+"')";
            pst=conn.prepareStatement(SQL);
            pst.execute();
             JOptionPane.showMessageDialog(null,"Data Berhasil Ditambahkan");
            tampilkan_jenis();
            jenis.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Data Gagal Ditambahkan");
        }
    }//GEN-LAST:event_buttonTambahJenisActionPerformed

    private void buttonHapus_jnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHapus_jnsActionPerformed
        // TODO add your handling code here:
           int i = tableJenis.getSelectedRow();
        if (i == -1) {
            return;
        }
        
        String id_jenis = (String) tableJenis.getValueAt(i, 0);
        
        int pernyataan = JOptionPane.showConfirmDialog(null, "Yakin Data Akan Dihapus","Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (pernyataan== JOptionPane.OK_OPTION) {
            try {
                Connection en = connect.getConnection();
                String sql = "DELETE FROM tb_jenis WHERE id_jenis = ?";
                PreparedStatement p = en.prepareStatement(sql);
                p.setString(1, id_jenis);
                p.executeUpdate();
                p.close();
                JOptionPane.showMessageDialog(null, "Data Terhapus");
            } catch (Exception e) {
                System.out.println("Terjadi Kesalahan");
            }finally{
                buttonHapus_jns.setEnabled(true);
                tampilkan_jenis();
               
            }
        }
    }//GEN-LAST:event_buttonHapus_jnsActionPerformed

    private void buttonHapus_satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHapus_satuanActionPerformed
        // TODO add your handling code here:
         int i = tableSatuan.getSelectedRow();
        if (i == -1) {
            return;
        }
        
        String id_satuan= (String) tableSatuan.getValueAt(i, 0);
        
        int pernyataan = JOptionPane.showConfirmDialog(null, "Yakin Data Akan Dihapus","Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (pernyataan== JOptionPane.OK_OPTION) {
            try {
                Connection en = connect.getConnection();
                String sql = "DELETE FROM tb_satuan WHERE id_satuan = ?";
                PreparedStatement p = en.prepareStatement(sql);
                p.setString(1, id_satuan);
                p.executeUpdate();
                p.close();
                JOptionPane.showMessageDialog(null, "Data Terhapus");
            } catch (Exception e) {
                System.out.println("Terjadi Kesalahan");
            }finally{
                buttonHapus_satuan.setEnabled(true);
                tampilkan_satuan();
               
            }
        }
    }//GEN-LAST:event_buttonHapus_satuanActionPerformed

    private void buttonCari_kywActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCari_kywActionPerformed
        // TODO add your handling code here:
         String key= txtCari_kyw.getText();
    System.out.println(key);
    if(key!=""){
        carikyw(key);
    }
    else{
        tampilkan_kyw();
    }
    }//GEN-LAST:event_buttonCari_kywActionPerformed

    private void buttonTambah_akunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTambah_akunActionPerformed
        // TODO add your handling code here:
        
        try {
            String pass = password.getText();
            String hash= Encryp.MD5(pass);
            Connection conn = connect.getConnection();
            String SQL ="insert into tb_user values('"
                 +id_user.getText()
                 +"','"+username.getText()
                 +"','"+hash
                +"','"+level.getText()+"')";
            pst=conn.prepareStatement(SQL);
            pst.execute();
             JOptionPane.showMessageDialog(null,"Data Berhasil Ditambahkan");
             tampilkan_user();
             username.setText("");
             password.setText("");
             level.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Data Gagal Ditambahkan");
        }
    }//GEN-LAST:event_buttonTambah_akunActionPerformed

    private void buttonHapus_akunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHapus_akunActionPerformed
        // TODO add your handling code here:
        int i = tableUser.getSelectedRow();
        if (i == -1) {
            return;
        }
        
        String id_user= (String) tableUser.getValueAt(i, 0);
        
        int pernyataan = JOptionPane.showConfirmDialog(null, "Yakin Data Akan Dihapus","Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (pernyataan== JOptionPane.OK_OPTION) {
            try {
                Connection en = connect.getConnection();
                String sql = "DELETE FROM tb_user WHERE id_user = ?";
                PreparedStatement p = en.prepareStatement(sql);
                p.setString(1, id_user);
                p.executeUpdate();
                p.close();
                JOptionPane.showMessageDialog(null, "Data Terhapus");
            } catch (Exception e) {
                System.out.println("Terjadi Kesalahan");
            }finally{
                buttonHapus_akun.setEnabled(true);
                tampilkan_user();
               
            }
        }
    }//GEN-LAST:event_buttonHapus_akunActionPerformed

    private void cbJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJenisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbJenisActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void txtuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtuserActionPerformed

    private void txID_BarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txID_BarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txID_BarangActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        tambahTransaksi();
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        // TODO add your handling code here:
        scanQRcode scan = new scanQRcode();
        scan.setVisible(true);
    }//GEN-LAST:event_jButton46ActionPerformed

    private void buttonSimpan_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSimpan_passActionPerformed
        // TODO add your handling code here:
        String user = txtuser.getText();
            String pass_baru = String.valueOf(txtPassBaru.getPassword());
            String pass_baru2 = String.valueOf(txtPassBaru2.getPassword());
        String pass_lama = String.valueOf(txtPassLama.getPassword());
       
        try {
            Connection c = (Connection) connect.getConnection();
            java.sql.Statement state = c.createStatement();
            @SuppressWarnings("deprecation")
            String query = "SELECT * FROM tb_user WHERE  Username= '" + user + "'";
            ResultSet rs = state.executeQuery(query);
            if (rs.next()) {
                String pass_db = rs.getString("Password");
                if (pass_db.equalsIgnoreCase(Encryp.MD5(pass_lama))) { 
                    if(pass_baru.equals("")||pass_baru2.equals("")){
                        JOptionPane.showMessageDialog(null,"Masukkan Password Baru");
                        
                    }
                    else if(pass_baru.equals(pass_baru2)){
                    try{
                        String hashPass= Encryp.MD5(pass_baru2);
                           String SQL ="UPDATE tb_user SET Password='"+hashPass+"'";
                            pst=c.prepareStatement(SQL);
                            pst.execute();
                            JOptionPane.showMessageDialog(null,"Perubahan berhasil Ditambahkan");
                            txtPassLama.setText("");
                    txtPassBaru.setText("");
                    txtPassBaru2.setText("");
                    }catch(SQLException ex){
                         JOptionPane.showMessageDialog(null,"Perubahan Gagal Ditambahkan");
                    }
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Password Baru tidak sama");
                        txtPassBaru.setText("");
                        txtPassBaru2.setText("");
                    }
                } 
                else {
                    JOptionPane.showMessageDialog(rootPane, "Password Salah");
                    txtPassLama.setText("");
                    txtPassBaru.setText("");
                    txtPassBaru2.setText("");
                }
            }
        }
             catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Pengubahan Password Gagal");
             }                              

    }//GEN-LAST:event_buttonSimpan_passActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
        // TODO add your handling code here:
          String key= txCariLapBrg.getText();
    System.out.println(key);
    if(key!=""){
        cariLapBrg(key);
    }
    else{
       tampilkan_lapBrg();
    }
    }//GEN-LAST:event_jButton59ActionPerformed

    private void txtId_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtId_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtId_userActionPerformed

    private void buttonCariLapJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariLapJualActionPerformed
        // TODO add your handling code here:
              String key= txtCariLapJual.getText();
    System.out.println(key);
    if(key!=""){
        cariLapJual(key);
    }
    else{
       tampilkan_lapJual();
    }
    }//GEN-LAST:event_buttonCariLapJualActionPerformed

    private void buttonSupplier_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSupplier_homeActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
       mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(Supplier);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonSupplier_homeActionPerformed

    private void buttonDataBeli_suppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDataBeli_suppActionPerformed
        // TODO add your handling code here:
         mainPanelSupplier.removeAll();
        mainPanelSupplier.repaint();
        mainPanelSupplier.revalidate();
        
       mainPanelSupplier.add(Pembelian);
        mainPanelSupplier.repaint();
        mainPanelSupplier.revalidate();
    }//GEN-LAST:event_buttonDataBeli_suppActionPerformed

    private void buttonHome_suppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHome_suppActionPerformed
        // TODO add your handling code here:
          mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
       mainPanel.add(Home);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_buttonHome_suppActionPerformed

    private void buttonTambahBeli_suppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTambahBeli_suppActionPerformed
        // TODO add your handling code here:
             mainPanelSupplier.removeAll();
        mainPanelSupplier.repaint();
        mainPanelSupplier.revalidate();
        
       mainPanelSupplier.add(TambahPembelian);
        mainPanelSupplier.repaint();
        mainPanelSupplier.revalidate();
        autonumberBeli();
        loadDataBeli();
    }//GEN-LAST:event_buttonTambahBeli_suppActionPerformed

    private void buttonManage_suppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonManage_suppActionPerformed
        // TODO add your handling code here:
             mainPanelSupplier.removeAll();
        mainPanelSupplier.repaint();
        mainPanelSupplier.revalidate();
        
       mainPanelSupplier.add(ManageSupplier);
        mainPanelSupplier.repaint();
        mainPanelSupplier.revalidate();
        autonumberSupp();
    }//GEN-LAST:event_buttonManage_suppActionPerformed

    private void JumlahBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JumlahBeliActionPerformed
        // TODO add your handling code here:
        int harga= Integer.parseInt(hargaBeli.getText());
        int jumlah =Integer.parseInt(JumlahBeli.getText());
        int hasil= harga* jumlah;
        
        totalHargaBeli.setText(String.valueOf(hasil));
    }//GEN-LAST:event_JumlahBeliActionPerformed

    private void hargaBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaBeliActionPerformed

    private void cbSuppBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSuppBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSuppBeliActionPerformed

    private void buttonSimpanBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSimpanBeliActionPerformed
        // TODO add your handling code here:
        try {
            Connection cn = connect.getConnection();
            cn.createStatement().executeUpdate("insert into tb_pembelian values ('"
                +kd_pembelian.getText()
                +"','"+tanggalBeli.getText()
                +"','"+User_beli.getText()
                +"','"+cbSuppBeli.getSelectedItem() 
                +"','"+cbObatBeli.getSelectedItem() 
                +"','"+hargaBeli.getText()
                +"','"+JumlahBeli.getText()
                +"','"+totalHargaBeli.getText()+"')");
            
            JOptionPane.showMessageDialog(null,"Data Berhasil Ditambahkan");
            tampilkan_pembelian();
            tanggalBeli.setText("");
            User_beli.setText("");
            loadDataBeli();
            hargaBeli.setText("");
            JumlahBeli.setText("");
            totalHargaBeli.setText("");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Data Gagal Ditambahkan");
        }
        
    }//GEN-LAST:event_buttonSimpanBeliActionPerformed

    private void buttonTambahSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTambahSuppActionPerformed
        // TODO add your handling code here:
        try {
            
            Connection conn = connect.getConnection();
            String SQL ="insert into tb_supplier values('"
                 +id_supp.getText()
                 +"','"+nama_pt.getText()
                 +"','"+alamatsupp.getText()
                +"','"+emailsupp.getText()
                +"','"+telpsupp.getText()+"')";
            pst=conn.prepareStatement(SQL);
            pst.execute();
             JOptionPane.showMessageDialog(null,"Data Berhasil Ditambahkan");
            tampilkan_Supp();
             nama_pt.setText("");
             alamatsupp.setText("");
             emailsupp.setText("");
             telpsupp.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Data Gagal Ditambahkan");
        }
    }//GEN-LAST:event_buttonTambahSuppActionPerformed

    private void buttonHapus_SuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHapus_SuppActionPerformed
        // TODO add your handling code here:
         int i = tableSupp.getSelectedRow();
        if (i == -1) {
            return;
        }
        
        String id_jenis = (String) tableSupp.getValueAt(i, 0);
        
        int pernyataan = JOptionPane.showConfirmDialog(null, "Yakin Data Akan Dihapus","Konfirmasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (pernyataan== JOptionPane.OK_OPTION) {
            try {
                Connection en = connect.getConnection();
                String sql = "DELETE FROM tb_supplier WHERE id_supplier= ?";
                PreparedStatement p = en.prepareStatement(sql);
                p.setString(1, id_jenis);
                p.executeUpdate();
                p.close();
                JOptionPane.showMessageDialog(null, "Data Terhapus");
            } catch (Exception e) {
                System.out.println("Terjadi Kesalahan");
            }finally{
                buttonHapus_Supp.setEnabled(true);
                tampilkan_Supp();
               
            }
        }
    }//GEN-LAST:event_buttonHapus_SuppActionPerformed

    private void txAsuransiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txAsuransiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txAsuransiActionPerformed

    private void txPemotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txPemotonganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txPemotonganActionPerformed

    private void buttonCariGajiKywActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariGajiKywActionPerformed
        // TODO add your handling code here:
              txID_Kyw.getText();
    System.out.println(txID_Kyw.getText());
    if(txID_Kyw.getText()!=""){
        try{
            Connection c = connect.getConnection();
                String query = "SELECT*FROM tb_karyawan where id_karyawan='"+txID_Kyw.getText()+"'";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next()){
                    txNama_kyw.setText(rs.getString("nama_karyawan"));
                    txStatus_Kyw.setText(rs.getString("status"));
                    txGaji_Pokok.setText(rs.getString("gaji_pokok"));
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Pencarian Gagal");
        }
    }
    else{
        clear3();
    }
        
    }//GEN-LAST:event_buttonCariGajiKywActionPerformed

    private void gaji_pokokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gaji_pokokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gaji_pokokActionPerformed

    private void buttonSimpanGajiBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSimpanGajiBersihActionPerformed
        // TODO add your handling code here:
         try {
            java.sql.Connection c = connect.getConnection();
//           
            String sql = "INSERT INTO `gaji_karyawan` (`id_karyawan`, `nama_karyawan`, `status`, `gaji_pokok`, `bonus`, `lembur`, `asuransi`, `gaji_bersih`) VALUES ('"+txID_Kyw.getText()+"', '"+txNama_kyw.getText()+"', '"+txStatus_Kyw.getText()+"', '"+txGaji_Pokok.getText()+"', '"+txBonus.getText()+"', '"+txJumlahLembur.getText()+"', '"+txAsuransi.getText()+"', '"+txGajiBersih.getText()+"')";
            java.sql.PreparedStatement pst=c.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
            clear3();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_buttonSimpanGajiBersihActionPerformed

    private void buttonCariPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariPembelianActionPerformed
        // TODO add your handling code here:
         String key= txtCariPembelian.getText();
    System.out.println(key);
    if(key!=""){
        cariPembelian(key);
    }
    else{
        tampilkan_pembelian();
    }
    }//GEN-LAST:event_buttonCariPembelianActionPerformed

    private void buttonCariLapBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariLapBeliActionPerformed
        // TODO add your handling code here:
         String key= txCariLapBeli.getText();
    System.out.println(key);
    if(key!=""){
        cariLapBeli(key);
    }
    else{
        tampilkan_lapBeli();
    }
    }//GEN-LAST:event_buttonCariLapBeliActionPerformed

    private void cbObatBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbObatBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbObatBeliActionPerformed

    private void totalHargaBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalHargaBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalHargaBeliActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
          try {
            int baris = tableJenis.getRowCount();
            for (int i = 0; i <baris ; i++){
            Connection c = connect.getConnection();
            String sql = "UPDATE tb_jenis SET jenis = '"+ tableJenis.getValueAt(i, 1) +"' WHERE tb_jenis.id_jenis = '"+ tableJenis.getValueAt(i, 0) +"'";
            
           PreparedStatement pst = c.prepareStatement(sql);
            pst.execute();
            pst.close();
            
            }JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {
            System.out.println("update error" + e);
        }finally{
            tampilkan_jenis();
        }    
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        try {
          
            Connection cn = connect.getConnection();
            cn.createStatement().executeUpdate("insert into tb_bukukas values ('"
                +idKas.getText()
                +"','"+TanggalKas.getText()
                +"','"+cbKategori.getSelectedItem()
                +"','"+UserKas.getText()
                +"','"+debitKas.getText()
                +"','"+kreditKas.getText()
                +"','"+saldoKas.getText()
                +"','"+keteranganKas.getText()+"')");
            
            JOptionPane.showMessageDialog(null,"Data Berhasil Ditambahkan");
            tampilkan_KAS();
            TanggalKas.setText("");
            loadDataKas();
            UserKas.setText("");
            debitKas.setText("");
            kreditKas.setText("");
            saldoKas.setText("");
            keteranganKas.setText("");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Data Gagal Ditambahkan");
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void keteranganKasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keteranganKasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keteranganKasActionPerformed

    private void saldoKasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saldoKasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saldoKasActionPerformed

    private void debitKasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debitKasActionPerformed
        // TODO add your handling code here: 
        int saldo, kredit = 0, debit = 0;
         try{
            Connection c = connect.getConnection();
                String query = "SELECT*FROM tb_bukukas";
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next()){
                  debit=Integer.parseInt(rs.getString("Saldo"));
                   kredit= Integer.parseInt(rs.getString("Kredit"));
        }
               int debit_br =Integer.parseInt(debitKas.getText()) + debit;
               int kredit_br=Integer.parseInt(kreditKas.getText()) + kredit;
                saldo= debit_br - kredit_br;
                saldoKas.setText(String.valueOf(saldo));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Pencarian Gagal");
        }
        
    }//GEN-LAST:event_debitKasActionPerformed

    private void kreditKasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kreditKasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kreditKasActionPerformed

    private void idKasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idKasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idKasActionPerformed

    private void buttonTambahKasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTambahKasActionPerformed
        // TODO add your handling code here:
         mainPanelLap.removeAll();
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
        
       mainPanelLap.add(TambahKas);
        mainPanelLap.repaint();
        mainPanelLap.revalidate();
        autonumberKas();
    }//GEN-LAST:event_buttonTambahKasActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        // TODO add your handling code here:
        String key= CariLapKas.getText();
    System.out.println(key);
    if(key!=""){
        cariLapKas(key);
    }
    else{
        tampilkan_KAS();
    }
    }//GEN-LAST:event_jButton61ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        try {
            int baris = tableSatuan.getRowCount();
            for (int i = 0; i <baris ; i++){
            Connection c = connect.getConnection();
            String sql = "UPDATE tb_satuan SET satuan = '"+ tableSatuan.getValueAt(i, 1) +"' WHERE tb_satuan.id_satuan = '"+ tableSatuan.getValueAt(i, 0) +"'";
            
           PreparedStatement pst = c.prepareStatement(sql);
            pst.execute();
            pst.close();
            
            }JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {
            System.out.println("update error" + e);
        }finally{
            tampilkan_satuan();
        }  
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
        try {
            int baris = tabelKyw.getRowCount();
            
            for (int i = 0; i <baris ; i++){
            
            Connection c = connect.getConnection();
            String sql = "UPDATE tb_karyawan SET user = '"+ tabelKyw.getValueAt(i, 1) +"',nama_karyawan = '"+ tabelKyw.getValueAt(i, 2) +"', JK =  '"+ tabelKyw.getValueAt(i, 3) +"' , email =  '"+ tabelKyw.getValueAt(i, 4) +"', status =  '"+ tabelKyw.getValueAt(i, 5) +"', no_tlp =  '"+ tabelKyw.getValueAt(i, 6) +"', kewarganegaraan =  '"+ tabelKyw.getValueAt(i, 7)+"' , pendidikan =  '"+ tabelKyw.getValueAt(i, 8) +"', alamat =  '"+ tabelKyw.getValueAt(i, 9) +"' WHERE tb_karyawan.id_karyawan = '"+ tabelKyw.getValueAt(i, 0) +"'";
            
           PreparedStatement pst = c.prepareStatement(sql);
            pst.execute();
            pst.close();
            
            }JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {
            System.out.println("update error" + e);
        }finally{
            tampilkan_kyw();
        }       
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
         try {
            int baris = tableUser.getRowCount();
            
            for (int i = 0; i <baris ; i++){
            
            Connection c = connect.getConnection();
            String sql = "UPDATE tb_user SET Username = '"+ tableUser.getValueAt(i, 1) +"',Level = '"+ tableUser.getValueAt(i, 3) +"' WHERE tb_user.id_user = '"+ tableUser.getValueAt(i, 0) +"'";
            
           PreparedStatement pst = c.prepareStatement(sql);
            pst.execute();
            pst.close();
            
            }JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {
            System.out.println("update error" + e);
        }finally{
            tampilkan_user();
        }     
    }//GEN-LAST:event_jButton13ActionPerformed

    private void buttonEdit_PembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEdit_PembelianActionPerformed
        // TODO add your handling code here:
         try {
            int baris = tableSupp.getRowCount();
            
            for (int i = 0; i <baris ; i++){
            
            Connection c = connect.getConnection();
            String sql = "UPDATE tb_supplier SET nama_pt = '"+ tableSupp.getValueAt(i, 1) +"',alamat = '"+ tableSupp.getValueAt(i, 2) +"', email =  '"+ tableSupp.getValueAt(i, 3) +"' , no_tlp =  '"+ tableSupp.getValueAt(i, 4) +"' WHERE tb_supplier.id_supplier = '"+ tableSupp.getValueAt(i, 0) +"'";
            
           PreparedStatement pst = c.prepareStatement(sql);
            pst.execute();
            pst.close();
            
            }JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {
            System.out.println("update error" + e);
        }finally{
            tampilkan_Supp();
        }     
    }//GEN-LAST:event_buttonEdit_PembelianActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
         try {
            int baris = tabelKyw.getRowCount();
            
            for (int i = 0; i <baris ; i++){
            
            Connection c = connect.getConnection();
            String sql = "UPDATE tb_karyawan SET user = '"+ tabelKyw.getValueAt(i, 1) +"',nama_karyawan = '"+ tabelKyw.getValueAt(i, 2) +"', JK =  '"+ tabelKyw.getValueAt(i, 3) +"' , email =  '"+ tabelKyw.getValueAt(i, 4) +"', status =  '"+ tabelKyw.getValueAt(i, 5) +"', no_tlp =  '"+ tabelKyw.getValueAt(i, 6) +"', kewarganegaraan =  '"+ tabelKyw.getValueAt(i, 7)+"' , pendidikan =  '"+ tabelKyw.getValueAt(i, 8) +"', alamat =  '"+ tabelKyw.getValueAt(i, 9) +"' WHERE tb_karyawan.id_karyawan = '"+ tabelKyw.getValueAt(i, 0) +"'";
            
           PreparedStatement pst = c.prepareStatement(sql);
            pst.execute();
            pst.close();
            
            }JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (Exception e) {
            System.out.println("update error" + e);
        }finally{
            tampilkan_brg();
        }     
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        // TODO add your handling code here:
        try{
        Connection con = connect.getConnection();
            File namafile= new File("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Project_Apotek\\src\\Report\\penjualann.jasper");
            
            JasperPrint jprint = JasperFillManager.fillReport(namafile.getPath(), null, con);
            
            JasperViewer.viewReport(jprint);
            
        } catch (JRException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
        // TODO add your handling code here:
        try{
         Connection con = connect.getConnection();
            File namafile= new File("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Project_Apotek\\src\\Report\\barang.jasper");
            
            JasperPrint jprint = JasperFillManager.fillReport(namafile.getPath(), null, con);
            
            JasperViewer.viewReport(jprint);
            
        } catch (JRException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
        // TODO add your handling code here:
         try{
        Connection con = connect.getConnection();
            File namafile= new File("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Project_Apotek\\src\\Report\\pembelian.jasper");
            
            JasperPrint jprint = JasperFillManager.fillReport(namafile.getPath(), null, con);
            
            JasperViewer.viewReport(jprint);
            
        } catch (JRException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jButton52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton52ActionPerformed
        // TODO add your handling code here:
        try{
        Connection con = connect.getConnection();
            File namafile= new File("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Project_Apotek\\src\\Report\\utang.jasper");
            
            JasperPrint jprint = JasperFillManager.fillReport(namafile.getPath(), null, con);
            
            JasperViewer.viewReport(jprint);
            
        } catch (JRException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_jButton52ActionPerformed

    private void jButton62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton62ActionPerformed
        // TODO add your handling code here:
        try{
        Connection con = connect.getConnection();
            File namafile= new File("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Project_Apotek\\src\\Report\\bukukas.jasper");
            
            JasperPrint jprint = JasperFillManager.fillReport(namafile.getPath(), null, con);
            
            JasperViewer.viewReport(jprint);
            
        } catch (JRException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_jButton62ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new mainView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Barang;
    private javax.swing.JPanel BioUser;
    private javax.swing.JPanel BuatAkun;
    private javax.swing.JButton ButtonLogin;
    private javax.swing.JTextField CariLapKas;
    private javax.swing.JPanel Gaji;
    private javax.swing.JPanel Home;
    private javax.swing.JPanel Insert;
    private javax.swing.JPanel InsertKaryawan;
    private javax.swing.JTextField JumlahBeli;
    private javax.swing.JPanel Karyawan;
    private javax.swing.JPanel KasBesar;
    private javax.swing.JPanel Kasir;
    private javax.swing.JLabel LabelUSer;
    private javax.swing.JLabel Label_jam;
    private javax.swing.JLabel Label_tgl;
    private javax.swing.JPanel LapBarang;
    private javax.swing.JTable LapBeli;
    private javax.swing.JTable LapBeli1;
    private javax.swing.JTable LapBrg;
    private javax.swing.JTable LapKas;
    private javax.swing.JPanel LapPembelian;
    private javax.swing.JTable LapPen;
    private javax.swing.JPanel LapPenjualan;
    private javax.swing.JPanel LapUtang;
    private javax.swing.JTable LapUtangPiutang;
    private javax.swing.JPanel Laporan;
    private javax.swing.JPanel Login;
    private javax.swing.JPanel ManageJenis;
    private javax.swing.JPanel ManageSatuan;
    private javax.swing.JPanel ManageSupplier;
    private javax.swing.JPanel Panel;
    private javax.swing.JPanel Pembelian;
    private javax.swing.JPanel Supplier;
    private javax.swing.JPanel TambahKas;
    private javax.swing.JPanel TambahPembelian;
    private javax.swing.JTextField TanggalKas;
    private javax.swing.JPanel Transaksi;
    private javax.swing.JPanel UbahPass;
    private javax.swing.JPanel User;
    private javax.swing.JTextField UserKas;
    private javax.swing.JTextField User_beli;
    private javax.swing.JTextField alamat;
    private javax.swing.JTextField alamatsupp;
    private javax.swing.JButton buttonBrg_brg;
    private javax.swing.JButton buttonBrg_home;
    private javax.swing.JButton buttonCariGajiKyw;
    private javax.swing.JButton buttonCariLapBeli;
    private javax.swing.JButton buttonCariLapJual;
    private javax.swing.JButton buttonCariPembelian;
    private javax.swing.JButton buttonCari_Brg;
    private javax.swing.JButton buttonCari_kyw;
    private javax.swing.JButton buttonDataBeli_supp;
    private javax.swing.JButton buttonData_user;
    private javax.swing.JButton buttonEdit_Pembelian;
    private javax.swing.JButton buttonGaji_kyw;
    private javax.swing.JButton buttonHapus_Pembelian;
    private javax.swing.JButton buttonHapus_Supp;
    private javax.swing.JButton buttonHapus_akun;
    private javax.swing.JButton buttonHapus_brg;
    private javax.swing.JButton buttonHapus_jns;
    private javax.swing.JButton buttonHapus_kyw;
    private javax.swing.JButton buttonHapus_satuan;
    private javax.swing.JButton buttonHitungGaji;
    private javax.swing.JButton buttonHome_brg;
    private javax.swing.JButton buttonHome_home;
    private javax.swing.JButton buttonHome_kyw;
    private javax.swing.JButton buttonHome_lap;
    private javax.swing.JButton buttonHome_supp;
    private javax.swing.JButton buttonHome_tr;
    private javax.swing.JButton buttonHome_user;
    private javax.swing.JButton buttonInsert_brg;
    private javax.swing.JButton buttonInsert_kyw;
    private javax.swing.JButton buttonJenis_brg;
    private javax.swing.JButton buttonKas_lap;
    private javax.swing.JButton buttonKasir_home;
    private javax.swing.JButton buttonKyw_home;
    private javax.swing.JButton buttonKyw_kyw;
    private javax.swing.JButton buttonLapBeli_lap;
    private javax.swing.JButton buttonLapBrg_lap;
    private javax.swing.JButton buttonLapJual_lap;
    private javax.swing.JButton buttonLapUtang_lap;
    private javax.swing.JButton buttonLap_home;
    private javax.swing.JButton buttonManageAkun_kyw;
    private javax.swing.JButton buttonManage_supp;
    private javax.swing.JButton buttonSatuan_brg;
    private javax.swing.JButton buttonSimpan;
    private javax.swing.JButton buttonSimpanBeli;
    private javax.swing.JButton buttonSimpanGajiBersih;
    private javax.swing.JButton buttonSimpan_Kasir;
    private javax.swing.JButton buttonSimpan_pass;
    private javax.swing.JButton buttonSupplier_home;
    private javax.swing.JButton buttonTambahBeli_supp;
    private javax.swing.JButton buttonTambahJenis;
    private javax.swing.JButton buttonTambahKas;
    private javax.swing.JButton buttonTambahSupp;
    private javax.swing.JButton buttonTambah_Satuan;
    private javax.swing.JButton buttonTambah_akun;
    private javax.swing.JButton buttonTransaksi_tr;
    private javax.swing.JButton buttonUbahPass_user;
    private javax.swing.JButton buttonUser_home;
    private javax.swing.JComboBox cbJenis;
    private javax.swing.JComboBox cbKategori;
    private javax.swing.JComboBox cbObatBeli;
    private javax.swing.JComboBox cbSatuan;
    private javax.swing.JComboBox cbSuppBeli;
    private javax.swing.JComboBox cbUser;
    private javax.swing.JPanel dataBarang;
    private javax.swing.JPanel dataKaryawan;
    private javax.swing.JTextField debitKas;
    private javax.swing.JTextField email;
    private javax.swing.JTextField emailsupp;
    private javax.swing.JButton enter;
    private javax.swing.JTextField gaji_pokok;
    private javax.swing.JTextField harga;
    private javax.swing.JTextField hargaBeli;
    private javax.swing.JTextField hargaJual;
    private javax.swing.JTextField idKas;
    private javax.swing.JTextField id_jenis;
    private javax.swing.JTextField id_karyawan;
    private javax.swing.JTextField id_satuan;
    private javax.swing.JTextField id_supp;
    private javax.swing.JTextField id_user;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jenis;
    private javax.swing.JTextField kd_obat;
    private javax.swing.JTextField kd_pembelian;
    private javax.swing.JTextField keteranganKas;
    private javax.swing.JTextField kewarganegaraan;
    private javax.swing.JTextField kreditKas;
    private javax.swing.JLabel labelGambar;
    private javax.swing.JRadioButton laki;
    private javax.swing.JTextField level;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainPanelBrg;
    private javax.swing.JPanel mainPanelKyw;
    private javax.swing.JPanel mainPanelLap;
    private javax.swing.JPanel mainPanelSupplier;
    private javax.swing.JPanel mainPanelTr;
    private javax.swing.JPanel mainPanelUser;
    private javax.swing.JPanel menuHome;
    private javax.swing.JPanel menuHome3;
    private javax.swing.JPanel menuHome4;
    private javax.swing.JPanel menuHome5;
    private javax.swing.JPanel menuHome6;
    private javax.swing.JPanel menuHome7;
    private javax.swing.JPanel menuHome8;
    private javax.swing.JTextField nama_karyawan;
    private javax.swing.JTextField nama_obat;
    private javax.swing.JTextField nama_pt;
    private javax.swing.JTextField no_tlp;
    private javax.swing.JPanel panelnama;
    private javax.swing.JTextField password;
    private javax.swing.JTextField pendidikan_terakhir;
    private javax.swing.JRadioButton perempuan;
    private javax.swing.JTextField saldoKas;
    private javax.swing.JTextField satuan;
    private javax.swing.JTextField status;
    private javax.swing.JTextField stok;
    private javax.swing.JTable tabelBarang;
    private javax.swing.JTable tabelKasir;
    private javax.swing.JTable tabelKyw;
    private javax.swing.JTable tableJenis;
    private javax.swing.JTable tableSatuan;
    private javax.swing.JTable tableSupp;
    private javax.swing.JTable tableUser;
    private javax.swing.JTextField tanggalBeli;
    private javax.swing.JTextField telpsupp;
    private javax.swing.JTextField totalHargaBeli;
    private javax.swing.JTextField txAsuransi;
    private javax.swing.JTextField txBayar;
    private javax.swing.JTextField txBonus;
    private javax.swing.JTextField txCariLapBeli;
    private javax.swing.JTextField txCariLapBrg;
    private javax.swing.JTextField txGajiBersih;
    private javax.swing.JTextField txGaji_Pokok;
    public static javax.swing.JTextField txHarga;
    public static javax.swing.JTextField txID_Barang;
    private javax.swing.JTextField txID_Kyw;
    private javax.swing.JTextField txJumlah;
    private javax.swing.JTextField txJumlahLembur;
    private javax.swing.JTextField txKembalian;
    private javax.swing.JTextField txMasaKerja_Kyw;
    public static javax.swing.JTextField txNamaObat;
    private javax.swing.JTextField txNama_kyw;
    private javax.swing.JTextField txNoTransaksi;
    private javax.swing.JTextField txPemotongan;
    private javax.swing.JTextField txStatus_Kyw;
    private javax.swing.JTextField txTampil;
    private javax.swing.JTextField txTanggal;
    private javax.swing.JTextField txTotalBayar;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCariBrg;
    private javax.swing.JTextField txtCariLapJual;
    private javax.swing.JTextField txtCariPembelian;
    private javax.swing.JTextField txtCari_kyw;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdKaryawan;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JTextField txtId_user;
    private javax.swing.JTextField txtJK;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNotlp;
    private javax.swing.JPasswordField txtPassBaru;
    private javax.swing.JPasswordField txtPassBaru2;
    private javax.swing.JPasswordField txtPassLama;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPendidikan;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtUser;
    private javax.swing.JTextField txtWarga;
    private javax.swing.JTextField txtuser;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
