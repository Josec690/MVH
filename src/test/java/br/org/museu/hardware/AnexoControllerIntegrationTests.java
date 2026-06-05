package br.org.museu.hardware;

import br.org.museu.hardware.model.Equipamento;
import br.org.museu.hardware.model.Fabricante;
import br.org.museu.hardware.repository.EquipamentoRepository;
import br.org.museu.hardware.repository.FabricanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AnexoControllerIntegrationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FabricanteRepository fabricanteRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    private MockMvc mockMvc;
    private Long idEquipamento;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Fabricante fabricante = new Fabricante();
        fabricante.setNome("Fabricante Teste");
        fabricante.setPais("Brasil");

        Equipamento equipamento = new Equipamento();
        equipamento.setModelo("Equipamento Teste");
        equipamento.setAno(1985);
        equipamento.setGeracao("MSX1");
        equipamento.setFabricante(fabricanteRepository.save(fabricante));

        idEquipamento = equipamentoRepository.save(equipamento).getIdEquipamento();
    }

    @Test
    void criaEListaImagemComUrlPorEquipamento() throws Exception {
        String urlImagem = "https://example.com/msx.png";
        String body = """
                {
                  "formato": ".png",
                  "tamanho": "200KB",
                  "resolucao": "640x480",
                  "url": "%s",
                  "idEquipamento": %d
                }
                """.formatted(urlImagem, idEquipamento);

        mockMvc.perform(post("/api/imagens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").value(urlImagem))
                .andExpect(jsonPath("$.idEquipamento").value(idEquipamento.intValue()));

        mockMvc.perform(get("/api/imagens/equipamento/{idEquipamento}", idEquipamento))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].url").value(urlImagem))
                .andExpect(jsonPath("$[0].idEquipamento").value(idEquipamento.intValue()));
    }

    @Test
    void criaEListaInformacoesPorEquipamento() throws Exception {
        mockMvc.perform(post("/api/curiosidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "descricao": "Curiosidade cadastrada via teste",
                                  "idEquipamento": %d
                                }
                                """.formatted(idEquipamento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEquipamento").value(idEquipamento.intValue()));

        mockMvc.perform(post("/api/fontes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome_site": "Fonte Teste",
                                  "url_original": "https://example.com/fonte",
                                  "idEquipamento": %d
                                }
                                """.formatted(idEquipamento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEquipamento").value(idEquipamento.intValue()));

        mockMvc.perform(post("/api/documentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titulo": "Manual Teste",
                                  "url": "https://example.com/manual.pdf",
                                  "idEquipamento": %d
                                }
                                """.formatted(idEquipamento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEquipamento").value(idEquipamento.intValue()));

        mockMvc.perform(get("/api/curiosidades/equipamento/{idEquipamento}", idEquipamento))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Curiosidade cadastrada via teste"));

        mockMvc.perform(get("/api/fontes/equipamento/{idEquipamento}", idEquipamento))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].url_original").value("https://example.com/fonte"));

        mockMvc.perform(get("/api/documentos/equipamento/{idEquipamento}", idEquipamento))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].url").value("https://example.com/manual.pdf"));
    }
}
