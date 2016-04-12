package cat.gencat.demoadminlogs.endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cat.gencat.demoadminlogs.model.Equipament;
import cat.gencat.demoadminlogs.service.EquipamentService;
import cat.gencat.demoadminlogs.util.PaginationUtils;

@Controller
@RequestMapping("/equipaments")
public class EquipamentServiceController {

	@Autowired
	EquipamentService equipamentService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findPaginated(
			@RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
			@RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp,
			@RequestParam(defaultValue = "id", value = "sortField", required = false) String sortField,
			@RequestParam(defaultValue = "asc", value = "sortDirection", required = false) String sortDirection,
			@RequestParam(value = "filter", required = false) String filter) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Equipament> equipaments = equipamentService.findPaginated(page,
				rpp, sortField, sortDirection, filter);
		responseMap.put("equipaments", equipaments);

		int pages = PaginationUtils.calculateNumberOfPages(equipamentService.count(filter), rpp);
		responseMap.put("pages", pages);

		return responseMap;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Equipament getEquipament(@PathVariable("id") Long equipamentId) {
		return equipamentService.getEquipament(equipamentId);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type=application/json")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEquipament(@RequestBody Equipament equipament)
			throws Exception {
		equipamentService.saveEquipament(equipament);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEquipamentFromForm(@ModelAttribute Equipament equipament)
			throws Exception {
		equipamentService.saveEquipament(equipament);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateEquipament(@RequestBody Equipament equipament)
			throws Exception {
		equipamentService.updateEquipament(equipament);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ResponseBody
	public void deleteEquipament(@PathVariable("id") Long equipamentId)
			throws Exception {
		equipamentService.deleteEquipament(equipamentId);
	}

}
