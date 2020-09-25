package br.com.fiap.Nac.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.Nac.Model.Nac;
import br.com.fiap.Nac.repository.NacRepository;

@Controller
@RequestMapping("/nac")
public class NacController {
	
	@Autowired
	private NacRepository repository;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping()
	public ModelAndView users() {
		List<Nac> nacs = repository.findAll();
		ModelAndView modelAndView = new ModelAndView("nacs");
		modelAndView.addObject("nacs", nacs);
		return modelAndView;
	}

	@PostMapping()
	public String save(@Valid Nac nac, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors())
			return "nac_new";
		repository.save(nac);
		attributes.addFlashAttribute("message", getMessage("message.newnac.success"));
		return "redirect:/nac";
	}

	@RequestMapping("new")
	public String formUser(Nac nac) {
		return "nac_new";
	}

	@GetMapping("delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		repository.deleteById(id);
		return "redirect:/nac";
	}
	
	@GetMapping("/{id}")
	public ModelAndView editNacForm(@PathVariable Long id) {
		Optional<Nac> nac = repository.findById(id);
		ModelAndView modelAndView = new ModelAndView("nac_edit");
		modelAndView.addObject("nac", nac);
		return modelAndView;		
	}

	@PostMapping("/update")
	public String updateUser(@Valid Nac nac, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) return "nac_edit";
		repository.save(nac);
		redirect.addFlashAttribute("message", getMessage("message.editnac.success"));
		return "redirect:/nac"; 
	}
	
	private String getMessage(String code) {
		return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}
}
