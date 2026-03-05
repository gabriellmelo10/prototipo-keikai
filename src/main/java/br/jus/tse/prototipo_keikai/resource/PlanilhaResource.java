package br.jus.tse.prototipo_keikai.resource;

import br.jus.tse.prototipo_keikai.config.SpringContextHolder;
import br.jus.tse.prototipo_keikai.service.PlanilhaService;
import io.keikai.ui.Spreadsheet;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.util.Clients;

public class PlanilhaResource extends SelectorComposer<Window> {

    @Wire
    private Spreadsheet meuKeikai; // ID definido no arquivo .zul

    private transient PlanilhaService planilhaService;

    @Override
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        this.planilhaService = SpringContextHolder.getBean(PlanilhaService.class);
    }

    @Listen("onClick = #btnSalvar")
    public void salvarDados() {
        int total = planilhaService.processarPlanilha(meuKeikai);
        Messagebox.show(total + " registros salvos no H2 com sucesso!");
    }
}
