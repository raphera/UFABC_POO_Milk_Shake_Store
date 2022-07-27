package com.ufabc.poo.helpers;

import com.ufabc.poo.services.BancoDeMilkShakes;
import com.ufabc.poo.services.Estoque;
import com.ufabc.poo.services.PersistenceService;
import com.ufabc.poo.services.TransacaoService;
import com.ufabc.poo.services.interfaces.IBancoDeMilkShakes;
import com.ufabc.poo.services.interfaces.IEstoque;
import com.ufabc.poo.services.interfaces.IPersistenceService;
import com.ufabc.poo.services.interfaces.ITranscaoService;

import eu.lestard.easydi.EasyDI;

public class DI {
    public static EasyDI injector;
    static {
        injector = new EasyDI();
        injector.bindInterface(IBancoDeMilkShakes.class, BancoDeMilkShakes.class);
        injector.bindInterface(IPersistenceService.class, PersistenceService.class);
        injector.bindInterface(IEstoque.class, Estoque.class);
        injector.bindInterface(ITranscaoService.class, TransacaoService.class);
    }

}
