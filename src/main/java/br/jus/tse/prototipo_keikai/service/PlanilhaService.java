package br.jus.tse.prototipo_keikai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.jus.tse.prototipo_keikai.entity.Despesa;
import br.jus.tse.prototipo_keikai.repository.DespesaRepository;
import io.keikai.api.Ranges;
import io.keikai.ui.Spreadsheet;

@Service
public class PlanilhaService {

    @Autowired
    private DespesaRepository repository;

    @Transactional
    public int processarPlanilha(Spreadsheet spreadsheet) {
        var sheet = spreadsheet.getSelectedSheet();
        List<Despesa> listaParaSalvar = new ArrayList<>();

        // Iniciamos em i = 1 para pular o cabeçalho
        for (int i = 1; i <= 5; i++) {
            // Coluna 0 é o ID (geralmente ignoramos pois o DB gera via IDENTITY)
            
            // Coluna 1: Categoria
            Object rawCat = Ranges.range(sheet, i, 1).getCellValue();
            
            // Coluna 2: Descrição (Usamos como critério de parada)
            Object rawDesc = Ranges.range(sheet, i, 2).getCellValue();
            if (rawDesc == null || rawDesc.toString().isBlank()) break;

            // Coluna 3: Valor
            Object rawValor = Ranges.range(sheet, i, 3).getCellValue();

            try {
                Despesa d = new Despesa();
                d.setCategoria(rawCat != null ? rawCat.toString() : "SEM CATEGORIA");
                d.setDescricao(rawDesc.toString());
                d.setValor(extrairDouble(rawValor));

                listaParaSalvar.add(d);
            } catch (Exception e) {
                System.err.println("Erro na linha " + (i + 1) + ": " + e.getMessage());
            }
        }

        if (!listaParaSalvar.isEmpty()) {
            repository.saveAll(listaParaSalvar);
        }
        
        return listaParaSalvar.size();
    }

    private Double extrairDouble(Object value) {
        // 1. Tratamento para Nulo
        if (value == null) {
            return 0.0;
        }

        // 2. Pattern Matching para Number (Captura Double, Integer, Long, etc.)
        if (value instanceof Number n) {
            return n.doubleValue();
        }

        // 3. Pattern Matching para String com verificação de conteúdo
        if (value instanceof String s && !s.isBlank()) {
            try {
                // Normaliza o formato brasileiro (vírgula) para o padrão Double (ponto)
                return Double.parseDouble(s.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                // Logar o valor problemático para facilitar o debug em produção
                System.err.println("Valor inválido para conversão numérica: " + s);
                return 0.0;
            }
        }

        return 0.0;
    }
}
