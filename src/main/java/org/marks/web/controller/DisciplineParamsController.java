/*
 * Created on 17 May 2016 ( Time 04:14:21 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//--- Common classes
import org.marks.web.common.AbstractController;
import org.marks.web.common.FormMode;
import org.marks.web.common.Message;
import org.marks.web.common.MessageType;

//--- Entities
import org.marks.bean.DisciplineParams;
import org.marks.bean.DisciplinesSemestr;

//--- Services 
import org.marks.business.service.DisciplineParamsService;
import org.marks.business.service.DisciplinesSemestrService;

//--- List Items 
import org.marks.web.listitem.DisciplinesSemestrListItem;

/**
 * Spring MVC controller for 'DisciplineParams' management.
 */
@Controller
@RequestMapping("/disciplineParams")
public class DisciplineParamsController extends AbstractController {

	//--- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "disciplineParams";
	private static final String MAIN_LIST_NAME   = "list";

	//--- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM   = "disciplineParams/form";
	private static final String JSP_LIST   = "disciplineParams/list";

	//--- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE   = "/disciplineParams/create";
	private static final String SAVE_ACTION_UPDATE   = "/disciplineParams/update";

	//--- Main entity service
	@Resource
    private DisciplineParamsService disciplineParamsService; // Injected by Spring
	//--- Other service(s)
	@Resource
    private DisciplinesSemestrService disciplinesSemestrService; // Injected by Spring

	//--------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public DisciplineParamsController() {
		super(DisciplineParamsController.class, MAIN_ENTITY_NAME );
		log("DisciplineParamsController created.");
	}

	//--------------------------------------------------------------------------------------
	// Spring MVC model management
	//--------------------------------------------------------------------------------------
	/**
	 * Populates the combo-box "items" for the referenced entity "DisciplinesSemestr"
	 * @param model
	 */
	private void populateListOfDisciplinesSemestrItems(Model model) {
		List<DisciplinesSemestr> list = disciplinesSemestrService.findAll();
		List<DisciplinesSemestrListItem> items = new LinkedList<DisciplinesSemestrListItem>();
		for ( DisciplinesSemestr disciplinesSemestr : list ) {
			items.add(new DisciplinesSemestrListItem( disciplinesSemestr ) );
		}
		model.addAttribute("listOfDisciplinesSemestrItems", items ) ;
	}


	/**
	 * Populates the Spring MVC model with the given entity and eventually other useful data
	 * @param model
	 * @param disciplineParams
	 */
	private void populateModel(Model model, DisciplineParams disciplineParams, FormMode formMode) {
		//--- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, disciplineParams);
		if ( formMode == FormMode.CREATE ) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE); 			
			//--- Other data useful in this screen in "create" mode (all fields)
			populateListOfDisciplinesSemestrItems(model);
		}
		else if ( formMode == FormMode.UPDATE ) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE); 			
			//--- Other data useful in this screen in "update" mode (only non-pk fields)
			populateListOfDisciplinesSemestrItems(model);
		}
	}

	//--------------------------------------------------------------------------------------
	// Request mapping
	//--------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of DisciplineParams found in the database
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		log("Action 'list'");
		List<DisciplineParams> list = disciplineParamsService.findAll();
		model.addAttribute(MAIN_LIST_NAME, list);		
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new DisciplineParams
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model) {
		log("Action 'formForCreate'");
		//--- Populates the model with a new instance
		DisciplineParams disciplineParams = new DisciplineParams();	
		populateModel( model, disciplineParams, FormMode.CREATE);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing DisciplineParams
	 * @param model Spring MVC model
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{id}")
	public String formForUpdate(Model model, @PathVariable("id") Integer id ) {
		log("Action 'formForUpdate'");
		//--- Search the entity by its primary key and stores it in the model 
		DisciplineParams disciplineParams = disciplineParamsService.findById(id);
		populateModel( model, disciplineParams, FormMode.UPDATE);		
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param disciplineParams  entity to be created
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create" ) // GET or POST
	public String create(@Valid DisciplineParams disciplineParams, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {
				DisciplineParams disciplineParamsCreated = disciplineParamsService.create(disciplineParams); 
				model.addAttribute(MAIN_ENTITY_NAME, disciplineParamsCreated);

				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return redirectToForm(httpServletRequest, disciplineParams.getId() );
			} else {
				populateModel( model, disciplineParams, FormMode.CREATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			log("Action 'create' : Exception - " + e.getMessage() );
			messageHelper.addException(model, "disciplineParams.error.create", e);
			populateModel( model, disciplineParams, FormMode.CREATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'UPDATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param disciplineParams  entity to be updated
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/update" ) // GET or POST
	public String update(@Valid DisciplineParams disciplineParams, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'update'");
		try {
			if (!bindingResult.hasErrors()) {
				//--- Perform database operations
				DisciplineParams disciplineParamsSaved = disciplineParamsService.update(disciplineParams);
				model.addAttribute(MAIN_ENTITY_NAME, disciplineParamsSaved);
				//--- Set the result message
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				log("Action 'update' : update done - redirect");
				return redirectToForm(httpServletRequest, disciplineParams.getId());
			} else {
				log("Action 'update' : binding errors");
				populateModel( model, disciplineParams, FormMode.UPDATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			messageHelper.addException(model, "disciplineParams.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage() );
			populateModel( model, disciplineParams, FormMode.UPDATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param redirectAttributes
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}") // GET or POST
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
		log("Action 'delete'" );
		try {
			disciplineParamsService.delete( id );
			//--- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));	
		} catch(Exception e) {
			messageHelper.addException(redirectAttributes, "disciplineParams.error.delete", e);
		}
		return redirectToList();
	}

}