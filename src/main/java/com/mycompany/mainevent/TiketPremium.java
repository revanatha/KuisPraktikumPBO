/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainevent;

/**
 *
 * @author Talitha Reva Nabila
 */
public class TiketPremium extends TiketAcara {
    private static final long serialVersionUID = 1L;

    public TiketPremium(String kode, String namaAcara, int kuota) {
        super(kode, namaAcara, kuota);
    }

    @Override
    public String getKategoriTiket() {
        return "VIP Premium (Akses Front Row & Lounge)";
    }
}
