package me.report;

import me.report.utils.DateManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigManager {

    public String usageCommand;
    public String playerNotFound;
    public String reportIsPlayer;
    public String immune;
    public String inCooldown;
    public List<String> reportMessageSend;

    public List<String> reportMessageReceived;
    public String reportNotFound;
    public String messageTeleport;
    public String reportExcluded;

    public long cooldown;

    public String titleReport;
    public List<String> loreReport;

    public String inventoryName;

    public String nextPageName;
    public String materialNextPage;
    public boolean isSkullNextPage;
    public String skullUrlNextPage;
    public List<String> loreNextPage;

    public String nameBackPage;
    public String materialBackPage;
    public boolean isSkullBackPage;
    public String skullUrlBackPage;
    public List<String> loreBackPage;

    public void loadConfig() {

        FileConfiguration inv = DateManager.getConfig("inventarios");
        FileConfiguration config = BoxReport.getInstance().getConfig();

        usageCommand = config.getString("Mensagens.Jogadores.ComoReportar");
        playerNotFound = config.getString("Mensagens.Jogadores.JogadorNaoEncontrado");
        reportIsPlayer = config.getString("Mensagens.Jogadores.ReportarASiMesmo");
        immune = config.getString("Mensagens.Jogadores.Imune");
        inCooldown = config.getString("Mensagens.Jogadores.EmCooldown");
        reportMessageSend = config.getStringList("Mensagens.Jogadores.JogadorReportado");
        reportMessageSend = reportMessageSend.stream().map(l -> l.replace("&", "§")).collect(Collectors.toList());

        reportMessageReceived = config.getStringList("Mensagens.Staffs.NovoReporte");
        reportMessageReceived = reportMessageReceived.stream().map(l -> l.replace("&", "§")).collect(Collectors.toList());
        reportNotFound = config.getString("Mensagens.Staffs.ReportadoNaoEncontrado");
        messageTeleport = config.getString("Mensagens.Staffs.TeleportadoComSucesso");
        reportExcluded = config.getString("Mensagens.Staffs.ReporteExcluido");

        cooldown = config.getLong("Configuracoes.Cooldown");

        titleReport = config.getString("Formato.Reportes.Titulo");
        loreReport = config.getStringList("Formato.Reportes.Lore");

        inventoryName = inv.getString("Inventarios.Reportes.Nome").replace("&", "§");

        nextPageName = inv.getString("Inventarios.Reportes.Items.ProximaPagina.Nome");
        materialNextPage = inv.getString("Inventarios.Reportes.Items.ProximaPagina.Material");
        isSkullNextPage = inv.getBoolean("Inventarios.Reportes.Items.ProximaPagina.Skull.Ativar");
        skullUrlNextPage = inv.getString("Inventarios.Reportes.Items.ProximaPagina.Skull.Skull-URL");
        loreNextPage = inv.getStringList("Inventarios.Reportes.Items.ProximaPagina.Lore");

        nameBackPage = inv.getString("Inventarios.Reportes.Items.PaginaAnterior.Nome");
        materialBackPage = inv.getString("Inventarios.Reportes.Items.PaginaAnterior.Material");
        isSkullBackPage = inv.getBoolean("Inventarios.Reportes.Items.PaginaAnterior.Skull.Ativar");
        skullUrlBackPage = inv.getString("Inventarios.Reportes.Items.PaginaAnterior.Skull.Skull-URL");
        loreBackPage = inv.getStringList("Inventarios.Reportes.Items.PaginaAnterior.Lore");

    }
}
