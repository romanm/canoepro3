package com.canoepro2.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.canoepro2.domain.Band;
import com.canoepro2.domain.Boot;
import com.canoepro2.domain.Competition;
import com.canoepro2.domain.Distance;
import com.canoepro2.domain.Gender;
import com.canoepro2.domain.Oldgroup;
import com.canoepro2.domain.Race;
import com.canoepro2.domain.Racetype;

@RequestMapping("/races")
@Controller
public class RaceController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Race race, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, race);
			return "races/create";
		}
		uiModel.asMap().clear();
		race.persist();
		return "redirect:/races/" + encodeUrlPathSegment(race.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Race());
		return "races/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("race", Race.findRace(id));
		uiModel.addAttribute("itemId", id);
		return "races/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("races", Race.findRaceEntries(firstResult, sizeNo));
			float nrOfPages = (float) Race.countRaces() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		} else {
			uiModel.addAttribute("races", Race.findAllRaces());
		}
		addDateTimeFormatPatterns(uiModel);
		return "races/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Race race, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, race);
			return "races/update";
		}
		uiModel.asMap().clear();
		race.merge();
		return "redirect:/races/" + encodeUrlPathSegment(race.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		System.out.println(".....................1 -- "+id);
		Race findRace = Race.findRace(id);
		System.out.println(".....................2 "+findRace);
		populateEditForm(uiModel, findRace);
		System.out.println(".....................3");
		return "races/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Race race = Race.findRace(id);
		race.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/races";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("race_start_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, Race race) {
		uiModel.addAttribute("race", race);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("bands", Band.findAllBands());
		uiModel.addAttribute("boots", Boot.findAllBoots());
		uiModel.addAttribute("competitions", Competition.findAllCompetitions());
		uiModel.addAttribute("distances", Distance.findAllDistances());
		uiModel.addAttribute("genders", Gender.findAllGenders());
		uiModel.addAttribute("oldgroups", Oldgroup.findAllOldgroups());
		uiModel.addAttribute("racetypes", Racetype.findAllRacetypes());
	}

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {}
		return pathSegment;
	}
}
