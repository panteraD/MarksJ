/*
 * Created on 17 May 2016 ( Time 04:14:22 )
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
import org.marks.bean.Roles;
import org.marks.business.service.RolesService;
import org.marks.web.listitem.RolesListItem;

/**
 * Spring MVC controller for 'Roles' management.
 */
@Controller
public class RolesRestController {

	@Resource
	private RolesService rolesService;
	
	@RequestMapping( value="/items/roles",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<RolesListItem> findAllAsListItems() {
		List<Roles> list = rolesService.findAll();
		List<RolesListItem> items = new LinkedList<RolesListItem>();
		for ( Roles roles : list ) {
			items.add(new RolesListItem( roles ) );
		}
		return items;
	}
	
	@RequestMapping( value="/roles",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Roles> findAll() {
		return rolesService.findAll();
	}

	@RequestMapping( value="/roles/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Roles findOne(@PathVariable("id") Integer id) {
		return rolesService.findById(id);
	}
	
	@RequestMapping( value="/roles",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Roles create(@RequestBody Roles roles) {
		return rolesService.create(roles);
	}

	@RequestMapping( value="/roles/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Roles update(@PathVariable("id") Integer id, @RequestBody Roles roles) {
		return rolesService.update(roles);
	}

	@RequestMapping( value="/roles/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		rolesService.delete(id);
	}
	
}