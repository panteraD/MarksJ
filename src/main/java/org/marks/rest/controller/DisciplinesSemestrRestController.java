/*
 * Created on 17 May 2016 ( Time 04:14:21 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.marks.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.marks.bean.DisciplinesSemestr;
import org.marks.business.service.DisciplinesSemestrService;
import org.marks.web.listitem.DisciplinesSemestrListItem;

/**
 * Spring MVC controller for 'DisciplinesSemestr' management.
 */
@Controller
public class DisciplinesSemestrRestController {

	@Resource
	private DisciplinesSemestrService disciplinesSemestrService;
	
	@RequestMapping( value="/items/disciplinesSemestr",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<DisciplinesSemestrListItem> findAllAsListItems() {
		List<DisciplinesSemestr> list = disciplinesSemestrService.findAll();
		List<DisciplinesSemestrListItem> items = new LinkedList<DisciplinesSemestrListItem>();
		for ( DisciplinesSemestr disciplinesSemestr : list ) {
			items.add(new DisciplinesSemestrListItem( disciplinesSemestr ) );
		}
		return items;
	}
	
	@RequestMapping( value="/disciplinesSemestr",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<DisciplinesSemestr> findAll() {
		return disciplinesSemestrService.findAll();
	}

	@RequestMapping( value="/disciplinesSemestr/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DisciplinesSemestr findOne(@PathVariable("id") Integer id) {
		return disciplinesSemestrService.findById(id);
	}
	
	@RequestMapping( value="/disciplinesSemestr",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DisciplinesSemestr create(@RequestBody DisciplinesSemestr disciplinesSemestr) {
		return disciplinesSemestrService.create(disciplinesSemestr);
	}

	@RequestMapping( value="/disciplinesSemestr/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public DisciplinesSemestr update(@PathVariable("id") Integer id, @RequestBody DisciplinesSemestr disciplinesSemestr) {
		return disciplinesSemestrService.update(disciplinesSemestr);
	}

	@RequestMapping( value="/disciplinesSemestr/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		disciplinesSemestrService.delete(id);
	}
	
}