package com.canoepro2.web;

import com.canoepro2.domain.Athlete;
import com.canoepro2.domain.Band;
import com.canoepro2.domain.BandAthlete;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/bandathletes")
@Controller
@RooWebScaffold(path = "bandathletes", formBackingObject = BandAthlete.class)
public class BandAthleteController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid BandAthlete bandAthlete, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bandAthlete);
            return "bandathletes/create";
        }
        uiModel.asMap().clear();
        bandAthlete.persist();
        return "redirect:/bandathletes/" + encodeUrlPathSegment(bandAthlete.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new BandAthlete());
        return "bandathletes/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("bandathlete", BandAthlete.findBandAthlete(id));
        uiModel.addAttribute("itemId", id);
        return "bandathletes/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("bandathletes", BandAthlete.findBandAthleteEntries(firstResult, sizeNo));
            float nrOfPages = (float) BandAthlete.countBandAthletes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("bandathletes", BandAthlete.findAllBandAthletes());
        }
        return "bandathletes/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid BandAthlete bandAthlete, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bandAthlete);
            return "bandathletes/update";
        }
        uiModel.asMap().clear();
        bandAthlete.merge();
        return "redirect:/bandathletes/" + encodeUrlPathSegment(bandAthlete.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, BandAthlete.findBandAthlete(id));
        return "bandathletes/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        BandAthlete bandAthlete = BandAthlete.findBandAthlete(id);
        bandAthlete.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/bandathletes";
    }

	void populateEditForm(Model uiModel, BandAthlete bandAthlete) {
        uiModel.addAttribute("bandAthlete", bandAthlete);
        uiModel.addAttribute("athletes", Athlete.findAllAthletes());
        uiModel.addAttribute("bands", Band.findAllBands());
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
