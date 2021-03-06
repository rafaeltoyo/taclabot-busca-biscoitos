package controller;

import algoritmos.busca.BuscaLRTA;
import algoritmos.busca.TipoBusca;
import algoritmos.heuristica.HeuristicaChebyshev;
import ambiente.Model;
import comuns.TemplateLabirinto;
import problema.Problema;
import sistema.Agente;

public class MainController
{
    // #################################################################################################################

    private static MainController ourInstance = new MainController();

    public static MainController getInstance() {
        return ourInstance;
    }

    private MainController() { }

    // #################################################################################################################

    public void runBuscaInformada1()
    {
        runBuscaOffline(TipoBusca.ESTRELA_1);
        ViewController.printDesempenho();
    }

    public void runBuscaInformada2()
    {
        runBuscaOffline(TipoBusca.ESTRELA_2);
        ViewController.printDesempenho();
    }

    public void runBuscaCustoUniforme()
    {
        runBuscaOffline(TipoBusca.CUSTO_UNI);
        ViewController.printDesempenho();
    }

    public void runBuscaAll()
    {
        runBuscaOffline(TipoBusca.CUSTO_UNI);
        runBuscaOffline(TipoBusca.ESTRELA_1);
        runBuscaOffline(TipoBusca.ESTRELA_2);
        ViewController.printDesempenho();
    }

    public void runBuscaOfflineInput()
    {
        TipoBusca tipo = ViewController.printTipoBuscaOptions();
        runBuscaOffline(tipo);
        ViewController.printDesempenho();
    }

    protected void runBuscaOffline(TipoBusca tipo)
    {
        Model model = TemplateLabirinto.makeModelLab2();
        Problema problem = TemplateLabirinto.makeProblemLab2();
        Agente agente = new Agente(model, problem);

        BuscaController.prepare(agente, tipo);

        // Ciclo de execucao do sistema
        // desenha labirinto
        ViewController.desenharLabModel(model);

        // agente escolhe proxima açao e a executa no ambiente (modificando
        // o estado do labirinto porque ocupa passa a ocupar nova posicao)
        System.out.println("\n*** Inicio do ciclo de raciocinio do agente ***\n");

        while (agente.deliberar() != -1) {
            ViewController.desenharLabModel(model);
        }
    }
    protected void runBuscaLRTA()
    {
        Model model = TemplateLabirinto.makeModelLab2();
        Problema problem = TemplateLabirinto.makeProblemLab2();

        BuscaLRTA.resetMemory(problem, new HeuristicaChebyshev());

        while (true) {
            Agente agente = new Agente(model, problem);
            BuscaController.prepare(agente, TipoBusca.LRTA_1);

            // Ciclo de execucao do sistema
            // desenha labirinto
            ViewController.desenharLabModel(model);

            // agente escolhe proxima açao e a executa no ambiente (modificando
            // o estado do labirinto porque ocupa passa a ocupar nova posicao)
            System.out.println("\n*** Inicio do ciclo de raciocinio do agente ***\n");

            while (agente.deliberar() != -1) {
                ViewController.desenharLabModel(model);
            }

            // Resetar o model para o estado inicial
            model.setPos(problem.estIni.getLin(), problem.estObj.getCol());
        }

    }
}
