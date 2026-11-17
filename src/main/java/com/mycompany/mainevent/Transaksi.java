/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainevent;

import java.io.Serializable;

/**
 *
 * @author Talitha Reva Nabila
 */

public class Transaksi implements Serializable {
    private static final long serialVersionUID = 1L;
    private Peserta peserta;
    private TiketAcara tiket;
    private int jumlah;

    public Transaksi(Peserta peserta, TiketAcara tiket, int jumlah) {
        this.peserta = peserta;
        this.tiket = tiket;
        this.jumlah = jumlah;
    }

    public Peserta getPeserta() {
        return peserta;
    }

    public TiketAcara getTiket() {
        return tiket;
    }

    public int getJumlah() {
        return jumlah;
    }
}
