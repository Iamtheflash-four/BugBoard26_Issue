package service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.IssuePostgresDAO;
import dto.CreateIssueRequest;
import dto.ImageDTO;
import dto.IssueDTO;
import entity.Issue;
import exceptions.FileNotUploadedException;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import service.FileUploader;
import service.SegnalazioneIssue;
import service.TokenGenerator;
import org.mockito.Mockito;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import static org.mockito.Mockito.*;

class SegnalazioneIssueTest {
	private Issue issue;
	private CreateIssueRequest issueRequest;
	private String validToken;
	private ImageDTO image;
	private ArrayList<ImageDTO> images; 
	private static TokenGenerator validator;
	private static IssuePostgresDAO issueDAO;
	private static FileUploaderWrapper fileUploader;
	
	@BeforeEach
	void initialize() {
        // Issue di base valida
		issue = new Issue(1,"EVERGREEN", "Bug", "Alta", "Titolo valido", "Descrizione valida", LocalDate.now());
		// Token di test
		image = createValidPngImage();
		images = new ArrayList<ImageDTO>();
		images.add(image);
		             // per i test negativi
        validToken = "tokenValido"; // per i test positivi
        //Richiesta
		issueRequest = new CreateIssueRequest(issue, null);  
		creaMock();
    }
	
	private ImageDTO createValidPngImage() {
	    byte[] bytes = new byte[1024 * 1024]; // 1MB
	    String base64 = Base64.getEncoder().encodeToString(bytes);

	    ImageDTO img = new ImageDTO();
	    img.setFileName("immagine.png");
	    img.setFilePath("/fake/path");
	    img.setContent(base64);

	    return img;
	}

	private void creaMock()
	{
		validator = mock(TokenGenerator.class);
	    issueDAO = mock(IssuePostgresDAO.class);
	    fileUploader = mock(FileUploaderWrapper.class);

	    // SOSTITUISCI I CAMPI STATICI DELLA CLASSE REALE
	    SegnalazioneIssue.setValidator(validator);
	    SegnalazioneIssue.setIssueDAO(issueDAO);
	    SegnalazioneIssue.fileUploader = fileUploader;
	}
	
	private void initilizeMockCasiValidi() throws Exception 
	{
	    when(validator.validateUserTokenAndGetID(validToken)).thenReturn(10L);
	    when(issueDAO.insertIssue(anyLong(), any(Issue.class), any()))
	    	.thenReturn(123L);
	    when(fileUploader.uploadFile(any(), anyLong(), anyLong()))
            .thenReturn(Response.status(201).build());   
	}
	
	@Test
	protected void casoValido1() throws Exception {
		initilizeMockCasiValidi();
		issueRequest.setImages(images);

	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);

