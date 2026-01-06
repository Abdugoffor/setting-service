package uz.sud.setting.modules.address.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import uz.sud.setting.modules.address.dto.AddressDto;
import uz.sud.setting.modules.address.dto.AddressPageDto;
import uz.sud.setting.modules.address.dto.AddressParamsDto;
import uz.sud.setting.modules.address.service.AddressService;

@Path("/v1/address")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressController {
    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GET
    @Path("search")
    public List<AddressPageDto> search(
            @QueryParam("title") String title,
            @QueryParam("parentId") Long parentId,
            @QueryParam("languageId") Long languageId
    ) {
        AddressParamsDto params = new AddressParamsDto();
        params.title = title;
        params.parentId = parentId;
        params.languageId = languageId;
        return service.searchAddress(params);
    }

    @GET
    @Path("page")
    public List<AddressPageDto> page(
            @QueryParam("title") String title,
            @QueryParam("parentId") Long parentId,
            @QueryParam("languageId") Long languageId,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size
    ) {
        AddressParamsDto params = new AddressParamsDto();
        params.title = title;
        params.parentId = parentId;
        params.languageId = languageId;
        return service.pageAddresses(params, page, size);
    }

    @POST
    @Path("/create")
    public AddressDto.Response create(@Valid AddressDto.Create dto) {
        return service.create(dto);
    }

    @GET
    @Path("{id}")
    public AddressDto.Response findOne(@PathParam("id") Long id, @QueryParam("languageId") Long languageId) {
        return service.findOne(id, languageId);
    }
}
