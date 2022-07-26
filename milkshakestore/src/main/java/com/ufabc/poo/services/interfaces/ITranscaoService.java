package com.ufabc.poo.services.interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import com.ufabc.poo.domain.interfaces.ITransacao;

public interface ITranscaoService {
    boolean efetuaCompra(ITransacao compra);
    boolean removeCompra(UUID Id);
    boolean efetuaVenda(ITransacao venda);
    ArrayList<ITransacao> getVendas(Timestamp date);
    ArrayList<ITransacao> getCompras(Timestamp date);
}
