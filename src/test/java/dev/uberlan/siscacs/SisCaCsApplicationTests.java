package dev.uberlan.siscacs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SisCaCsApplicationTests {


//	@Autowired
//	private UsuarioService usuarioService;
//
//	@Autowired
//	private ArmaService armaService;
//	@Autowired private MunicaoService municaoService;
//	@Autowired private TreinamentoService treinamentoService;

	@Test
	void contextLoads() {
//		Usuario usuario = usuarioService.findUsuarioByLogin("teste02@teste.com").orElseThrow(() -> UsuarioNotFoundException.of("teste02@teste.com"));
//		UUID idTreinamento = UUID.fromString("9fecebce-ead3-4804-a6f2-8142c3e18115");
//		TreinamentoDTO treinamentoDTO = treinamentoService.findTreinamentoById(idTreinamento).orElseThrow(() -> TreinamentoNotFoundException.of(idTreinamento));
//		System.out.println("treinamentoDTO = " + treinamentoDTO);
//		ArmaDTO armaDTO = new ArmaDTO(treinamentoDTO.arma().id(), treinamentoDTO.arma().calibre(), treinamentoDTO.arma().descricao());
//		System.out.println("armaDTO = " + armaDTO);
//		TreinamentoUpdateRequest treinamentoRequest = new TreinamentoUpdateRequest(treinamentoDTO.id(), treinamentoDTO.dataTreinamento(), treinamentoDTO.arma().id(), treinamentoDTO.quantidadeTiros(), treinamentoDTO.pontuacao(), treinamentoDTO.observacao());
//		System.out.println("treinamentoRequest = " + treinamentoRequest);
//		TreinamentoUpdateCommand cmd = new TreinamentoUpdateCommand(idTreinamento, treinamentoRequest.dataTreinamento(), new ArmaDTO(treinamentoRequest.armaId()), treinamentoRequest.quantidadeTiros(), treinamentoRequest.pontuacao(), treinamentoRequest.observacao());
//		treinamentoService.updateTreinamento(cmd);
	}

}
