/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainevent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Talitha Reva Nabila
 */
public class SistemEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private String namaEventOrganizer;
    private HashMap<String, Peserta> daftarPeserta;
    private HashMap<String, TiketAcara> daftarTiket;
    private ArrayList<Transaksi> daftarTransaksi;

    public SistemEvent(String namaEventOrganizer) {
        this.namaEventOrganizer = namaEventOrganizer;
        this.daftarPeserta = new HashMap<>();
        this.daftarTiket = new HashMap<>();
        this.daftarTransaksi = new ArrayList<>();
    }

    public void tambahPeserta(String id, String nama) {
        daftarPeserta.put(id, new Peserta(id, nama));
    }

    public void tambahTiketAcara(String kode, String namaAcara, int kuota) {
        TiketAcara tiket;
        String namaLower = namaAcara.toLowerCase();

        if (namaLower.contains("premium")) {
            tiket = new TiketPremium(kode, namaAcara, kuota);
        } else if (namaLower.contains("reguler")) {
            tiket = new TiketReguler(kode, namaAcara, kuota);
        } else {
            tiket = new TiketEkonomis(kode, namaAcara, kuota);
        }

        daftarTiket.put(kode, tiket);
    }

    public void jalankanTransaksiDariFile(String namaFile) throws FileNotFoundException {
        File fileTransaksi = new File(namaFile);
        Scanner mesinBaca = new Scanner(fileTransaksi);

        while (mesinBaca.hasNextLine()) {
            String baris = mesinBaca.nextLine();
            if (baris.trim().isEmpty()) {
                continue;
            }
            
            String[] listTransaksi = baris.split("\\|");

            for (int i = 0; i < listTransaksi.length; i++) {
                String dataTrx = listTransaksi[i].trim();
                if (dataTrx.isEmpty()) {
                    continue;
                }

                try {
                    String[] bagian = dataTrx.split("/");
                    if (bagian.length != 3) {
                        throw new TransaksiTidakValidException("Format data tidak sesuai: " + dataTrx);
                    }

                    String segmenId = bagian[0].trim();
                    String segmenKode = bagian[1].trim();
                    String segmenJumlah = bagian[2].trim();

                    if (!segmenId.startsWith("ID-") || !segmenKode.startsWith("KD-") || !segmenJumlah.startsWith("JML-")) {
                        throw new TransaksiTidakValidException("Awalan harus ID-, KD-, dan JML-: " + dataTrx);
                    }

                    String idPeserta = segmenId.substring(3).trim();
                    String kodeTiket = segmenKode.substring(3).trim();
                    int jumlah = Integer.parseInt(segmenJumlah.substring(4).trim());

                    prosesTransaksi(idPeserta, kodeTiket, jumlah);

                } catch (TransaksiTidakValidException e) {
                    System.out.println("[TRANSAKSI GAGAL] " + dataTrx + " -> " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("[TRANSAKSI GAGAL] " + dataTrx + " -> Jumlah tiket harus berupa angka.");
                }
            }
        }

        mesinBaca.close();
    }

    private void prosesTransaksi(String idPeserta, String kodeTiket, int jumlah) throws TransaksiTidakValidException {
        Peserta peserta = daftarPeserta.get(idPeserta);
        if (peserta == null) {
            throw new TransaksiTidakValidException("ID Peserta '" + idPeserta + "' tidak terdaftar di sistem.");
        }

        TiketAcara tiket = daftarTiket.get(kodeTiket);
        if (tiket == null) {
            throw new TransaksiTidakValidException("Kode Tiket '" + kodeTiket + "' tidak ditemukan.");
        }

        tiket.kurangiKuota(jumlah);
        Transaksi trx = new Transaksi(peserta, tiket, jumlah);
        daftarTransaksi.add(trx);

        System.out.println("[BERHASIL] " + peserta.getNama() + " (" + idPeserta + ") membeli "
                + jumlah + " tiket " + tiket.getNamaAcara() + " [" + tiket.getKategoriTiket() + "]"
                + " | Sisa kuota: " + tiket.getKuota());
    }

    public void simpanLaporanAkhir() {
        try {
            FileOutputStream fileKeluar = new FileOutputStream("sistem_state.ser");
            ObjectOutputStream mesinPembeku = new ObjectOutputStream(fileKeluar);

            mesinPembeku.writeObject(this);
            mesinPembeku.close();

            System.out.println("\n[SERIALIZATION] Status sistem berhasil disimpan ke 'sistem_state.ser'.");
        } catch (IOException e) {
            System.out.println("[ERROR SERIALIZATION] Gagal menyimpan objek: " + e.getMessage());
        }

        try {
            FileWriter pipaTulis = new FileWriter("laporan.txt");
            pipaTulis.write("=== DAFTAR AKTOR AKTIF (" + namaEventOrganizer + ") ===\n");

            for (Peserta p : daftarPeserta.values()) {
                pipaTulis.write("ID: " + p.getId() + " | Nama: " + p.getNama() + "\n");
            }

            pipaTulis.close();
            System.out.println("[FILE I/O] Laporan daftar aktor aktif berhasil ditulis ke 'laporan.txt'.");
        } catch (IOException e) {
            System.out.println("[ERROR FILE I/O] Gagal menulis laporan.txt: " + e.getMessage());
        }
    }
}
