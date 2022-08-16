package com.ufabc.poo.services.interfaces;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.ufabc.poo.domain.abstractions.ATransacao;

public interface ITranscaoService {
    boolean efetuaCompra(ATransacao compra);
    boolean removeCompra(UUID Id);
    boolean removeCompra(UUID Id, int quantidade);
    boolean efetuaVenda(ATransacao venda);
    ArrayList<ATransacao> getVendas(LocalDate dataInicial, LocalDate dataFinal);
    ArrayList<ATransacao> getCompras(LocalDate dataInicial, LocalDate dataFinal);
}
