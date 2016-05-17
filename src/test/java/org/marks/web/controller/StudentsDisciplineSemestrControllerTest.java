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
import org.marks.bean.StudentsDisciplineSemestr;
import org.marks.bean.DisciplineParams;
import org.marks.bean.Students;
import org.marks.test.StudentsDisciplineSemestrFactoryForTest;
import org.marks.test.DisciplineParamsFactoryForTest;
import org.marks.test.StudentsFactoryForTest;

//--- Services 
import org.marks.business.service.StudentsDisciplineSemestrService;
import org.marks.business.service.DisciplineParamsService;
import org.marks.business.service.StudentsService;

//--- List Items 
import org.marks.web.listitem.DisciplineParamsListItem;
import org.marks.web.listitem.StudentsListItem;

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
public class StudentsDisciplineSemestrControllerTest {
	
	@InjectMocks
	private StudentsDisciplineSemestrController studentsDisciplineSemestrController;
	@Mock
	private StudentsDisciplineSemestrService studentsDisciplineSemestrService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private DisciplineParamsService disciplineParamsService; // Injected by Spring
	@Mock
	private StudentsService studentsService; // Injected by Spring

	private StudentsDisciplineSemestrFactoryForTest studentsDisciplineSemestrFactoryForTest = new StudentsDisciplineSemestrFactoryForTest();
	private DisciplineParamsFactoryForTest disciplineParamsFactoryForTest = new DisciplineParamsFactoryForTest();
	private StudentsFactoryForTest studentsFactoryForTest = new StudentsFactoryForTest();

	List<DisciplineParams> disciplineParamss = new ArrayList<DisciplineParams>();
	List<Students> studentss = new ArrayList<Students>();

