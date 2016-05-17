package org.marks.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import org.marks.bean.DisciplineParams;
import org.marks.bean.DisciplinesSemestr;
import org.marks.test.DisciplineParamsFactoryForTest;
import org.marks.test.DisciplinesSemestrFactoryForTest;

//--- Services 
import org.marks.business.service.DisciplineParamsService;
import org.marks.business.service.DisciplinesSemestrService;

//--- List Items 
import org.marks.web.listitem.DisciplinesSemestrListItem;

import org.marks.web.common.Message;
import org.marks.web.common.MessageHelper;
import org.marks.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class DisciplineParamsControllerTest {
	
	@InjectMocks
	private DisciplineParamsController disciplineParamsController;
	@Mock
	private DisciplineParamsService disciplineParamsService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private DisciplinesSemestrService disciplinesSemestrService; // Injected by Spring

	private DisciplineParamsFactoryForTest disciplineParamsFactoryForTest = new DisciplineParamsFactoryForTest();
	private DisciplinesSemestrFactoryForTest disciplinesSemestrFactoryForTest = new DisciplinesSemestrFactoryForTest();

	List<DisciplinesSemestr> disciplinesSemestrs = new ArrayList<DisciplinesSemestr>();

	private void givenPopulateModel() {
		DisciplinesSemestr disciplinesSemestr1 = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		DisciplinesSemestr disciplinesSemestr2 = disciplinesSemestrFactoryForTest.newDisciplinesSemestr();
		List<DisciplinesSemestr> disciplinesSemestrs = new ArrayList<DisciplinesSemestr>();
		disciplinesSemestrs.add(disciplinesSemestr1);
		disciplinesSemestrs.add(disciplinesSemestr2);
		when(disciplinesSemestrService.findAll()).thenReturn(disciplinesSemestrs);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<DisciplineParams> list = new ArrayList<DisciplineParams>();
		when(disciplineParamsService.findAll()).thenReturn(list);
		
		// When
		String viewName = disciplineParamsController.list(model);
		
		// Then
		assertEquals("disciplineParams/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = disciplineParamsController.formForCreate(model);
		
		// Then
		assertEquals("disciplineParams/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((DisciplineParams)modelMap.get("disciplineParams")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/disciplineParams/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DisciplinesSemestrListItem> disciplinesSemestrListItems = (List<DisciplinesSemestrListItem>) modelMap.get("listOfDisciplinesSemestrItems");
		assertEquals(2, disciplinesSemestrListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DisciplineParams disciplineParams = disciplineParamsFactoryForTest.newDisciplineParams();
		Integer id = disciplineParams.getId();
		when(disciplineParamsService.findById(id)).thenReturn(disciplineParams);
		
		// When
		String viewName = disciplineParamsController.formForUpdate(model, id);
		
		// Then
		assertEquals("disciplineParams/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplineParams, (DisciplineParams) modelMap.get("disciplineParams"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/disciplineParams/update", modelMap.get("saveAction"));
		
		List<DisciplinesSemestrListItem> disciplinesSemestrListItems = (List<DisciplinesSemestrListItem>) modelMap.get("listOfDisciplinesSemestrItems");
		assertEquals(2, disciplinesSemestrListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DisciplineParams disciplineParams = disciplineParamsFactoryForTest.newDisciplineParams();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DisciplineParams disciplineParamsCreated = new DisciplineParams();
		when(disciplineParamsService.create(disciplineParams)).thenReturn(disciplineParamsCreated); 
		
		// When
		String viewName = disciplineParamsController.create(disciplineParams, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/disciplineParams/form/"+disciplineParams.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplineParamsCreated, (DisciplineParams) modelMap.get("disciplineParams"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DisciplineParams disciplineParams = disciplineParamsFactoryForTest.newDisciplineParams();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = disciplineParamsController.create(disciplineParams, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplineParams/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplineParams, (DisciplineParams) modelMap.get("disciplineParams"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/disciplineParams/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DisciplinesSemestrListItem> disciplinesSemestrListItems = (List<DisciplinesSemestrListItem>) modelMap.get("listOfDisciplinesSemestrItems");
		assertEquals(2, disciplinesSemestrListItems.size());
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		DisciplineParams disciplineParams = disciplineParamsFactoryForTest.newDisciplineParams();
		
		Exception exception = new RuntimeException("test exception");
		when(disciplineParamsService.create(disciplineParams)).thenThrow(exception);
		
		// When
		String viewName = disciplineParamsController.create(disciplineParams, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplineParams/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplineParams, (DisciplineParams) modelMap.get("disciplineParams"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/disciplineParams/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "disciplineParams.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<DisciplinesSemestrListItem> disciplinesSemestrListItems = (List<DisciplinesSemestrListItem>) modelMap.get("listOfDisciplinesSemestrItems");
		assertEquals(2, disciplinesSemestrListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DisciplineParams disciplineParams = disciplineParamsFactoryForTest.newDisciplineParams();
		Integer id = disciplineParams.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DisciplineParams disciplineParamsSaved = new DisciplineParams();
		disciplineParamsSaved.setId(id);
		when(disciplineParamsService.update(disciplineParams)).thenReturn(disciplineParamsSaved); 
		
		// When
		String viewName = disciplineParamsController.update(disciplineParams, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/disciplineParams/form/"+disciplineParams.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplineParamsSaved, (DisciplineParams) modelMap.get("disciplineParams"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DisciplineParams disciplineParams = disciplineParamsFactoryForTest.newDisciplineParams();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = disciplineParamsController.update(disciplineParams, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplineParams/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplineParams, (DisciplineParams) modelMap.get("disciplineParams"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/disciplineParams/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DisciplinesSemestrListItem> disciplinesSemestrListItems = (List<DisciplinesSemestrListItem>) modelMap.get("listOfDisciplinesSemestrItems");
		assertEquals(2, disciplinesSemestrListItems.size());
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		DisciplineParams disciplineParams = disciplineParamsFactoryForTest.newDisciplineParams();
		
		Exception exception = new RuntimeException("test exception");
		when(disciplineParamsService.update(disciplineParams)).thenThrow(exception);
		
		// When
		String viewName = disciplineParamsController.update(disciplineParams, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("disciplineParams/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(disciplineParams, (DisciplineParams) modelMap.get("disciplineParams"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/disciplineParams/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "disciplineParams.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<DisciplinesSemestrListItem> disciplinesSemestrListItems = (List<DisciplinesSemestrListItem>) modelMap.get("listOfDisciplinesSemestrItems");
		assertEquals(2, disciplinesSemestrListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DisciplineParams disciplineParams = disciplineParamsFactoryForTest.newDisciplineParams();
		Integer id = disciplineParams.getId();
		
		// When
		String viewName = disciplineParamsController.delete(redirectAttributes, id);
		
		// Then
		verify(disciplineParamsService).delete(id);
		assertEquals("redirect:/disciplineParams", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DisciplineParams disciplineParams = disciplineParamsFactoryForTest.newDisciplineParams();
		Integer id = disciplineParams.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(disciplineParamsService).delete(id);
		
		// When
		String viewName = disciplineParamsController.delete(redirectAttributes, id);
		
		// Then
		verify(disciplineParamsService).delete(id);
		assertEquals("redirect:/disciplineParams", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "disciplineParams.error.delete", exception);
	}
	
	
}