	    // Assert
	    ArrayList<String> imageNames = new ArrayList<>();
	    imageNames.add(image.getFileName());
	    assertEquals(200, response.getStatus());
	    verifica(imageNames);
	}

	
	protected void verifica(ArrayList<String> imageNames) throws Exception {
		verify(validator).validateUserTokenAndGetID(validToken);
	    verify(issueDAO).insertIssue(10L, issue, imageNames);
	    if(imageNames != null)
	    	verify(fileUploader).uploadFile(any(), eq(10L), eq(123L));
	}

	@Test
	protected void casoValido2() throws Exception
	{
		initilizeMockCasiValidi();
	    issueRequest.setImages(null);

	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);

	    // Assert
	    assertEquals(200, response.getStatus());
	    verifica(null);
	}

	@Test
	void IS001_issueRequstNull_400() throws Exception {
	    issueRequest = null;
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());
	    verify(issueDAO, never()).insertIssue(anyLong(), any(), any());
	    verify(fileUploader, never()).uploadFile(any(), anyLong(), anyLong());
	}
	
	@Test
	void IS002_issueNull_400() throws Exception {
	    Issue oldIssue = issueRequest.getIssue();
		issueRequest.setIssue(null);
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    verify(issueDAO, never()).insertIssue(anyLong(), any(), any());
	    verify(fileUploader, never()).uploadFile(any(), anyLong(), anyLong());
	    issueRequest.setIssue(oldIssue);
	}
	
	@Test
	void IS003_titoloNull_400() throws Exception {
	    String old = issue.getTitolo();
	    issue.setTitolo(null);
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setTitolo(old);
	}

	@Test
	void IS004_descrizioneNull_400() throws Exception {
	    String old = issue.getDescrizione();
	    issue.setDescrizione(null);
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setDescrizione(old);
	}

	@Test
	void IS005_tipoNull_400() throws Exception {
	    String old = issue.getTipo();
	    issue.setTipo(null);
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setTipo(old);
	}

	@Test
	void IS006_prioritaNull_400() throws Exception {
	    String old = issue.getPriorita();
	    issue.setPriorita(null);
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());
	    issue.setPriorita(old);
	}

	@Test
	void IS007_dataNull_400() throws Exception {
	    LocalDate old = issue.getData();
	    issue.setData(null);
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());
	    issue.setData(old);
	}

	@Test
	void IS008_titoloVuoto_400() throws Exception {
	    String old = issue.getTitolo();
	    issue.setTitolo("");
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setTitolo(old);
	}

	@Test
	void IS009_titoloTroppoLungo_400() throws Exception {
	    String old = issue.getTitolo();
	    issue.setTitolo("A".repeat(31));
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setTitolo(old);
	}

	@Test
	void IS010_descrizioneVuota_400() throws Exception {
	    String old = issue.getDescrizione();
	    issue.setDescrizione("");
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setDescrizione(old);
	}

	@Test
	void IS011_descrizioneTroppoLunga_400() throws Exception {
	    String old = issue.getDescrizione();
	    issue.setDescrizione("A".repeat(501));
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setDescrizione(old);
	}

	@Test
	void IS012_tipoNonValido_400() throws Exception {
	    String old = issue.getTipo();
	    issue.setTipo("INVALIDO");
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setTipo(old);
	}

	@Test
	void IS013_prioritaNonValida_400() throws Exception {
	    String old = issue.getPriorita();
	    issue.setPriorita("INVALIDA");
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setPriorita(old);
	}

	@Test
	void IS014_dataFutura_400() throws Exception {
	    LocalDate old = issue.getData();
	    issue.setData(LocalDate.now().plusDays(1));
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issue.setData(old);
	}

	@Test
	void IS015_troppeImmagini_400() throws Exception {
	    ArrayList<ImageDTO> old = issueRequest.getImages();
	    ArrayList<ImageDTO> imgs = new ArrayList<>();
	    for (int i = 0; i < 6; i++) imgs.add(image);
	    issueRequest.setImages(imgs);
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());
	    issueRequest.setImages(old);
	}

	@Test
	void IS016_estensioneNonValida_400() throws Exception {
	    ArrayList<ImageDTO> old = issueRequest.getImages();
	    ImageDTO bad = createValidPngImage();
	    bad.setFileName("virus.exe");
	    issueRequest.setImages(new ArrayList<>(List.of(bad)));
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(400, response.getStatus());;
	    issueRequest.setImages(old);
	}

	@Test
	void IS017_dimensioneTroppoGrande_400() throws Exception
	{
		ArrayList<ImageDTO> old = issueRequest.getImages(); 
		ImageDTO big = createValidPngImage(); 
		big.setContent(Base64.getEncoder().encodeToString(new byte[6 * 1024 * 1024])); 
		issueRequest.setImages(new ArrayList<>(List.of(big))); 
		Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken); 
		assertEquals(400, response.getStatus());
		issueRequest.setImages(old); 
	}
	
	private void assertBadRequest() throws Exception {
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(401, response.getStatus());;
	    verify(issueDAO, never()).insertIssue(anyLong(), any(), any());
	    verify(fileUploader, never()).uploadFile(any(), anyLong(), anyLong());
	}
	
	@Test
	void TK001_tokenNull_401() throws Exception {
	    String token = null;
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, token);
	    assertEquals(401, response.getStatus());
	    verify(issueDAO, never()).insertIssue(anyLong(), any(), any());
	    verify(fileUploader, never()).uploadFile(any(), anyLong(), anyLong());
	}

	@Test
	void TK002_tokenScaduto_401() throws Exception {
	    when(validator.validateUserTokenAndGetID(anyString()))
	            .thenThrow(new JWTVerificationException("Token scaduto"));
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(401, response.getStatus());
	    verify(issueDAO, never()).insertIssue(anyLong(), any(), any());
	    verify(fileUploader, never()).uploadFile(any(), anyLong(), anyLong());
	}

	@Test
	void TK003_firmaInvalida_401() throws Exception {
	    when(validator.validateUserTokenAndGetID(anyString()))
	            .thenThrow(new JWTVerificationException("Token non valido"));
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    assertEquals(401, response.getStatus());
	    verify(issueDAO, never()).insertIssue(anyLong(), any(), any());
	    verify(fileUploader, never()).uploadFile(any(), anyLong(), anyLong());
	}

	@Test
	void DB001_databaseOffline() throws Exception {
	    when(issueDAO.insertIssue(anyLong(), any(), any()))
	            .thenThrow(new RuntimeException("DB offline"));

	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);

	    assertEquals(500, response.getStatus());
	    verify(fileUploader, never()).uploadFile(any(), anyLong(), anyLong());
	}
	
	@Test
	void UP001_uploadFallito_207() throws Exception {
	    when(issueDAO.insertIssue(anyLong(), any(), any()))
            .thenReturn(123L);
	    when(fileUploader.uploadFile(any(), anyLong(), anyLong()))
            .thenThrow(new FileNotUploadedException("Errore caricamento immagini"));
	    when(validator.validateUserTokenAndGetID(any())).thenReturn(1L);
	    issueRequest.setImages(this.images);
	    Response response = SegnalazioneIssue.segnalaIssue(issueRequest, validToken);
	    
	    assertEquals(207, response.getStatus());
	    verify(issueDAO, times(1)).insertIssue(anyLong(), any(), any());
	    verify(fileUploader, atLeastOnce()).uploadFile(any(), anyLong(), anyLong());
	    issueRequest.setImages(null);
	}
}
