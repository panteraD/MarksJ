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
import org.marks.bean.Disciplines;
import org.marks.business.service.DisciplinesService;
import org.marks.web.listitem.DisciplinesListItem;

/**
 * Spring MVC controller for 'Disciplines' management.
 */
@Controller
public class DisciplinesRestController {

	@Resource
	private DisciplinesService disciplinesService;
	
	@RequestMapping( value="/items/disciplines",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<DisciplinesListItem> findAllAsListItems() {
		List<Disciplines> list = disciplinesService.findAll();
		List<DisciplinesListItem> items = new LinkedList<DisciplinesListItem>();
		for ( Disciplines disciplines : list ) {
			items.add(new DisciplinesListItem( disciplines ) );
		}
		return items;
	}
	
	@RequestMapping( value="/disciplines",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Disciplines> findAll() {
		return disciplinesService.findAll();
	}

	@RequestMapping( value="/disciplines/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Disciplines findOne(@PathVariable("id") Integer id) {
		return disciplinesService.findById(id);
	}
	
	@RequestMapping( value="/disciplines",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Disciplines create(@RequestBody Disciplines disciplines) {
		return disciplinesService.create(disciplines);
	}

	@RequestMapping( value="/disciplines/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Disciplines update(@PathVariable("id") Integer id, @RequestBody Disciplines disciplines) {
		return disciplinesService.update(disciplines);
	}

	@RequestMapping( value="/disciplines/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		disciplinesService.delete(id);
	}
	
}
