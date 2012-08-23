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

import com.canoepro2.domain.Athlete;
import com.canoepro2.domain.BandAthlete;

@RequestMapping("/athletes")
@Controller
public class AthleteController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Athlete athlete, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, athlete);
			return "athletes/create";
		}
		uiModel.asMap().clear();
		athlete.persist();
		return "redirect:/athletes/" + encodeUrlPathSegment(athlete.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Athlete());
		return "athletes/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("athlete", Athlete.findAthlete(id));
		uiModel.addAttribute("itemId", id);
		return "athletes/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("athletes", Athlete.findAthleteEntries(firstResult, sizeNo));
			float nrOfPages = (float) Athlete.countAthletes() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		} else {
			uiModel.addAttribute("athletes", Athlete.findAllAthletes());
		}
		addDateTimeFormatPatterns(uiModel);
		return "athletes/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Athlete athlete, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, athlete);
			return "athletes/update";
		}
		uiModel.asMap().clear();
		athlete.merge();
		return "redirect:/athletes/" + encodeUrlPathSegment(athlete.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, Athlete.findAthlete(id));
		return "athletes/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Athlete athlete = Athlete.findAthlete(id);
		athlete.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/athletes";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("athlete_birthdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, Athlete athlete) {
		uiModel.addAttribute("athlete", athlete);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("bandathletes", BandAthlete.findAllBandAthletes());
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
