package uz.sud.setting.modules.language.controller;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import uz.sud.setting.modules.language.dto.LanguageCreateDTO;
import uz.sud.setting.modules.language.dto.LanguageResponseDTO;
import uz.sud.setting.modules.language.dto.LanguageUpdateDTO;
import uz.sud.setting.modules.language.service.LanguageService;

@Path("/v1/languages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LanguageController {

    private final LanguageService service;

    public LanguageController(LanguageService service) {
        this.service = service;
    }

    @POST
    @Path("/create")
    public LanguageResponseDTO create(LanguageCreateDTO dto) {
        return service.create(dto);
    }

    @GET
    @Path("/main")
    public LanguageResponseDTO main() {
        return service.getMain();
    }

    @GET
    public List<LanguageResponseDTO> search(
            @QueryParam("name") String name,
            @QueryParam("description") String description,
            @QueryParam("search") String search
    ) {
        return service.search(name,description, search);
    }

    @GET
    @Path("/page")
    public List<LanguageResponseDTO> page(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size
    ) {
        return service.page(page, size);
    }

    @PATCH
    @Path("/{id}")
    public LanguageResponseDTO update(
            @PathParam("id") Long id,
            LanguageUpdateDTO dto
    ) {
        return service.update(id, dto);
    }
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
