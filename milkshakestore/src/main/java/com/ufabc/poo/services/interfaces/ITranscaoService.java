package com.ufabc.poo.services.interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import com.ufabc.poo.domain.abstractions.ATransacao;

public interface ITranscaoService {
    boolean efetuaCompra(ATransacao compra);
    boolean removeCompra(UUID Id);
    boolean efetuaVenda(ATransacao venda);
    ArrayList<ATransacao> getVendas(Timestamp date);
    ArrayList<ATransacao> getCompras(Timestamp date);
}