	private void givenPopulateModel() {
		DisciplineParams disciplineParams1 = disciplineParamsFactoryForTest.newDisciplineParams();
		DisciplineParams disciplineParams2 = disciplineParamsFactoryForTest.newDisciplineParams();
		List<DisciplineParams> disciplineParamss = new ArrayList<DisciplineParams>();
		disciplineParamss.add(disciplineParams1);
		disciplineParamss.add(disciplineParams2);
		when(disciplineParamsService.findAll()).thenReturn(disciplineParamss);

		Students students1 = studentsFactoryForTest.newStudents();
		Students students2 = studentsFactoryForTest.newStudents();
		List<Students> studentss = new ArrayList<Students>();
		studentss.add(students1);
		studentss.add(students2);
		when(studentsService.findAll()).thenReturn(studentss);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<StudentsDisciplineSemestr> list = new ArrayList<StudentsDisciplineSemestr>();
		when(studentsDisciplineSemestrService.findAll()).thenReturn(list);
		
		// When
		String viewName = studentsDisciplineSemestrController.list(model);
		
		// Then
		assertEquals("studentsDisciplineSemestr/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = studentsDisciplineSemestrController.formForCreate(model);
		
		// Then
		assertEquals("studentsDisciplineSemestr/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((StudentsDisciplineSemestr)modelMap.get("studentsDisciplineSemestr")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/studentsDisciplineSemestr/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DisciplineParamsListItem> disciplineParamsListItems = (List<DisciplineParamsListItem>) modelMap.get("listOfDisciplineParamsItems");
		assertEquals(2, disciplineParamsListItems.size());
		
		@SuppressWarnings("unchecked")
		List<StudentsListItem> studentsListItems = (List<StudentsListItem>) modelMap.get("listOfStudentsItems");
		assertEquals(2, studentsListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrFactoryForTest.newStudentsDisciplineSemestr();
		Integer id = studentsDisciplineSemestr.getId();
		when(studentsDisciplineSemestrService.findById(id)).thenReturn(studentsDisciplineSemestr);
		
		// When
		String viewName = studentsDisciplineSemestrController.formForUpdate(model, id);
		
		// Then
		assertEquals("studentsDisciplineSemestr/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(studentsDisciplineSemestr, (StudentsDisciplineSemestr) modelMap.get("studentsDisciplineSemestr"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/studentsDisciplineSemestr/update", modelMap.get("saveAction"));
		
		List<StudentsListItem> studentsListItems = (List<StudentsListItem>) modelMap.get("listOfStudentsItems");
		assertEquals(2, studentsListItems.size());
		
		List<DisciplineParamsListItem> disciplineParamsListItems = (List<DisciplineParamsListItem>) modelMap.get("listOfDisciplineParamsItems");
		assertEquals(2, disciplineParamsListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrFactoryForTest.newStudentsDisciplineSemestr();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		StudentsDisciplineSemestr studentsDisciplineSemestrCreated = new StudentsDisciplineSemestr();
		when(studentsDisciplineSemestrService.create(studentsDisciplineSemestr)).thenReturn(studentsDisciplineSemestrCreated); 
		
		// When
		String viewName = studentsDisciplineSemestrController.create(studentsDisciplineSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/studentsDisciplineSemestr/form/"+studentsDisciplineSemestr.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(studentsDisciplineSemestrCreated, (StudentsDisciplineSemestr) modelMap.get("studentsDisciplineSemestr"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrFactoryForTest.newStudentsDisciplineSemestr();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = studentsDisciplineSemestrController.create(studentsDisciplineSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("studentsDisciplineSemestr/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(studentsDisciplineSemestr, (StudentsDisciplineSemestr) modelMap.get("studentsDisciplineSemestr"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/studentsDisciplineSemestr/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DisciplineParamsListItem> disciplineParamsListItems = (List<DisciplineParamsListItem>) modelMap.get("listOfDisciplineParamsItems");
		assertEquals(2, disciplineParamsListItems.size());
		
		@SuppressWarnings("unchecked")
		List<StudentsListItem> studentsListItems = (List<StudentsListItem>) modelMap.get("listOfStudentsItems");
		assertEquals(2, studentsListItems.size());
		
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

		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrFactoryForTest.newStudentsDisciplineSemestr();
		
		Exception exception = new RuntimeException("test exception");
		when(studentsDisciplineSemestrService.create(studentsDisciplineSemestr)).thenThrow(exception);
		
		// When
		String viewName = studentsDisciplineSemestrController.create(studentsDisciplineSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("studentsDisciplineSemestr/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(studentsDisciplineSemestr, (StudentsDisciplineSemestr) modelMap.get("studentsDisciplineSemestr"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/studentsDisciplineSemestr/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "studentsDisciplineSemestr.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<DisciplineParamsListItem> disciplineParamsListItems = (List<DisciplineParamsListItem>) modelMap.get("listOfDisciplineParamsItems");
		assertEquals(2, disciplineParamsListItems.size());
		
		@SuppressWarnings("unchecked")
		List<StudentsListItem> studentsListItems = (List<StudentsListItem>) modelMap.get("listOfStudentsItems");
		assertEquals(2, studentsListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrFactoryForTest.newStudentsDisciplineSemestr();
		Integer id = studentsDisciplineSemestr.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		StudentsDisciplineSemestr studentsDisciplineSemestrSaved = new StudentsDisciplineSemestr();
		studentsDisciplineSemestrSaved.setId(id);
		when(studentsDisciplineSemestrService.update(studentsDisciplineSemestr)).thenReturn(studentsDisciplineSemestrSaved); 
		
		// When
		String viewName = studentsDisciplineSemestrController.update(studentsDisciplineSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/studentsDisciplineSemestr/form/"+studentsDisciplineSemestr.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(studentsDisciplineSemestrSaved, (StudentsDisciplineSemestr) modelMap.get("studentsDisciplineSemestr"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrFactoryForTest.newStudentsDisciplineSemestr();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = studentsDisciplineSemestrController.update(studentsDisciplineSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("studentsDisciplineSemestr/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(studentsDisciplineSemestr, (StudentsDisciplineSemestr) modelMap.get("studentsDisciplineSemestr"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/studentsDisciplineSemestr/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<StudentsListItem> studentsListItems = (List<StudentsListItem>) modelMap.get("listOfStudentsItems");
		assertEquals(2, studentsListItems.size());
		
		@SuppressWarnings("unchecked")
		List<DisciplineParamsListItem> disciplineParamsListItems = (List<DisciplineParamsListItem>) modelMap.get("listOfDisciplineParamsItems");
		assertEquals(2, disciplineParamsListItems.size());
		
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

		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrFactoryForTest.newStudentsDisciplineSemestr();
		
		Exception exception = new RuntimeException("test exception");
		when(studentsDisciplineSemestrService.update(studentsDisciplineSemestr)).thenThrow(exception);
		
		// When
		String viewName = studentsDisciplineSemestrController.update(studentsDisciplineSemestr, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("studentsDisciplineSemestr/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(studentsDisciplineSemestr, (StudentsDisciplineSemestr) modelMap.get("studentsDisciplineSemestr"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/studentsDisciplineSemestr/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "studentsDisciplineSemestr.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<StudentsListItem> studentsListItems = (List<StudentsListItem>) modelMap.get("listOfStudentsItems");
		assertEquals(2, studentsListItems.size());
		
		@SuppressWarnings("unchecked")
		List<DisciplineParamsListItem> disciplineParamsListItems = (List<DisciplineParamsListItem>) modelMap.get("listOfDisciplineParamsItems");
		assertEquals(2, disciplineParamsListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrFactoryForTest.newStudentsDisciplineSemestr();
		Integer id = studentsDisciplineSemestr.getId();
		
		// When
		String viewName = studentsDisciplineSemestrController.delete(redirectAttributes, id);
		
		// Then
		verify(studentsDisciplineSemestrService).delete(id);
		assertEquals("redirect:/studentsDisciplineSemestr", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		StudentsDisciplineSemestr studentsDisciplineSemestr = studentsDisciplineSemestrFactoryForTest.newStudentsDisciplineSemestr();
		Integer id = studentsDisciplineSemestr.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(studentsDisciplineSemestrService).delete(id);
		
		// When
		String viewName = studentsDisciplineSemestrController.delete(redirectAttributes, id);
		
		// Then
		verify(studentsDisciplineSemestrService).delete(id);
		assertEquals("redirect:/studentsDisciplineSemestr", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "studentsDisciplineSemestr.error.delete", exception);
	}
	
	
}
