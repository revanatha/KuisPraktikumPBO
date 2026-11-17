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

public abstract class TiketAcara implements Serializable {
    private static final long serialVersionUID = 1L;
    private String kode;
    private String namaAcara;
    private int kuota;

    public TiketAcara(String kode, String namaAcara, int kuota) {
        this.kode = kode;
        this.namaAcara = namaAcara;
        this.kuota = kuota;
    }

    public String getKode() {
        return kode;
    }

    public String getNamaAcara() {
        return namaAcara;
    }

    public int getKuota() {
        return kuota;
    }

    public void kurangiKuota(int jumlah) throws TransaksiTidakValidException {
        if (jumlah <= 0) {
            throw new TransaksiTidakValidException("Jumlah tiket harus lebih dari 0.");
        }
        if (this.kuota < jumlah) {
            throw new TransaksiTidakValidException(
                "Kuota tidak mencukupi untuk " + namaAcara + " (Sisa: " + kuota + ", Diminta: " + jumlah + ")"
            );
        }
        this.kuota -= jumlah;
    }

    public abstract String getKategoriTiket();
}
