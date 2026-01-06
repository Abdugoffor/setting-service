package uz.sud.setting.modules.address.controller;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import uz.sud.setting.modules.address.dto.AddressTranslationDto;
import uz.sud.setting.modules.address.service.AddressTranslationService;


@Path("/v1/address/{id}/translation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressTranslationController {
    private final AddressTranslationService service;

    public AddressTranslationController(AddressTranslationService service) {
        this.service = service;
    }

    @POST
    @Path("/create")
    public Long create(@PathParam("id") Long id, @Valid AddressTranslationDto.Create dto) {
        dto.addressId = id;
        return service.create(dto);
    }

    @PATCH
    @Path("{translationId}")
    public Long update(@PathParam("id") Long id,
                       @PathParam("translationId") Long translationId,
                       @Valid AddressTranslationDto.Update dto) {
        return service.update(id, translationId, dto);
    }
}
