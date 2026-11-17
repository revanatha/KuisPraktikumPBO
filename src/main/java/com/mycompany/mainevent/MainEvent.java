/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mainevent;

/**
 *
 * @author Talitha Reva Nabila
 */

public class MainEvent {
    public static void main(String[] args) {
        SistemEvent event = new SistemEvent("Event Organizer Puncak");
        
        event.tambahPeserta("ACT-01", "Qori");
        event.tambahPeserta("ACT-02", "Rafi");
        
        event.tambahTiketAcara("OBJ-A", "Konser VIP Premium", 10);
        event.tambahTiketAcara("OBJ-B", "Seminar Reguler", 2);
        event.tambahTiketAcara("OBJ-C", "Workshop Ekonomis", 50);

        System.out.println("--- MEMULAI PEMBACAAN FILE TRANSAKSI ---");
        
        try {
            event.jalankanTransaksiDariFile("input_transaksi.txt");
        } catch (Exception e) {
            System.out.println("[SISTEM FATAL ERROR] File gagal dibaca/diproses: " + e.getMessage());
        }

        event.simpanLaporanAkhir();
    }
}
