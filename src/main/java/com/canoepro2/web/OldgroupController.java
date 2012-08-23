package com.canoepro2.web;

import com.canoepro2.domain.Oldgroup;
import com.canoepro2.domain.Race;
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

@RequestMapping("/oldgroups")
@Controller
@RooWebScaffold(path = "oldgroups", formBackingObject = Oldgroup.class)
public class OldgroupController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Oldgroup oldgroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, oldgroup);
            return "oldgroups/create";
        }
        uiModel.asMap().clear();
        oldgroup.persist();
        return "redirect:/oldgroups/" + encodeUrlPathSegment(oldgroup.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Oldgroup());
        return "oldgroups/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("oldgroup", Oldgroup.findOldgroup(id));
        uiModel.addAttribute("itemId", id);
        return "oldgroups/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("oldgroups", Oldgroup.findOldgroupEntries(firstResult, sizeNo));
            float nrOfPages = (float) Oldgroup.countOldgroups() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("oldgroups", Oldgroup.findAllOldgroups());
        }
        return "oldgroups/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Oldgroup oldgroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, oldgroup);
            return "oldgroups/update";
        }
        uiModel.asMap().clear();
        oldgroup.merge();
        return "redirect:/oldgroups/" + encodeUrlPathSegment(oldgroup.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, Oldgroup.findOldgroup(id));
        return "oldgroups/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Oldgroup oldgroup = Oldgroup.findOldgroup(id);
        oldgroup.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/oldgroups";
    }

	void populateEditForm(Model uiModel, Oldgroup oldgroup) {
        uiModel.addAttribute("oldgroup", oldgroup);
        uiModel.addAttribute("races", Race.findAllRaces());
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
