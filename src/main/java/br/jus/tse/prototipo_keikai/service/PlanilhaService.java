package br.jus.tse.prototipo_keikai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.jus.tse.prototipo_keikai.entity.Despesa;
import br.jus.tse.prototipo_keikai.repository.DespesaRepository;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.Sheet;
import io.keikai.ui.Spreadsheet;

/**
 * Serviço responsável por transformar os dados da planilha Keikai em entidades {@link Despesa}.
 */
@Service
public class PlanilhaService {

    private static final String CATEGORIA_PADRAO = "SEM CATEGORIA";

    public static final int COL_ID = 0;
    public static final int COL_CATEGORIA = 1;
    public static final int COL_DESCRICAO = 2;
    public static final int COL_VALOR = 3;
    public static final int PRIMEIRA_LINHA_DADOS = 1;

    @Autowired
    private DespesaRepository repository;

    /**
     * Percorre a planilha ativa do componente {@link Spreadsheet}, extrai as linhas preenchidas
     * e persiste os dados como entidades {@link Despesa}. Caso o ID esteja presente na planilha,
     * o registro é atualizado; caso contrário, é inserido um novo.
     *
     * @param spreadsheet instância do componente Keikai com a planilha carregada
     * @return quantidade de registros processados
     */
    @Transactional
    public int processarPlanilha(Spreadsheet spreadsheet) {
        Sheet sheet = spreadsheet.getSelectedSheet();
        List<Despesa> despesas = new ArrayList<>();

        for (int row = PRIMEIRA_LINHA_DADOS; ; row++) {
            Range descricaoCell = Ranges.range(sheet, row, COL_DESCRICAO);
            String descricao = descricaoCell.getCellEditText();
            if (descricao == null || descricao.isBlank()) {
                break;
            }

            Long id = recuperarLong(Ranges.range(sheet, row, COL_ID));
            Despesa despesa = id != null
                    ? repository.findById(id).orElseGet(Despesa::new)
                    : new Despesa();

            if (id != null) {
                despesa.setId(id);
            }

            despesa.setCategoria(recuperarTexto(Ranges.range(sheet, row, COL_CATEGORIA), CATEGORIA_PADRAO));
            despesa.setDescricao(descricao);
            despesa.setValor(recuperarDouble(Ranges.range(sheet, row, COL_VALOR)));

            despesas.add(despesa);
        }

        if (!despesas.isEmpty()) {
            repository.saveAll(despesas);
        }

        return despesas.size();
    }

    /**
     * Converte o valor da célula para {@link Long}, aceitando números e strings normalizadas.
     *
     * @param cell célula da planilha
     * @return valor convertido ou {@code null}
     */
    private Long recuperarLong(Range cell) {
        if (cell == null) {
            return null;
        }

        Object value = cell.getCellValue();
        if (value instanceof Number number) {
            long converted = number.longValue();
            return converted > 0 ? converted : null;
        }

        String text = cell.getCellEditText();
        if (text != null && !text.isBlank()) {
            try {
                long converted = Long.parseLong(text.trim());
                return converted > 0 ? converted : null;
            } catch (NumberFormatException e) {
                System.err.println("ID inválido para conversão numérica: " + text);
            }
        }

        return null;
    }

    /**
     * Converte o valor de uma célula para {@link Double}, tratando números e strings com vírgula.
     *
     * @param cell célula da planilha
     * @return valor numérico convertido ou zero
     */
    private Double recuperarDouble(Range cell) {
        if (cell == null) {
            return 0.0;
        }

        Object value = cell.getCellValue();
        if (value instanceof Number number) {
            return number.doubleValue();
        }

        String text = cell.getCellEditText();
        if (text != null && !text.isBlank()) {
            try {
                return Double.parseDouble(text.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.err.println("Valor inválido para conversão numérica: " + text);
            }
        }

        return 0.0;
    }

    /**
     * Normaliza o texto de uma célula, aplicando um valor padrão quando necessário.
     *
     * @param cell célula da planilha
     * @param defaultValue valor padrão para campos vazios
     * @return texto normalizado
     */
    private String recuperarTexto(Range cell, String defaultValue) {
        String text = cell.getCellEditText();
        if (text == null || text.isBlank()) {
            return defaultValue;
        }
        return text.trim();
    }
}
